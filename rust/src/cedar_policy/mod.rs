pub (crate) mod authz_decision;
pub (crate) mod authz_error;
pub (crate) mod diagnostics;
pub (crate) mod policy_id;
pub (crate) mod policy_response;


pub (crate) use authz_decision::JavaAuthzDecision as JavaAuthzDecision;
pub (crate) use authz_error::JavaAuthzError as JavaAuthzError;
pub (crate) use diagnostics::JavaDiagnostics as JavaDiagnostics;
pub (crate) use policy_id::JavaPolicyId as JavaPolicyId;
pub (crate) use policy_response::JavaPolicyResponse as JavaPolicyResponse;

use jni::JNIEnv;
use crate::{Result};

pub (crate) fn jni_cache_init<'local> (
    env: &mut JNIEnv<'local>
) -> Result<()> {

    JavaAuthzDecision::jni_cache_init(env)?;
    JavaAuthzError::jni_cache_init(env)?;
    JavaDiagnostics::jni_cache_init(env)?;
    JavaPolicyId::jni_cache_init(env)?;
    JavaPolicyResponse::jni_cache_init(env)
}