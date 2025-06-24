use cedarling::*;
use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::{java_string_to_native_string,call_jni_object_method,require_some};
use crate::config::*;
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};


use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/BootstrapConfiguration";

const JAVA_METHOD_NAME_GET_APPLICATION_NAME: &str = "getApplicationName";
const JAVA_METHOD_SIG_GET_APPLICATION_NAME: &str = "()Ljava/lang/String;";

const JAVA_METHOD_NAME_GET_LOG_CONFIGURATION: &str = "getLogConfiguration";
const JAVA_METHOD_SIG_GET_LOG_CONFIGURATION: &str = "()Lio/jans/cedarling/bridge/config/LogConfiguration;";

const JAVA_METHOD_NAME_GET_POLICY_STORE_CONFIGURATION: &str = "getPolicyStoreConfiguration";
const JAVA_METHOD_SIG_GET_POLICY_STORE_CONFIGURATION: &str = "()Lio/jans/cedarling/bridge/config/PolicyStoreConfiguration;";

const JAVA_METHOD_NAME_GET_JWT_CONFIGURATION: &str = "getJwtConfiguration";
const JAVA_METHOD_SIG_GET_JWT_CONFIGURATION: &str = "()Lio/jans/cedarling/bridge/config/JwtConfiguration;";

const JAVA_METHOD_NAME_GET_AUTHORIZATION_CONFIGURATION: &str = "getAuthzConfiguration";
const JAVA_METHOD_SIG_GET_AUTHORIZATION_CONFIGURATION: &str = "()Lio/jans/cedarling/bridge/config/AuthorizationConfiguration;";

const JAVA_METHOD_NAME_GET_ENTITY_BUILDER_CONFIGURATION: &str = "getEntityBuilderConfiguration";
const JAVA_METHOD_SIG_GET_ENTITY_BUILDER_CONFIGURATION: &str = "()Lio/jans/cedarling/bridge/config/EntityBuilderConfiguration;";

const JAVA_METHOD_NAME_GET_LOCK_CONFIGURATION: &str = "getLockConfiguration";
const JAVA_METHOD_SIG_GET_LOCK_CONFIGURATION: &str = "()Lio/jans/cedarling/bridge/config/LockServiceConfiguration;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaBootstrapConfig<'local> {
    jobj: JObject<'local>,
    get_application_name_method: JMethodID,
    get_log_configuration_method: JMethodID,
    get_policy_store_configuration_method: JMethodID,
    get_jwt_configuration_method: JMethodID,
    get_authorization_configuration_method: JMethodID,
    get_entity_builder_configuration_method: JMethodID,
    get_lock_configuration_method: JMethodID
}

