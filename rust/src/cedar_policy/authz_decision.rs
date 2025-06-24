use cedar_policy::{Decision};
use jni::JNIEnv;
use jni::objects::{JClass,JObject,JStaticFieldID};
use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::*;

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/cedar/policy/AuthzDecision";

const JAVA_AUTHZ_DECISION_ALLOW: &str = "ALLOW";
const JAVA_AUTHZ_DECISION_DENY: &str = "DENY";

const JAVA_AUTHZ_DECISION_VALUE_SIGNATURE: &str = "Lio/jans/cedarling/bridge/cedar/policy/AuthzDecision;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaAuthzDecision;

impl JavaAuthzDecision {

    pub fn jni_cache_init<'local> (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(
            env,
            JAVA_CLS_NAME
        )?;

        cache.add_static_field (
            env,
            JAVA_CLS_NAME,
            JAVA_AUTHZ_DECISION_ALLOW,
            JAVA_AUTHZ_DECISION_VALUE_SIGNATURE
        )?;

        cache.add_static_field (
            env,
            JAVA_CLS_NAME,
            JAVA_AUTHZ_DECISION_DENY,
            JAVA_AUTHZ_DECISION_VALUE_SIGNATURE
        )
    }

    pub fn as_java_object<'local> (
        env: &mut JNIEnv<'local>,
        decision: &Decision
    ) -> Result<JObject<'local>> {

        match decision {
            Decision::Allow => JavaAuthzDecision::allow_value(env),
            Decision::Deny  => JavaAuthzDecision::deny_value(env),
        }
    }

    fn allow_value<'local> (
        env: &mut JNIEnv<'local>
    ) -> Result<JObject<'local>> {

        let cache = LOCAL_JNI_CACHE.lock()?;
        let class: JClass<'local> = cache.get_class(env,JAVA_CLS_NAME)?;
        let allow_key = (
            JAVA_CLS_NAME,
            JAVA_AUTHZ_DECISION_ALLOW,
            JAVA_AUTHZ_DECISION_VALUE_SIGNATURE
        );

        let field_id: JStaticFieldID = cache.get_static_field(&allow_key)?;
        get_static_field_object_value(env,&class,&field_id,JAVA_CLS_NAME)
    }

    fn deny_value<'local> (
        env: &mut JNIEnv<'local>
    ) -> Result<JObject<'local>> {

        let cache = LOCAL_JNI_CACHE.lock()?;
        let class: JClass<'local> = cache.get_class(env,JAVA_CLS_NAME)?;
        let deny_key = (
            JAVA_CLS_NAME,
            JAVA_AUTHZ_DECISION_DENY,
            JAVA_AUTHZ_DECISION_VALUE_SIGNATURE
        );

        let field_id: JStaticFieldID = cache.get_static_field(&deny_key)?;
        get_static_field_object_value(env,&class,&field_id,JAVA_CLS_NAME)
    }
}