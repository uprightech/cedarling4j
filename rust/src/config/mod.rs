use jni::JNIEnv;
use crate::{Result};

pub (crate) mod authorization_config;
pub (crate) mod bootstrap_config;
pub (crate) mod entity_builder_config;
pub (crate) mod entity_names;
pub (crate) mod id_token_trust_mode;
pub (crate) mod json_rule;
pub (crate) mod jwt_algorithm;
pub (crate) mod jwt_config;
pub (crate) mod lock_service_config;
pub (crate) mod log_config;
pub (crate) mod log_level;
pub (crate) mod log_type;
pub (crate) mod memory_log_config;
pub (crate) mod policy_store_config;
pub (crate) mod policy_store_source;

//pub (crate) mod unsigned_role_id_src;

pub (crate) use authorization_config::JavaAuthorizationConfig as JavaAuthorizationConfig;
pub (crate) use bootstrap_config::JavaBootstrapConfig as JavaBootstrapConfig;
pub (crate) use entity_builder_config::JavaEntityBuilderConfig as JavaEntityBuilderConfig;
pub (crate) use entity_names::JavaEntityNames as JavaEntityNames;
pub (crate) use id_token_trust_mode::JavaIdTokenTrustMode as JavaIdTokenTrustMode;
pub (crate) use json_rule::JavaJsonRule as JavaJsonRule;
pub (crate) use jwt_algorithm::JavaJwtAlgorithm as JavaJwtAlgorithm;
pub (crate) use jwt_config::JavaJwtConfig as JavaJwtConfig;
pub (crate) use lock_service_config::JavaLockServiceConfig as JavaLockServiceConfig;
pub (crate) use log_config::JavaLogConfig as JavaLogConfig;
pub (crate) use log_level::JavaLogLevel as JavaLogLevel;
pub (crate) use log_type::JavaLogType as JavaLogType;
pub (crate) use memory_log_config::JavaMemoryLogConfig as JavaMemoryLogConfig;
pub (crate) use policy_store_config::JavaPolicyStoreConfig as JavaPolicyStoreConfig;
pub (crate) use policy_store_source::JavaPolicyStoreSource as JavaPolicyStoreSource;
//pub (crate) use unsigned_role_id_src as JavaUnsignedRoleIdSrc;

pub (crate) fn jni_cache_init<'local> (
    env: &mut JNIEnv<'local>
) -> Result<()> {

    
    JavaAuthorizationConfig::jni_cache_init(env)?;
    JavaBootstrapConfig::jni_cache_init(env)?;
    JavaEntityBuilderConfig::jni_cache_init(env)?;
    JavaEntityNames::jni_cache_init(env)?;
    JavaJsonRule::jni_cache_init(env)?;
    JavaJwtConfig::jni_cache_init(env)?;
    JavaLockServiceConfig::jni_cache_init(env)?;
    JavaLogConfig::jni_cache_init(env)?;
    JavaMemoryLogConfig::jni_cache_init(env)?;
    JavaPolicyStoreConfig::jni_cache_init(env)
    //JavaUnsignedRoleIdSrc::jni_cache_init(env)
}