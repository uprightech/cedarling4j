// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use jni::JNIEnv;
use jni::objects::{JObject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::util::{call_jni_method_to_string};
use cedarling::{LogLevel};
use std::str::{FromStr};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/LogType";

pub (crate) struct JavaLogLevel {
    name: String
}

impl JavaLogLevel {

   
    pub fn from_jni_object<'local> (
        env: &mut JNIEnv<'local>,
        jobj: JObject<'local>
    ) -> Result<Option<JavaLogLevel>> {

        if jobj.is_null() {
            return Ok(None);
        }

        Ok( call_jni_method_to_string(env,&jobj)?.map(|level| JavaLogLevel{ name: level}) )
    }

    pub fn as_enum (
        &self
    ) -> Result<LogLevel> {

        LogLevel::from_str(&self.name).map_err(|_|  {
            CedarlingBridgeError::UnknownEnumValue {enum_cls: JAVA_CLS_NAME, value: self.name.clone()}
        })
    }
}