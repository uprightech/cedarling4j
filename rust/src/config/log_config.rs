// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::{LogConfig,MemoryLogConfig};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::*;
use super::{JavaLogType,JavaLogLevel,JavaMemoryLogConfig};

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/LogConfiguration";

const JAVA_METHOD_NAME_GET_LOG_TYPE:  &str = "getLogType";
const JAVA_METHOD_SIG_GET_LOG_TYPE:   &str = "()Lio/jans/cedarling/bridge/config/LogType;";
const JAVA_METHOD_NAME_GET_LOG_LEVEL: &str = "getLogLevel";
const JAVA_METHOD_SIG_GET_LOG_LEVEL:  &str = "()Lio/jans/cedarling/bridge/config/LogLevel;";
const JAVA_METHOD_NAME_GET_MEMORY_LOG_CONFIG: &str = "getMemoryLogConfiguration";
const JAVA_METHOD_SIG_GET_MEMORY_LOG_CONFIG:  &str = "()Lio/jans/cedarling/bridge/config/MemoryLogConfiguration;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaLogConfig<'local> {
    jobj: JObject<'local>,
    get_log_type_method: JMethodID,
    get_log_level_method: JMethodID,
    get_memory_log_config_method: JMethodID
}

impl <'local> JavaLogConfig <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_TYPE,
            JAVA_METHOD_SIG_GET_LOG_TYPE
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_LEVEL,
            JAVA_METHOD_SIG_GET_LOG_LEVEL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_MEMORY_LOG_CONFIG,
            JAVA_METHOD_SIG_GET_MEMORY_LOG_CONFIG
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaLogConfig<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_log_type_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_TYPE,
            JAVA_METHOD_SIG_GET_LOG_TYPE
        );

        let get_log_level_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_LEVEL,
            JAVA_METHOD_SIG_GET_LOG_LEVEL
        );

        let get_memory_log_config_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_MEMORY_LOG_CONFIG,
            JAVA_METHOD_SIG_GET_MEMORY_LOG_CONFIG
        );

        Ok ( 
            Some ( JavaLogConfig {
                jobj: jobj,
                get_log_type_method: cache.get_instance_method(&get_log_type_key)?,
                get_log_level_method: cache.get_instance_method(&get_log_level_key)?,
                get_memory_log_config_method: cache.get_instance_method(&get_memory_log_config_key)?
            })
        )
    }

    pub fn as_cedarling_log_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<LogConfig> {

        let java_log_type = { require_some(self.get_log_type(env)?,JAVA_CLS_NAME,"logType")? };
        let java_log_level = { require_some(self.get_log_level(env)?,JAVA_CLS_NAME,"logLevel")? };
        let mem_log_config: Option<MemoryLogConfig> = { self.get_cedarling_memory_log_config(env)? };
        let log_type = java_log_type.as_enum(mem_log_config)?;
        let log_level = java_log_level.as_enum()?;
        Ok(LogConfig {
            log_type: log_type,
            log_level: log_level
        })
    }

    fn get_log_type (
        &self,
        env: &mut JNIEnv<'local>,
    ) -> Result<Option<JavaLogType>> {

        let method: &JMethodID = &self.get_log_type_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        Ok(JavaLogType::from_jni_object(env,obj)?)
    }

    fn get_log_level (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaLogLevel>> {

        let method: &JMethodID = &self.get_log_level_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        Ok(JavaLogLevel::from_jni_object(env,obj)?)
    }

    fn get_memory_log_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaMemoryLogConfig<'local>>> {

        let method: &JMethodID = &self.get_memory_log_config_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaMemoryLogConfig::new(obj)
    }

    fn get_cedarling_memory_log_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<MemoryLogConfig>> {

        self.get_memory_log_config(env)?
            .map(|config| config.as_cedarling_memory_log_config(env))
            .transpose()
    } 
}