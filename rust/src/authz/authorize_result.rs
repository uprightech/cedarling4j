use cedarling::{AuthorizeResult};
use crate::{Result};
use crate::cedar_policy::{JavaPolicyResponse};
use crate::jni::{JniCache};
use crate::jni::util::*;
use jni::JNIEnv;
use jni::objects::{JClass,JMethodID,JObject};
use jni::sys::{jvalue};

use std::sync::{Mutex,LazyLock};


const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/authz/AuthorizeResult";

const JAVA_CLS_CTOR: &str = "<init>";
const JAVA_CLS_CTOR_SIG: &str = "()V";

const JAVA_METHOD_NAME_SET_WORKLOAD: &str = "setWorkload";
const JAVA_METHOD_SIG_SET_WORKLOAD: &str =  "(Lio/jans/cedarling/bridge/cedar/policy/PolicyResponse;)V";

const JAVA_METHOD_NAME_SET_PERSON: &str = "setPerson";
const JAVA_METHOD_SIG_SET_PERSON: &str =  "(Lio/jans/cedarling/bridge/cedar/policy/PolicyResponse;)V";

const JAVA_METHOD_NAME_ADD_PRINCIPAL: &str = "addPrincipal";
const JAVA_METHOD_SIG_ADD_PRINCIPAL: &str =  "(Ljava/lang/String;Lio/jans/cedarling/bridge/cedar/policy/PolicyResponse;)V";

const JAVA_METHOD_NAME_SET_DECISION: &str = "setDecision";
const JAVA_METHOD_SIG_SET_DECISION: &str =  "(Z)V";

const JAVA_METHOD_NAME_SET_REQUEST_ID: &str = "setRequestId";
const JAVA_METHOD_SIG_SET_REQUEST_ID: &str =  "(Ljava/lang/String;)V";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaAuthorizeResult <'r> {

    authz_result: &'r AuthorizeResult,
    ctor_method: JMethodID,
    set_workload_method: JMethodID,
    set_person_method: JMethodID,
    add_principal_method: JMethodID,
    set_decision_method: JMethodID,
    set_request_id_method: JMethodID
}

impl <'r> JavaAuthorizeResult <'r> {

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
            JAVA_METHOD_NAME_SET_WORKLOAD,
            JAVA_METHOD_SIG_SET_WORKLOAD
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SET_PERSON,
            JAVA_METHOD_SIG_SET_PERSON
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_ADD_PRINCIPAL,
            JAVA_METHOD_SIG_ADD_PRINCIPAL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SET_DECISION,
            JAVA_METHOD_SIG_SET_DECISION
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SET_REQUEST_ID,
            JAVA_METHOD_SIG_SET_REQUEST_ID
        )
    }

    pub fn new<'local> (
        authz_result: &'r AuthorizeResult
    ) -> Result<JavaAuthorizeResult<'r>> {

        let cache = LOCAL_JNI_CACHE.lock()?;

        let ctor_key = (
            JAVA_CLS_NAME,
            JAVA_CLS_CTOR,
            JAVA_CLS_CTOR_SIG
        );

        let set_workload_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SET_WORKLOAD,
            JAVA_METHOD_SIG_SET_WORKLOAD
        );

        let set_person_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SET_PERSON,
            JAVA_METHOD_SIG_SET_PERSON
        );

        let add_principal_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_ADD_PRINCIPAL,
            JAVA_METHOD_SIG_ADD_PRINCIPAL
        );

        let set_decision_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SET_DECISION,
            JAVA_METHOD_SIG_SET_DECISION
        );

        let set_request_id_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SET_REQUEST_ID,
            JAVA_METHOD_SIG_SET_REQUEST_ID
        );

        Ok(JavaAuthorizeResult {
            authz_result: authz_result,
            ctor_method: cache.get_instance_method(&ctor_key)?,
            set_workload_method: cache.get_instance_method(&set_workload_key)?,
            set_person_method: cache.get_instance_method(&set_person_key)?,
            add_principal_method: cache.get_instance_method(&add_principal_key)?,
            set_decision_method: cache.get_instance_method(&set_decision_key)?,
            set_request_id_method: cache.get_instance_method(&set_request_id_key)?
        })
    }
    
    pub fn as_java_object<'local> (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<JObject<'local>> {

        let cache = LOCAL_JNI_CACHE.lock()?;
        let class: JClass<'local> = cache.get_class(env,JAVA_CLS_NAME)?;
        let obj =  call_jni_object_constructor(env,&class,&self.ctor_method,&[])?;

        self.set_person(env,&obj)?;
        self.set_workload(env,&obj)?;
        self.set_principals(env,&obj)?;
        self.set_request_id(env,&obj)?;
        self.set_decision(env,&obj)?;
        
        Ok(obj)
    }

    fn set_workload<'local> (
        &self,
        env: &mut JNIEnv<'local>,
        ar_obj: &JObject<'local>
    ) -> Result<()> {

        let workload_obj = match &self.authz_result.workload {
            Some(val) => JavaPolicyResponse::new(&val)?.as_java_object(env)?,
            None => JObject::null()
        };
        let args: [jvalue; 1] = [ jvalue{ l: workload_obj.as_raw() }];
        let method: &JMethodID = &self.set_workload_method;
        call_jni_void_method(env,ar_obj,method,&args)
    }

    fn set_person<'local> (
        &self,
        env: &mut JNIEnv<'local>,
        ar_obj: &JObject<'local>
    ) -> Result<()> {

        let person_obj = match &self.authz_result.person {
            Some(val) => JavaPolicyResponse::new(&val)?.as_java_object(env)?,
            None => JObject::null(),
        };
        let args: [jvalue; 1] = [ jvalue{l: person_obj.as_raw() }];
        let method: &JMethodID = &self.set_person_method;
        call_jni_void_method(env,ar_obj,method,&args)
    }

    fn set_request_id <'local> (
        &self,
        env: &mut JNIEnv<'local>,
        ar_obj: &JObject<'local>
    ) -> Result<()> {

        let req_id_obj = env.new_string(&self.authz_result.request_id)?;
        let req_id_obj_ref = env.new_global_ref(req_id_obj)?;

        let args: [jvalue; 1] = [ jvalue{ l: req_id_obj_ref.as_raw() } ];
        let method: &JMethodID = &self.set_request_id_method;
        call_jni_void_method(env,ar_obj,method,&args)
    }

    fn set_principals <'local> (
        &self,
        env: &mut JNIEnv<'local>,
        ar_obj: &JObject<'local>
    ) -> Result<()> {

        let method: &JMethodID = &self.add_principal_method;
        for (key , response) in &self.authz_result.principals {

            let key_obj = env.new_string(&key)?;

            let response_obj = JavaPolicyResponse::new(&response)?.as_java_object(env)?;
            let args: [jvalue; 2] = [ jvalue{l: key_obj.as_raw()}, jvalue{ l: response_obj.as_raw()} ];
            let _ = call_jni_void_method(env,ar_obj,method,&args)?;
        }
        Ok(())
    }

    fn set_decision <'local> (
        &self,
        env: &mut JNIEnv<'local>,
        ar_obj: &JObject<'local>
    ) -> Result<()> {

        let method: &JMethodID = &self.set_decision_method;
        let decision_val: u8 = match &self.authz_result.decision {
             true => 1,
             false => 0
        };

        let args: [jvalue;1] = [ jvalue{ z: decision_val } ];
        call_jni_void_method(env,ar_obj,method,&args)
    }

}