use cedarling::{UnsignedRoleIdSrc};
use jni::JNIEnv;
use jni::objects::{JClass,JMethodID,JObject};

use crate::{Result};
use crate::jni::util::*;
use crate::jni::{JniCache,JniCacheResult};

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/UnsignedRoleIdSrc";

const JAVA_METHOD_NAME_GET_VALUE: &str = "getValue";
const JAVA_METHOD_SIG_GET_VALUE: &str = "()Ljava/lang/String;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaUnsignedRoleIdSrc <'local> {

    jobj: JObject<'local>,
    get_value_method: JMethodID,
}

impl <'local> JavaUnsignedRoleIdSrc <'local> {

    pub fn jni_init (
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
            JAVA_METHOD_NAME_GET_VALUE,
            JAVA_METHOD_SIG_GET_VALUE
        )
       
    }

    pub fn new (
        env: &mut JNIEnv<'local>,
        jobj: JObject<'local>
    ) -> Result<Option<JavaUnsignedRoleIdSrc<'local>>> {

        if jobj.is_null() {
            return None;
        }

        let cache =  LOCAL_JNI_CACHE.lock()?;

        let get_value_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_VALUE,
            JAVA_METHOD_SIG_GET_VALUE
        );

        Ok(
            Some (
            JavaUnsignedRoleIdSrc {
                jobj: jobj,
                get_value_method: cache.get_instance_method(&get_value_key)?
            })
        )
    }

    pub fn as_cedarling_unsigned_role_id_src (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<UnsignedRoleIdSrc> {

        //TODO: Fix this so the value can be provided programmatically
        let _value = require_some(self.get_value(env)?,JAVA_CLS_NAME,"value")?;
        Ok(UnsignedRoleIdSrc)
    }

    fn get_value (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_value_method;
        unsafe { call_jni_string_method(env,&self.jobj,method,&[]) }
    }

    
}
