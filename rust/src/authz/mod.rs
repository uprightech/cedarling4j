pub (crate) mod authorize_request;
pub (crate) mod authorize_request_unsigned;
pub (crate) mod authorize_result;
pub (crate) mod context;
pub (crate) mod entity_data;

pub (crate) use authorize_request::JavaAuthorizeRequest as JavaAuthorizeRequest;
pub (crate) use authorize_request_unsigned::JavaAuthorizeRequestUnsigned as JavaAuthorizeRequestUnsigned;
pub (crate) use authorize_result::JavaAuthorizeResult as JavaAuthorizeResult;
pub (crate) use context::JavaContext as JavaContext;
pub (crate) use entity_data::JavaEntityData as JavaEntityData;

use jni::JNIEnv;
use crate::{Result};

pub (crate) fn jni_cache_init<'local> (
    env: &mut JNIEnv<'local>
) -> Result<()> {

    JavaAuthorizeRequest::jni_cache_init(env)?;
    JavaAuthorizeRequestUnsigned::jni_cache_init(env)?;
    JavaAuthorizeResult::jni_cache_init(env)?;
    JavaEntityData::jni_cache_init(env)?;
    JavaContext::jni_cache_init(env)
}