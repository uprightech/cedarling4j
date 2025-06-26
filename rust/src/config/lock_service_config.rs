// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::{LockServiceConfig,LogLevel};
use crate::{Result};
use crate::jni::{JniCache,JavaDuration,JavaURI};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use std::sync::{Mutex,LazyLock};
use crate::jni::util::*;
use super::{JavaLogLevel};
use url::{Url};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/LockServiceConfiguration";

const JAVA_METHOD_NAME_GET_LOG_LEVEL: &str = "getLogLevel";
const JAVA_METHOD_SIG_GET_LOG_LEVEL: &str = "()Lio/jans/cedarling/bridge/config/LogLevel;";

const JAVA_METHOD_NAME_GET_CONFIG_URI: &str = "getConfigUri";
const JAVA_METHOD_SIG_GET_CONFIG_URI: &str = "()Ljava/net/URI;";

const JAVA_METHOD_NAME_GET_DYNAMIC_CONFIG: &str = "getDynamicConfig";
const JAVA_METHOD_SIG_GET_DYNAMIC_CONFIG: &str = "()Z";

const JAVA_METHOD_NAME_GET_SSA_JWT: &str = "getSsaJwt";
const JAVA_METHOD_SIG_GET_SSA_JWT: &str = "()Ljava/lang/String;";

const JAVA_METHOD_NAME_GET_LOG_INTERVAL: &str = "getLogInterval";
const JAVA_METHOD_SIG_GET_LOG_INTERVAL: &str = "()Ljava/time/Duration;";

const JAVA_METHOD_NAME_GET_HEALTH_INTERVAL: &str = "getHealthInterval";
const JAVA_METHOD_SIG_GET_HEALTH_INTERVAL: &str = "()Ljava/time/Duration;";

const JAVA_METHOD_NAME_GET_TELEMETRY_INTERVAL: &str = "getTelemetryInterval";
const JAVA_METHOD_SIG_GET_TELEMETRY_INTERVAL: &str = "()Ljava/time/Duration;";

const JAVA_METHOD_NAME_GET_LISTEN_SSE: &str = "getListenSse";
const JAVA_METHOD_SIG_GET_LISTEN_SSE: &str = "()Z";

const JAVA_METHOD_NAME_GET_ACCEPT_INVALID_CERTS: &str = "getAcceptInvalidCerts";
const JAVA_METHOD_SIG_GET_ACCEPT_INVALID_CERTS: &str = "()Z";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaLockServiceConfig<'local> {
    
    jobj: JObject<'local>,
    get_log_level_method: JMethodID,
    get_config_uri_method: JMethodID,
    get_dynamic_config_method: JMethodID,
    get_ssa_jwt_method: JMethodID,
    get_log_interval_method: JMethodID,
    get_health_interval_method: JMethodID,
    get_telemetry_interval_method: JMethodID,
    get_listen_sse_method: JMethodID,
    get_accept_invalid_certs_method: JMethodID
}


impl <'local> JavaLockServiceConfig <'local> {

     pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_LEVEL,
            JAVA_METHOD_SIG_GET_LOG_LEVEL
        )?;
        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_CONFIG_URI,
            JAVA_METHOD_SIG_GET_CONFIG_URI
        )?;
        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DYNAMIC_CONFIG,
            JAVA_METHOD_SIG_GET_DYNAMIC_CONFIG
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_SSA_JWT,
            JAVA_METHOD_SIG_GET_SSA_JWT
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_INTERVAL,
            JAVA_METHOD_SIG_GET_LOG_INTERVAL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_HEALTH_INTERVAL,
            JAVA_METHOD_SIG_GET_HEALTH_INTERVAL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_TELEMETRY_INTERVAL,
            JAVA_METHOD_SIG_GET_TELEMETRY_INTERVAL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LISTEN_SSE,
            JAVA_METHOD_SIG_GET_LISTEN_SSE
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ACCEPT_INVALID_CERTS,
            JAVA_METHOD_SIG_GET_ACCEPT_INVALID_CERTS
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaLockServiceConfig<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_log_level_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_LEVEL,
            JAVA_METHOD_SIG_GET_LOG_LEVEL
        );

        let get_config_uri_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_CONFIG_URI,
            JAVA_METHOD_SIG_GET_CONFIG_URI
        );

        let get_dynamic_config_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DYNAMIC_CONFIG,
            JAVA_METHOD_SIG_GET_DYNAMIC_CONFIG
        );

        let get_ssa_jwt_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_SSA_JWT,
            JAVA_METHOD_SIG_GET_SSA_JWT
        );

        let get_log_interval_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_INTERVAL,
            JAVA_METHOD_SIG_GET_LOG_INTERVAL
        );

        let get_health_interval_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_HEALTH_INTERVAL,
            JAVA_METHOD_SIG_GET_HEALTH_INTERVAL
        );

        let get_telemetry_interval_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_TELEMETRY_INTERVAL,
            JAVA_METHOD_SIG_GET_TELEMETRY_INTERVAL
        );

        let get_listen_sse_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LISTEN_SSE,
            JAVA_METHOD_SIG_GET_LISTEN_SSE
        );

        let get_accept_invalid_certs_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ACCEPT_INVALID_CERTS,
            JAVA_METHOD_SIG_GET_ACCEPT_INVALID_CERTS
        );

        Ok (
            Some ( JavaLockServiceConfig {
                jobj: jobj,
                get_log_level_method: cache.get_instance_method(&get_log_level_key)?,
                get_config_uri_method: cache.get_instance_method(&get_config_uri_key)?,
                get_dynamic_config_method: cache.get_instance_method(&get_dynamic_config_key)?,
                get_ssa_jwt_method: cache.get_instance_method(&get_ssa_jwt_key)?,
                get_log_interval_method: cache.get_instance_method(&get_log_interval_key)?,
                get_health_interval_method: cache.get_instance_method(&get_health_interval_key)?,
                get_telemetry_interval_method: cache.get_instance_method(&get_telemetry_interval_key)?,
                get_listen_sse_method: cache.get_instance_method(&get_listen_sse_key)?,
                get_accept_invalid_certs_method: cache.get_instance_method(&get_accept_invalid_certs_key)?
            } )
        )
    }

    pub fn as_cedarling_lock_service_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<LockServiceConfig>  {

        let log_level : LogLevel = {
            let java_log_level = require_some(self.get_log_level(env)?,JAVA_CLS_NAME,"logLevel")?;
            java_log_level.as_enum()?
        };

        let config_uri: Url = {
            let java_config_uri = require_some(self.get_config_uri(env)?,JAVA_CLS_NAME,"configUri")?;
            java_config_uri.as_url(env)?
        };

        let dynamic_config: bool = self.get_dynamic_config(env)?;
        let ssa_jwt: Option<String>  = self.get_ssa_jwt(env)?;

        let log_interval =  { 
            let opt_val = self.get_log_interval(env)?;
            opt_val.map(|val| val.to_duration(env)).transpose()?
        };

        let health_interval = {
            let opt_val = self.get_health_interval(env)?;
            opt_val.map(|val| val.to_duration(env)).transpose()?
        };

        let telemetry_interval = { 
            let opt_val = self.get_telemetry_interval(env)?;
            opt_val.map(|val| val.to_duration(env)).transpose()?
        };

        let listen_sse : bool  = self.get_listen_sse(env)?;

        let accept_invalid_certs : bool = self.get_accept_invalid_certs(env)?;

        return Ok(LockServiceConfig {
            log_level: log_level,
            config_uri: config_uri,
            dynamic_config: dynamic_config,
            ssa_jwt: ssa_jwt,
            log_interval: log_interval,
            health_interval: health_interval,
            telemetry_interval: telemetry_interval,
            listen_sse: listen_sse,
            accept_invalid_certs: accept_invalid_certs
        });
    }

    fn get_log_level (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaLogLevel>> {

        let method: &JMethodID = &self.get_log_level_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        Ok(JavaLogLevel::from_jni_object(env,obj)?)
    }

    fn get_config_uri (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaURI<'local>>> {

        let method: &JMethodID = &self.get_config_uri_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaURI::new(obj)
    }

    fn get_dynamic_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_dynamic_config_method;
        call_jni_bool_method(env,&self.jobj,method,&[])
    }

    fn get_ssa_jwt (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_ssa_jwt_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_log_interval (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaDuration<'local>>> {

        let method: &JMethodID = &self.get_log_interval_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaDuration::new(obj)
    }

    fn get_health_interval (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaDuration<'local>>> {

        let method: &JMethodID = &self.get_health_interval_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaDuration::new(obj)
    }

    fn get_telemetry_interval (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaDuration<'local>>> {

        let method: &JMethodID = &self.get_telemetry_interval_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaDuration::new(obj)
    }

    fn get_listen_sse (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_listen_sse_method;
        call_jni_bool_method(env,&self.jobj,method,&[])   
    }

    fn get_accept_invalid_certs (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_accept_invalid_certs_method;
        call_jni_bool_method(env,&self.jobj,method,&[])
    }
}