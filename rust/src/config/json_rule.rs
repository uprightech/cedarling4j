use cedarling::{JsonRule};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};

use crate::{Result,CedarlingBridgeError};
use crate::jni::util::*;
use crate::jni::{JniCache};

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/JsonRule";

const JAVA_METHOD_NAME_GET_VALUE: &str = "getValue";
const JAVA_METHOD_SIG_GET_VALUE: &str =  "()Ljava/lang/String;";
static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaJsonRule<'local>  {
    jobj: JObject<'local>,
    get_value_method: JMethodID
}

impl <'local> JavaJsonRule<'local> {

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
            JAVA_METHOD_NAME_GET_VALUE,
            JAVA_METHOD_SIG_GET_VALUE
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaJsonRule<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let get_value_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_VALUE,
            JAVA_METHOD_SIG_GET_VALUE
        );

        let cache = LOCAL_JNI_CACHE.lock()?;
        Ok (
            Some ( JavaJsonRule {
                jobj: jobj,
                get_value_method: cache.get_instance_method(&get_value_key)?
            } )
        )
    }

    pub fn as_cedarling_json_rule (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<JsonRule> {

        let rule_value = { require_some(self.get_value(env)?,JAVA_CLS_NAME,"value")? };
        let json_rule_value = serde_json::from_str(&rule_value).map_err(|e| { 
            CedarlingBridgeError::JsonError{
                json_err:e, 
                additional_description: "Parsing json rule failed".to_string()
            }
        })?;

        Ok(
            JsonRule::new(json_rule_value).map_err(|e| CedarlingBridgeError::GenericError(e.to_string()))?
        ) 
    }

    fn get_value(
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: JMethodID = self.get_value_method;
        call_jni_string_method(env,&self.jobj,&method,&[])
    }
}

