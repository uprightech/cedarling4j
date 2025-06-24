use cedar_policy::{Response};
use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::*;
use jni::JNIEnv;
use jni::objects::{JClass,JMethodID,JObject};
use jni::sys::{jvalue};

use std::sync::{Mutex,LazyLock};
use super::{JavaAuthzDecision,JavaDiagnostics};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/cedar/policy/PolicyResponse";

const JAVA_CLS_CTOR: &str = "<init>";
const JAVA_CLS_CTOR_SIG: &str = "(Lio/jans/cedarling/bridge/cedar/policy/AuthzDecision;Lio/jans/cedarling/bridge/cedar/policy/Diagnostics;)V";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaPolicyResponse <'r> {
    policy_response: &'r Response,
    ctor_method: JMethodID,
}

impl <'r> JavaPolicyResponse <'r> {

    pub fn jni_cache_init<'local> (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class (
            env,
            JAVA_CLS_NAME
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_CLS_CTOR,
            JAVA_CLS_CTOR_SIG
        )
    }

    pub fn new<'local> (
        policy_response: &'r Response
    ) -> Result<JavaPolicyResponse<'r>> {

        let cache = LOCAL_JNI_CACHE.lock()?;

        let ctor_key = (
            JAVA_CLS_NAME,
            JAVA_CLS_CTOR,
            JAVA_CLS_CTOR_SIG
        );

        Ok(JavaPolicyResponse {
            policy_response: policy_response,
            ctor_method: cache.get_instance_method(&ctor_key)?
        })
    }
    
    pub fn as_java_object<'local> (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<JObject<'local>> {

        let cache = LOCAL_JNI_CACHE.lock()?;
        let class: JClass<'local> = cache.get_class(env,JAVA_CLS_NAME)?;

        let decision_obj = JavaAuthzDecision::as_java_object(env,&self.policy_response.decision())?;

        let diagnostics_obj = JavaDiagnostics::new(self.policy_response.diagnostics())?.as_java_object(env)?;

        let args: [jvalue;2] = [ jvalue {l: decision_obj.as_raw() }, jvalue{l: diagnostics_obj.as_raw() }] ;
        call_jni_object_constructor(env,&class,&self.ctor_method,&args)
    }
   
}