impl <'local> JavaBootstrapConfig <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>,
    ) -> Result<()> {

        let mut cache  = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method(
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_APPLICATION_NAME,
            JAVA_METHOD_SIG_GET_APPLICATION_NAME
        )?;
        cache.add_instance_method(
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_CONFIGURATION,
            JAVA_METHOD_SIG_GET_LOG_CONFIGURATION
        )?;
        cache.add_instance_method(
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_POLICY_STORE_CONFIGURATION,
            JAVA_METHOD_SIG_GET_POLICY_STORE_CONFIGURATION,
        )?;
        cache.add_instance_method(
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWT_CONFIGURATION,
            JAVA_METHOD_SIG_GET_JWT_CONFIGURATION
        )?;

        cache.add_instance_method(
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_AUTHORIZATION_CONFIGURATION,
            JAVA_METHOD_SIG_GET_AUTHORIZATION_CONFIGURATION
        )?;

        cache.add_instance_method(
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ENTITY_BUILDER_CONFIGURATION,
            JAVA_METHOD_SIG_GET_ENTITY_BUILDER_CONFIGURATION
        )?;

        cache.add_instance_method(
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOCK_CONFIGURATION,
            JAVA_METHOD_SIG_GET_LOCK_CONFIGURATION
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaBootstrapConfig<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_application_name_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_APPLICATION_NAME,
            JAVA_METHOD_SIG_GET_APPLICATION_NAME
        );

        let get_log_configuration_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_CONFIGURATION,
            JAVA_METHOD_SIG_GET_LOG_CONFIGURATION
        );

        let get_policy_store_configuration_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_POLICY_STORE_CONFIGURATION,
            JAVA_METHOD_SIG_GET_POLICY_STORE_CONFIGURATION,
        );

        let get_jwt_configuration_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWT_CONFIGURATION,
            JAVA_METHOD_SIG_GET_JWT_CONFIGURATION
        );

        let get_authorization_configuration_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_AUTHORIZATION_CONFIGURATION,
            JAVA_METHOD_SIG_GET_AUTHORIZATION_CONFIGURATION
        );

        let get_entity_builder_configuration_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ENTITY_BUILDER_CONFIGURATION,
            JAVA_METHOD_SIG_GET_ENTITY_BUILDER_CONFIGURATION
        );

        let get_lock_configuration_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOCK_CONFIGURATION,
            JAVA_METHOD_SIG_GET_LOCK_CONFIGURATION
        );

        Ok (
            Some ( JavaBootstrapConfig {
                jobj: jobj,
                get_application_name_method: cache.get_instance_method(&get_application_name_key)?,
                get_log_configuration_method: cache.get_instance_method(&get_log_configuration_key)?,
                get_policy_store_configuration_method: cache.get_instance_method(&get_policy_store_configuration_key)?,
                get_jwt_configuration_method: cache.get_instance_method(&get_jwt_configuration_key)?,
                get_authorization_configuration_method: cache.get_instance_method(&get_authorization_configuration_key)?,
                get_entity_builder_configuration_method: cache.get_instance_method(&get_entity_builder_configuration_key)?,
                get_lock_configuration_method: cache.get_instance_method(&get_lock_configuration_key)?
            } )
        )
    }

    pub fn as_cedarling_boostrap_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<BootstrapConfig> {

        let app_name: String = {
            require_some(self.get_application_name(env)?,JAVA_CLS_NAME,"applicationName")?
        };
        
        let log_config: LogConfig = {
            let java_log_config = require_some(self.get_log_configuration(env)?,JAVA_CLS_NAME,"logConfiguration")?;
            java_log_config.as_cedarling_log_config(env)?
        };
        
        let policy_store_config: PolicyStoreConfig = {
            let java_ps_config=  require_some(self.get_policy_store_configuration(env)?,JAVA_CLS_NAME,"policyStoreConfiguration")?;
            java_ps_config.as_cedarling_policy_store_config(env)?
        };

        let jwt_config: JwtConfig = {
            let java_jwt_config = require_some(self.get_jwt_configuration(env)?,JAVA_CLS_NAME,"jwtConfiguration")?;
            java_jwt_config.as_cedarling_jwt_config(env)?
        };
        
        let authz_config: AuthorizationConfig = {
            let java_authz_config = require_some(self.get_authz_configuration(env)?,JAVA_CLS_NAME,"authzConfiguration")?;
            java_authz_config.as_cedarling_authz_config(env)?
        };

        let entity_builder_config: EntityBuilderConfig = {
            let java_eb_config = require_some(self.get_entity_builder_configuration(env)?,JAVA_CLS_NAME,"entityBuilderConfiguration")?;
            java_eb_config.as_cedarling_entity_builder_config(env)?
        };

        let lock_config: Option<LockServiceConfig> = self.get_lock_configuration(env)?
            .map(|val| val.as_cedarling_lock_service_config(env))
            .transpose()?;

        Ok(BootstrapConfig {
            application_name:  app_name,
            log_config: log_config,
            policy_store_config: policy_store_config,
            jwt_config: jwt_config,
            authorization_config: authz_config,
            entity_builder_config: entity_builder_config,
            lock_config: lock_config
        })
    }

    fn get_application_name (
        &self,
        env: &mut JNIEnv<'local>
    )  -> Result<Option<String>> {

        let method: &JMethodID = &self.get_application_name_method;
        let app_name = call_jni_object_method(env,&self.jobj,method,&[])?;
        java_string_to_native_string(env,&app_name)
    }

    
    fn get_log_configuration (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaLogConfig<'local>>> {

        let method: &JMethodID = &self.get_log_configuration_method;
        let log_config_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaLogConfig::new(log_config_obj)
    }

    
    fn get_policy_store_configuration (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaPolicyStoreConfig<'local>>> {

        let method: &JMethodID = &self.get_policy_store_configuration_method;
        let psconfig_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaPolicyStoreConfig::new (psconfig_obj)
    }

    
    fn get_jwt_configuration ( 
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaJwtConfig<'local>>> {

        let method: &JMethodID = &self.get_jwt_configuration_method;
        let jwt_config_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaJwtConfig::new (jwt_config_obj)
    }

    fn get_authz_configuration (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaAuthorizationConfig<'local>>> {

        let method: &JMethodID = &self.get_authorization_configuration_method;
        let authz_config_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaAuthorizationConfig::new (authz_config_obj)
    }

    fn get_entity_builder_configuration (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaEntityBuilderConfig<'local>>> {

        let method: &JMethodID = &self.get_entity_builder_configuration_method;
        let entity_builder_config_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaEntityBuilderConfig::new(entity_builder_config_obj)
    }

    fn get_lock_configuration (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaLockServiceConfig<'local>>> {

        let method: &JMethodID = &self.get_lock_configuration_method;
        let lock_config_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaLockServiceConfig::new(lock_config_obj)
    }
}