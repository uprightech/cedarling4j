// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache};
use jni::JNIEnv;
use jni::objects::{JClass,JMethodID,JObject,JStaticFieldID,JString};
use jni::signature::{JavaType,ReturnType,Primitive};
use jni::sys::{jint,jlong,jvalue};
use std::sync::{Mutex,LazyLock};

const CEDARLING_ERROR_CLSNAME: &str = "io/jans/cedarling/bridge/config/CedarlingError";
const CEDARLING_CONFIGURATION_ERROR_CLSNAME: &str = "io/jans/cedarling/bridge/config/CedarlingConfigurationError";
const CEDARLING_AUTHORIZATION_ERROR_CLSNAME: &str = "io/jans/cedarling/bridge/authz/CedarlingAuthorizationError";
const JAVA_OBJECT_CLSNAME: &str = "java/lang/Object";
const JAVA_OBJECT_METHOD_NAME_TO_STRING: &str = "toString";

const JAVA_LONG_CLSNAME: &str = "java/lang/Long";
const JAVA_OBJECT_METHOD_SIG_TO_STRING: &str = "()Ljava/lang/String;";
const JAVA_LONG_METHOD_NAME_LONG_VALUE: &str = "longValue";
const JAVA_LONG_METHOD_SIG_LONG_VALUE: &str = "()J";


static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) fn jni_cache_init<'local> (
    env: &mut JNIEnv<'local>
) -> Result<()> {

    let mut cache = LOCAL_JNI_CACHE.lock()?;

    cache.add_class(env,JAVA_OBJECT_CLSNAME)?;
    
    cache.add_instance_method(
        env,
        JAVA_OBJECT_CLSNAME,
        JAVA_OBJECT_METHOD_NAME_TO_STRING,
        JAVA_OBJECT_METHOD_SIG_TO_STRING
    )?;

    cache.add_instance_method (
        env,
        JAVA_LONG_CLSNAME,
        JAVA_LONG_METHOD_NAME_LONG_VALUE,
        JAVA_LONG_METHOD_SIG_LONG_VALUE
    )
}

pub (crate) fn call_jni_object_constructor<'local> (
    env: &mut JNIEnv<'local>,
    class: &JClass<'local>,
    ctor_id: &JMethodID,
    args: &[jvalue]
) -> Result<JObject<'local>> {

    Ok(unsafe { env.new_object_unchecked(class,ctor_id.clone(),args)? } )
}

pub (crate) fn call_jni_object_method<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>,
    method: &JMethodID,
    args: &[jvalue]
) -> Result<JObject<'local>> {

    let return_type: ReturnType = ReturnType::Object;
    let obj = unsafe { env.call_method_unchecked(obj,method,return_type,args)? };
    Ok(obj.l()?)
}

pub (crate) fn call_jni_void_method<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>,
    method: &JMethodID,
    args: &[jvalue]
) -> Result<()> {

    let return_type: ReturnType = ReturnType::Primitive(Primitive::Void);
    Ok( unsafe { env.call_method_unchecked(obj,method,return_type,args)?; } )
}

pub (crate) fn call_jni_long_method<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>,
    method: &JMethodID,
    args: &[jvalue]
) -> Result<jlong> {

    let return_type: ReturnType  = ReturnType::Primitive(Primitive::Long);
    let obj = unsafe { env.call_method_unchecked(obj,method,return_type,args)? };
    Ok(obj.j()?)
}

pub (crate) fn call_jni_opt_long_method<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>,
    method: &JMethodID,
    args: &[jvalue]
) -> Result<Option<jlong>> {

    let tmp_obj = call_jni_object_method(env,obj,method,args)?;
    if tmp_obj.is_null() {
        return Ok(None);
    }

    let cache = LOCAL_JNI_CACHE.lock()?;
    let long_value_key = (
        JAVA_LONG_CLSNAME,
        JAVA_LONG_METHOD_NAME_LONG_VALUE,
        JAVA_LONG_METHOD_SIG_LONG_VALUE
    );

    let long_value_method: JMethodID = cache.get_instance_method(&long_value_key)?;
    Ok( Some(call_jni_long_method(env,&tmp_obj,&long_value_method,&[])?) )

}

