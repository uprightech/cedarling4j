// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::{BootstrapConfig,Request,RequestUnsigned};
use cedarling::blocking::Cedarling;
use jni::JNIEnv;
use jni::objects::{JClass,JObject};
use jni::sys::{jobject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::util::*;
use crate::config::{JavaBootstrapConfig};
use crate::authz::{JavaAuthorizeRequest,JavaAuthorizeRequestUnsigned,JavaAuthorizeResult};
use std::sync::{MutexGuard};

const CEDARLING_INTERNAL_REF_FIELD: &str = "cedarlingRef";

#[no_mangle]
pub extern "system" fn Java_io_jans_cedarling_bridge_Cedarling_initJniCache<'local> (
    mut env: JNIEnv<'local>,
    cls_cedarling: JClass<'local>
) -> () {

    let _ = init_cache(&mut env,cls_cedarling);
    ()
}


#[no_mangle]
pub extern "system" fn Java_io_jans_cedarling_bridge_Cedarling_createNativeCedarling<'local> (
    mut env: JNIEnv<'local>,
    cedarling_obj: JObject<'local>,
    config_obj: JObject<'local>
) -> () {
   
    match new_cedarling_instance(&mut env,cedarling_obj,config_obj) {
        Ok(_) => (),
        Err(err) => {
            let errmsg = format!("Could not create rust cedarling instance. {}",err.to_string());
            let _ = throw_cedarling_configuration_error(&mut env,&errmsg);
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_io_jans_cedarling_bridge_Cedarling_authorize<'local> (
    mut env: JNIEnv<'local>,
    cedarling_obj: JObject<'local>,
    request_obj: JObject<'local>
) -> jobject {
    
    match cedarling_authorize(&mut env, cedarling_obj,request_obj) {
        Ok(ret) => ret.into_raw(),
        Err(err) => {
            let errmsg = format!("Cedarling authorization failed. {}",err.to_string());
            let _ = throw_cedarling_authorization_error(&mut env,&errmsg);
            JObject::null().into_raw()
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_io_jans_cedarling_bridge_Cedarling_authorizeUnsigned<'local> (
    mut env: JNIEnv<'local>,
    cedarling_obj: JObject<'local>,
    unsigned_request_obj: JObject<'local>
) -> jobject {

    match cedarling_authorize_unsigned(&mut env, cedarling_obj, unsigned_request_obj) {
        Ok(ret) => ret.into_raw(),
        Err(err) => {
            let errmsg = format!("Cedarling unsigned authorization failed. {}",err.to_string());
            let _ = throw_cedarling_authorization_error(&mut env,&errmsg);
            JObject::null().into_raw()
        }
    }
}

#[no_mangle]
pub extern "system" fn Java_io_jans_cedarling_bridge_Cedarling_cleanupCedarling<'local> (
    mut env: JNIEnv<'local>,
    cedarling_obj: JObject<'local>
) -> () {

    let _ = cedarling_cleanup(&mut env,cedarling_obj).or_else(|e| {
        let errstr = format!("Could not release cedarling rust resources. {}",e.to_string());
        throw_cedarling_error(&mut env,&errstr)
    });
}


fn init_cache<'local> (
    env: &mut JNIEnv<'local>,
    _cedarling_cls: JClass<'local>
) -> Result<()> {

    crate::jni::jni_cache_init(env)?;
    crate::config::jni_cache_init(env)?;
    crate::authz::jni_cache_init(env)?;
    crate::cedar_policy::jni_cache_init(env)
}

fn new_cedarling_instance<'local> (
    env: &mut JNIEnv<'local>,
    cedarling_obj: JObject<'local>,
    config_obj: JObject<'local>
) -> Result<()> {

    let jbootstrap_config_wrapper: JavaBootstrapConfig = JavaBootstrapConfig::new(config_obj)?
        .ok_or_else( || CedarlingBridgeError::GenericError("Java BootstrapConfiguration cannot be null".to_string()) )?;
    
    let bootstrap_config: BootstrapConfig = jbootstrap_config_wrapper.as_cedarling_boostrap_config(env)?;
    
    let cedarling = Cedarling::new(&bootstrap_config).map_err(|e| { CedarlingBridgeError::GenericError(e.to_string())})?;

    Ok( unsafe { env.set_rust_field(cedarling_obj,CEDARLING_INTERNAL_REF_FIELD,cedarling)? } )
}

fn cedarling_cleanup<'local> (
    env: &mut JNIEnv<'local>,
    cedarling_obj: JObject<'local>
) -> Result<()> {

    match unsafe { env.take_rust_field::<&JObject<'_>,&str,Cedarling>(&cedarling_obj,CEDARLING_INTERNAL_REF_FIELD)  } {
        Ok(_) => Ok(()),
        Err(err) => Err(CedarlingBridgeError::GenericError(err.to_string()))
    }
}

fn cedarling_authorize<'local> (
    env: &mut JNIEnv<'local>,
    cedarling_obj: JObject<'local>,
    request_obj: JObject<'local>
) -> Result<JObject<'local>> {

    let request: Request = {
        let request_wrapper = JavaAuthorizeRequest::new(request_obj)?.ok_or_else(
            || CedarlingBridgeError::GenericError("Java authz request cannot be null".to_string()) 
        )?;
        request_wrapper.as_cedarling_authz_request(env)?
    };

    let authz_result = {

        let mut guard: MutexGuard<'_,Cedarling> = unsafe { env.get_rust_field(cedarling_obj,CEDARLING_INTERNAL_REF_FIELD)? };
        let cedarling: &mut Cedarling = &mut guard;
        cedarling.authorize(request)?
    };
    
    JavaAuthorizeResult::new(&authz_result)?.as_java_object(env)
}

fn cedarling_authorize_unsigned<'local> (
    env: &mut JNIEnv<'local>,
    cedarling_obj: JObject<'local>,
    unsigned_request_obj: JObject<'local>
) -> Result<JObject<'local>> {

    let request: RequestUnsigned = {
        let request_wrapper = JavaAuthorizeRequestUnsigned::new(unsigned_request_obj)?.ok_or_else(
            || CedarlingBridgeError::GenericError("Java unsigned authz request cannot be null".to_string()) 
        )?;
        request_wrapper.as_cedarling_authz_request_unsigned(env)?
    };

    let authz_result = {

        let mut guard: MutexGuard<'_,Cedarling> = unsafe { env.get_rust_field(cedarling_obj,CEDARLING_INTERNAL_REF_FIELD)? };
        let cedarling: &mut Cedarling = &mut guard;
        cedarling.authorize_unsigned(request)?
    };

    JavaAuthorizeResult::new(&authz_result)?.as_java_object(env)
}