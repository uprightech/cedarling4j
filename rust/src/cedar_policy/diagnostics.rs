use cedar_policy::{AuthorizationError,Diagnostics,PolicyId};
use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::*;
use jni::JNIEnv;
use jni::objects::{JClass,JMethodID,JObject};
use jni::sys::{jvalue};

use std::sync::{Mutex,LazyLock};
use super::{JavaPolicyId,JavaAuthzError};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/cedar/policy/Diagnostics";

const JAVA_CLS_CTOR: &str = "<init>";
const JAVA_CLS_CTOR_SIG: &str = "()V";

const JAVA_METHOD_NAME_ADD_POLICY_ID: &str = "addPolicyId";
const JAVA_METHOD_SIG_ADD_POLICY_ID: &str = "(Lio/jans/cedarling/bridge/cedar/policy/PolicyId;)V";

const JAVA_METHOD_NAME_ADD_ERROR: &str = "addError";
const JAVA_METHOD_SIG_ADD_ERROR: &str = "(Lio/jans/cedarling/bridge/cedar/policy/AuthzError;)V";


static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaDiagnostics <'r> {
    diagnostics: &'r Diagnostics,
    ctor_method: JMethodID,
    add_policy_id_method: JMethodID,
    add_error_method: JMethodID
}

impl <'r> JavaDiagnostics <'r> {

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
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_ADD_POLICY_ID,
            JAVA_METHOD_SIG_ADD_POLICY_ID
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_ADD_ERROR,
            JAVA_METHOD_SIG_ADD_ERROR
        )
    }

    pub fn new<'local> (
        diagnostics: &'r Diagnostics
    ) -> Result<JavaDiagnostics<'r>> {

        let cache = LOCAL_JNI_CACHE.lock()?;

        let ctor_key = (
            JAVA_CLS_NAME,
            JAVA_CLS_CTOR,
            JAVA_CLS_CTOR_SIG
        );

        let add_policy_id_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_ADD_POLICY_ID,
            JAVA_METHOD_SIG_ADD_POLICY_ID
        );

        let add_error_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_ADD_ERROR,
            JAVA_METHOD_SIG_ADD_ERROR
        );

        Ok(JavaDiagnostics {
            diagnostics: diagnostics,
            ctor_method: cache.get_instance_method(&ctor_key)?,
            add_policy_id_method: cache.get_instance_method(&add_policy_id_key)?,
            add_error_method: cache.get_instance_method(&add_error_key)?
        })
    }
    
    pub fn as_java_object<'local> (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<JObject<'local>> {

        let cache = LOCAL_JNI_CACHE.lock()?;
        let class: JClass<'local> = cache.get_class(env,JAVA_CLS_NAME)?;
        let obj: JObject<'local> = call_jni_object_constructor(env,&class,&self.ctor_method,&[])?;

        for p in self.diagnostics.reason() {
            self.add_policy_id(env,&obj,p)?;
        }

        for e in self.diagnostics.errors() {
            self.add_error(env,&obj,e)?;
        }
        
        Ok(obj)
    }

    fn add_policy_id<'local> (
        &self,
        env: &mut JNIEnv<'local>,
        diagnostics: &JObject<'local>,
        policy_id: &PolicyId
    ) -> Result<()> {

        let pid_obj: JObject<'local> = JavaPolicyId::new(policy_id)?.as_java_object(env)?;

        let args: [jvalue;1] = [ jvalue{l : pid_obj.as_raw() } ];
        let method: &JMethodID = &self.add_policy_id_method;
        call_jni_void_method(env,diagnostics,method,&args)
    }

    fn add_error<'local> (
        &self,
        env: &mut JNIEnv<'local>,
        diagnostics: &JObject<'local>,
        error: &AuthorizationError
    ) -> Result<()> {

        let err_obj : JObject<'local> = JavaAuthzError::new(error)?.as_java_object(env)?;

        let args: [jvalue;1] = [ jvalue{l : err_obj.as_raw() } ];
        let method: &JMethodID = &self.add_error_method;
        call_jni_void_method(env,diagnostics,method,&args)
    }
}