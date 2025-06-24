use cedarling::{EntityNames};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};

use crate::{Result};
use crate::jni::util::*;
use crate::jni::{JniCache};

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/EntityNames";

const JAVA_METHOD_NAME_GET_USER: &str = "getUser";
const JAVA_METHOD_SIG_GET_USER: &str = "()Ljava/lang/String;";

const JAVA_METHOD_NAME_GET_WORKLOAD: &str = "getWorkload";
const JAVA_METHOD_SIG_GET_WORKLOAD: &str = "()Ljava/lang/String;";

const JAVA_METHOD_NAME_GET_ROLE: &str = "getRole";
const JAVA_METHOD_SIG_GET_ROLE: &str = "()Ljava/lang/String;";

const JAVA_METHOD_NAME_GET_ISS: &str = "getIss";
const JAVA_METHOD_SIG_GET_ISS: &str = "()Ljava/lang/String;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaEntityNames <'local> {

    jobj: JObject<'local>,
    get_user_method: JMethodID,
    get_workload_method: JMethodID,
    get_role_method: JMethodID,
    get_iss_method: JMethodID
}

impl <'local> JavaEntityNames <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(
            env,
            JAVA_CLS_NAME
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_USER,
            JAVA_METHOD_SIG_GET_USER
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_WORKLOAD,
            JAVA_METHOD_SIG_GET_WORKLOAD
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ROLE,
            JAVA_METHOD_SIG_GET_ROLE
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ISS,
            JAVA_METHOD_SIG_GET_ISS
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaEntityNames<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache =  LOCAL_JNI_CACHE.lock()?;

        let get_user_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_USER,
            JAVA_METHOD_SIG_GET_USER
        );

        let get_workload_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_WORKLOAD,
            JAVA_METHOD_SIG_GET_WORKLOAD
        );

        let get_role_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ROLE,
            JAVA_METHOD_SIG_GET_ROLE
        );


        let get_iss_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ISS,
            JAVA_METHOD_SIG_GET_ISS
        );

        Ok (
            Some ( JavaEntityNames {
                jobj: jobj,
                get_user_method: cache.get_instance_method(&get_user_key)?,
                get_workload_method: cache.get_instance_method(&get_workload_key)?,
                get_role_method: cache.get_instance_method(&get_role_key)?,
                get_iss_method: cache.get_instance_method(&get_iss_key)?
            } )
        )
    }

    pub fn as_cedarling_entity_names (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<EntityNames> {

        let user = require_some(self.get_user(env)?,JAVA_CLS_NAME,"user")?;
        let workload = require_some(self.get_workload(env)?,JAVA_CLS_NAME,"workload")?;
        let role = require_some(self.get_role(env)?,JAVA_CLS_NAME,"rol")?;
        let iss  = require_some(self.get_iss(env)?,JAVA_CLS_NAME,"iss")?;
        
        Ok(EntityNames {
            user: user,
            workload: workload,
            role: role,
            iss: iss
        })
    }

    fn get_user (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_user_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_workload (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_workload_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_role (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_role_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_iss (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_iss_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }
    
}
