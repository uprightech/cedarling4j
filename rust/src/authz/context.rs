// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache};
use crate::jni::util::{java_string_to_native_string,call_jni_object_method,require_some};

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/authz/Context";
const JAVA_METHOD_NAME_GET_DATA: &str = "getData";
const JAVA_METHOD_SIG_GET_DATA: &str = "()Ljava/lang/String;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaContext<'local> {
    jobj: JObject<'local>,
    get_data_method: JMethodID
}

impl <'local> JavaContext<'local> {

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
            JAVA_METHOD_NAME_GET_DATA,
            JAVA_METHOD_SIG_GET_DATA
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaContext<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_data_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DATA,
            JAVA_METHOD_SIG_GET_DATA
        );

        Ok( Some (JavaContext {
            jobj: jobj,
            get_data_method: cache.get_instance_method(&get_data_key)?
        }))
    }

    pub fn as_cedarling_context (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<serde_json::Value> {

        let data: String = require_some(self.get_data(env)?,JAVA_CLS_NAME,"data")?;
        serde_json::from_str::<'_,serde_json::Value>(&data).map_err(|e| {
            CedarlingBridgeError::JsonError {
                additional_description: "Parsing authz request context data failed".to_string(),
                json_err: e
            }
        })
    }

    fn get_data (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_data_method;
        let data_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        java_string_to_native_string(env,&data_obj)
    }
}