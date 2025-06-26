// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

pub (crate) mod config;
pub (crate) mod authz;
pub (crate) mod cedarling;
pub (crate) mod jni;
pub (crate) mod error;
pub (crate) mod cedar_policy;

pub (crate) use error::CedarlingBridgeError as CedarlingBridgeError;
pub (crate) type Result<T> = core::result::Result<T,CedarlingBridgeError>;