pub (crate) fn call_jni_int_method<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>,
    method: &JMethodID,
    args: &[jvalue]
) -> Result<jint> {

    let return_type: ReturnType = ReturnType::Primitive(Primitive::Int);
    Ok(unsafe { env.call_method_unchecked(obj,method,return_type,args)?.i()?})
}

pub (crate) fn call_jni_string_method<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>,
    method: &JMethodID,
    args: &[jvalue]
) -> Result<Option<String>> {

    let ret = call_jni_object_method(env,obj,method,args)?;
    java_string_to_native_string(env,&ret)
}

pub (crate) fn call_jni_bool_method<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>,
    method: &JMethodID,
    args: &[jvalue]
) -> Result<bool> {

    let return_type: ReturnType = ReturnType::Primitive(Primitive::Boolean);
    Ok(unsafe{ env.call_method_unchecked(obj,method,return_type,args)?.z()? })
}

pub (crate) fn java_string_to_native_string<'local> (
    env: &mut JNIEnv<'local>,
    java_string: &JObject<'local>
) -> Result<Option<String>> {

    if java_string.is_null() {

        return Ok(None)
    }

    let jstring_obj = unsafe { JString::from_raw(java_string.as_raw()) };
    let obj = unsafe{ env.get_string_unchecked(&jstring_obj)? };
    let str = obj.to_string_lossy().into_owned();
    Ok(Some(str))
}

pub (crate) fn call_jni_method_to_string<'local> (
    env: &mut JNIEnv<'local>,
    obj: &JObject<'local>
) -> Result<Option<String>> {

    let cache = LOCAL_JNI_CACHE.lock()?;

    let to_string_key = (
        JAVA_OBJECT_CLSNAME,
        JAVA_OBJECT_METHOD_NAME_TO_STRING,
        JAVA_OBJECT_METHOD_SIG_TO_STRING
    );

    let method_id : JMethodID = cache.get_instance_method(&to_string_key)?;
    let res_as_obj = call_jni_object_method(env,obj,&method_id,&[])?;
    java_string_to_native_string(env,&res_as_obj)

}

pub (crate) fn get_static_field_object_value<'local> (
    env: &mut JNIEnv<'local>,
    class: &JClass,
    field_id: &JStaticFieldID,
    object_type: &str
) -> Result<JObject<'local>> {

    let java_type: JavaType = JavaType::Object(String::from(object_type));
    let field_obj = env.get_static_field_unchecked(class,field_id,java_type)?;
    Ok(field_obj.l()?)
}

pub (crate) fn throw_cedarling_error<'local> (
    env: &mut JNIEnv<'local>,
    message: &str
) -> Result<()> {
    Ok(env.throw_new(CEDARLING_ERROR_CLSNAME,message)?)
}

pub (crate) fn throw_cedarling_configuration_error<'local> (
    env: &mut JNIEnv<'local>,
    message: &str
) -> Result<()> {
    Ok(env.throw_new(CEDARLING_CONFIGURATION_ERROR_CLSNAME,message)?)
}

pub (crate) fn throw_cedarling_authorization_error<'local> (
    env: &mut JNIEnv<'local>,
    message: &str
) -> Result<()> {
    Ok(env.throw_new(CEDARLING_AUTHORIZATION_ERROR_CLSNAME,message)?)
}


pub (crate) fn require_some<T> (
    val: Option<T>,
    class: &'static str,
    field: &'static str
) -> Result<T> {
    
    val.ok_or_else(|| CedarlingBridgeError::FieldCannotBeNull{ field: field, class: class})
}