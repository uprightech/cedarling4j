// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::{Request};
use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache,JavaList};
use crate::jni::util::*;
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use jni::sys::{jvalue};

use std::collections::{HashMap};
use std::sync::{Mutex,LazyLock};
use super::{JavaContext,JavaEntityData};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/authz/AuthorizeRequest";

const JAVA_METHOD_NAME_GET_TOKEN: &str = "getToken";
const JAVA_METHOD_SIG_GET_TOKEN: &str  = "(Ljava/lang/String;)Ljava/lang/String;";
const JAVA_METHOD_NAME_GET_TOKEN_NAMES: &str = "getTokenNames";
const JAVA_METHOD_SIG_GET_TOKEN_NAMES: &str = "()Ljava/util/List;";
const JAVA_METHOD_NAME_GET_ACTION: &str = "getAction";
const JAVA_METHOD_SIG_GET_ACTION: &str = "()Ljava/lang/String;";
const JAVA_METHOD_NAME_GET_RESOURCE: &str = "getResource";
const JAVA_METHOD_SIG_GET_RESOURCE: &str = "()Lio/jans/cedarling/bridge/authz/EntityData;";
const JAVA_METHOD_NAME_GET_CONTEXT: &str = "getContext";
const JAVA_METHOD_SIG_GET_CONTEXT: &str = "()Lio/jans/cedarling/bridge/authz/Context;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaAuthorizeRequest <'local> {

    jobj: JObject<'local>,
    get_token_method: JMethodID,
    get_token_names_method: JMethodID,
    get_action_method: JMethodID,
    get_resource_method: JMethodID,
    get_context_method: JMethodID
}

impl <'local> JavaAuthorizeRequest <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class (
            env,
            JAVA_CLS_NAME
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_TOKEN,
            JAVA_METHOD_SIG_GET_TOKEN
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_TOKEN_NAMES,
            JAVA_METHOD_SIG_GET_TOKEN_NAMES
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ACTION,
            JAVA_METHOD_SIG_GET_ACTION
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_RESOURCE,
            JAVA_METHOD_SIG_GET_RESOURCE
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_CONTEXT,
            JAVA_METHOD_SIG_GET_CONTEXT
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaAuthorizeRequest<'local>>> {

        if jobj.is_null() {

            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

    
        let get_token_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_TOKEN,
            JAVA_METHOD_SIG_GET_TOKEN
        );

        let get_token_names_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_TOKEN_NAMES,
            JAVA_METHOD_SIG_GET_TOKEN_NAMES
        );

        let get_action_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ACTION,
            JAVA_METHOD_SIG_GET_ACTION
        );

        let get_resource_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_RESOURCE,
            JAVA_METHOD_SIG_GET_RESOURCE
        );

        let get_context_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_CONTEXT,
            JAVA_METHOD_SIG_GET_CONTEXT
        );
       
        Ok (
            Some ( JavaAuthorizeRequest {
                jobj: jobj,
                get_token_method: cache.get_instance_method(&get_token_key)?,
                get_token_names_method: cache.get_instance_method(&get_token_names_key)?,
                get_action_method: cache.get_instance_method(&get_action_key)?,
                get_resource_method: cache.get_instance_method(&get_resource_key)?,
                get_context_method: cache.get_instance_method(&get_context_key)?
            })
        )
    }

    pub fn as_cedarling_authz_request (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Request> {

        let tokens = self.get_tokens(env)?;
        let action = require_some(self.get_action(env)?,JAVA_CLS_NAME,"action")?;
        let resource = {
            let resource_obj = require_some(self.get_resource(env)?,JAVA_CLS_NAME,"resource")?;
            resource_obj.as_cedarling_entity_data(env)?
        };
        let context = {
            let context_obj = require_some(self.get_context(env)?,JAVA_CLS_NAME,"context")?;
            context_obj.as_cedarling_context(env)?
        };
        
        Ok( Request {
            tokens: tokens,
            action: action,
            resource: resource,
            context: context
        })
    }

    fn get_tokens (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<HashMap<String,String>> {

        let token_names_obj = { require_some(self.get_token_names(env)?,JAVA_CLS_NAME,"tokens")? };
        let token_count = { token_names_obj.size(env)? };
        let mut tokens: HashMap<String,String> = HashMap::new();
        for i in 0..token_count {
            let token_name_obj = token_names_obj.get(env,i)?;
            let token_name = java_string_to_native_string(env,&token_name_obj)?.ok_or_else (
                || CedarlingBridgeError::GenericError("null token name in authorization request".to_string())
            )?;

            let token_value = self.get_token(env,&token_name_obj)?.ok_or_else(
                || CedarlingBridgeError::GenericError(
                    format!("null value for token`{}` in authorization request",token_name)
                )
            )?;
            tokens.insert(token_name,token_value);
        }
        Ok(tokens)
    }

    fn get_token (
        &self,
        env: &mut JNIEnv<'local>,
        name_obj: &JObject<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_token_method;
        let args: [jvalue;1] = [jvalue{ l: name_obj.as_raw()}];
        call_jni_string_method(env,&self.jobj,method,&args)
    }

    fn get_token_names (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaList<'local>>> {

        let method: &JMethodID = &self.get_token_names_method;
        let token_names_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaList::new(token_names_obj)
    }

    fn get_action (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_action_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_resource (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaEntityData<'local>>> {

        let method: &JMethodID = &self.get_resource_method;
        let resource_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaEntityData::new(resource_obj)
    }

    fn get_context (
        &self, 
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaContext<'local>>> {

        let method: &JMethodID = &self.get_context_method;
        let context_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaContext::new(context_obj)
    }
}



