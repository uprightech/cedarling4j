// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::{LogTypeConfig,MemoryLogConfig};
use jni::JNIEnv;
use jni::objects::{JObject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::util::{call_jni_method_to_string};

const JAVA_CLS_NAME:          &str = "io/jans/cedarling/bridge/config/LogType";
const JAVA_ENUM_VALUE_OFF:    &str = "OFF";
const JAVA_ENUM_VALUE_MEMORY: &str = "MEMORY";
const JAVA_ENUM_VALUE_STDOUT: &str = "STDOUT";

pub (crate) struct JavaLogType {
    name: String,
}

impl JavaLogType {

   
    pub fn from_jni_object<'local> (
        env: &mut JNIEnv<'local>,
        jobj: JObject<'local>
    ) -> Result<Option<JavaLogType>> {

        if jobj.is_null() {
            return Ok(None);
        }

        Ok(call_jni_method_to_string(env,&jobj)?.map(|logtype| JavaLogType{ name: logtype}))
    }

    pub fn as_enum (
        &self,
        mem_log_config: Option<MemoryLogConfig>
    ) -> Result<LogTypeConfig> {

        let invalid_config_state_error = CedarlingBridgeError::GenericError(
            "Log type set to MEMORY but no memory log configuration provided".to_string()
        );

        match &self.name as &str {
            JAVA_ENUM_VALUE_OFF => Ok(LogTypeConfig::Off),
            JAVA_ENUM_VALUE_MEMORY => mem_log_config.map(|config| LogTypeConfig::Memory(config)).ok_or_else(|| invalid_config_state_error),
            JAVA_ENUM_VALUE_STDOUT => Ok(LogTypeConfig::StdOut),
            _ => Err( CedarlingBridgeError::UnknownEnumValue{enum_cls: JAVA_CLS_NAME, value: self.name.clone()} )
        }
    }
}