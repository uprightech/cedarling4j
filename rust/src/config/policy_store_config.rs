// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::{PolicyStoreConfig};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache,JavaFile};
use crate::config::{JavaPolicyStoreSource};
use crate::jni::util::{call_jni_object_method,call_jni_string_method,require_some};

use std::path::{Path};
use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/PolicyStoreConfiguration";

const JAVA_METHOD_NAME_GET_SOURCE: &str = "getSource";
const JAVA_METHOD_SIG_GET_SOURCE: &str = "()Lio/jans/cedarling/bridge/config/PolicyStoreSource;";
const JAVA_METHOD_NAME_GET_DATA: &str = "getData";
const JAVA_METHOD_SIG_GET_DATA: &str = "()Ljava/lang/String;";
const JAVA_METHOD_NAME_GET_DATA_PATH: &str = "getDataPath";
const JAVA_METHOD_SIG_GET_DATA_PATH: &str = "()Ljava/io/File;";


pub (crate) struct JavaPolicyStoreConfig <'local> {
    jobj: JObject<'local>,
    get_source_method: JMethodID,
    get_data_method: JMethodID,
    get_data_path_method: JMethodID
}

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

impl <'local> JavaPolicyStoreConfig <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_SOURCE,
            JAVA_METHOD_SIG_GET_SOURCE
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DATA,
            JAVA_METHOD_SIG_GET_DATA
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DATA_PATH,
            JAVA_METHOD_SIG_GET_DATA_PATH
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaPolicyStoreConfig<'local>>> {

        if jobj.is_null() {

            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_source_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_SOURCE,
            JAVA_METHOD_SIG_GET_SOURCE
        );

        let get_data_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DATA,
            JAVA_METHOD_SIG_GET_DATA
        );

        let get_data_path_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DATA_PATH,
            JAVA_METHOD_SIG_GET_DATA_PATH
        );

        Ok (
            Some( JavaPolicyStoreConfig {
                jobj: jobj,
                get_source_method: cache.get_instance_method(&get_source_key)?,
                get_data_method: cache.get_instance_method(&get_data_key)?,
                get_data_path_method: cache.get_instance_method(&get_data_path_key)?
            })
        )
    }

    pub fn as_cedarling_policy_store_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<PolicyStoreConfig> {

        let policy_store_source = { require_some(self.get_source(env)?,JAVA_CLS_NAME,"source")? };
        let opt_data = self.get_data(env)?;
        let opt_data_path = self.get_data_path(env)?;

        let data_fn = || -> Result<String> { require_some(opt_data,JAVA_CLS_NAME,"source") };

        let data_path_fn = || -> Result<Box<Path>> {

            let data_path_obj = require_some(opt_data_path,JAVA_CLS_NAME,"dataPath")?;
            match data_path_obj.get_absolute_path(env)? {
                Some(data) => Ok(Path::new(&data).into()),
                None => Err(CedarlingBridgeError::GenericError("Could not get policy store file path".to_string()))
            }
        };

        Ok( PolicyStoreConfig {
            source: policy_store_source.as_enum(data_fn,data_path_fn)?
        })
    }

    fn get_source (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaPolicyStoreSource>> {
        
        let method: &JMethodID = &self.get_source_method;
        let source_obj = call_jni_object_method(env,&self.jobj,method,&[])?;

        JavaPolicyStoreSource::from_jni_object(env,source_obj)
    }

    fn get_data (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_data_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_data_path (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaFile<'local>>> {
        
        let method: &JMethodID = &self.get_data_path_method;
        let file_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaFile::new(file_obj)
    }

}