// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use thiserror::Error;

#[derive(Error,Debug)]
pub (crate) enum CedarlingBridgeError {

    #[error("{0}")]
    GenericError(String),

    #[error("JNI error: {0}")]
    Jni(#[from] jni::errors::Error),
    
    #[error("Unknown value specified for java enum `{enum_cls}`. Value: `{value}`")]
    UnknownEnumValue{enum_cls: &'static str, value: String},

    #[error("Field `{field}` in java class `{class}` cannot be null")]
    FieldCannotBeNull{field: &'static str, class: &'static str},

    #[error("Json error: {additional_description}. {json_err}")]
    JsonError{ additional_description: String, json_err: serde_json::Error},

    #[error(transparent)]
    CedarlingAuthError(#[from] cedarling::AuthorizeError),

    #[error("Class `{0}` not found in cache")]
    CachedClassNotFound(String),

    #[error("Instance method `{method}` not found in cache for class `{class}`")]
    CachedInstanceMethodNotFound{ class: String, method: String},

    #[error("Static field `{field}` not found in cache for class `{class}`")]
    CachedStaticFieldNotFound{ class: String, field: String},

    #[error(transparent)]
    CacheMutexError(#[from] std::sync::PoisonError<std::sync::MutexGuard<'static,crate::jni::JniCache>>),

    #[error(transparent)]
    UrlParseError(#[from] url::ParseError)
}