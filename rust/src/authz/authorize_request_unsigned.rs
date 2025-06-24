use cedarling::{RequestUnsigned,EntityData};
use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache,JavaList};
use crate::jni::util::*;
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};

use std::sync::{Mutex,LazyLock};
use super::{JavaContext,JavaEntityData};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/authz/AuthorizeRequestUnsigned";

const JAVA_METHOD_NAME_GET_PRINCIPALS: &str = "getPrincipals";
const JAVA_METHOD_SIG_GET_PRINCIPALS: &str = "()Ljava/util/List;";
const JAVA_METHOD_NAME_GET_ACTION: &str = "getAction";
const JAVA_METHOD_SIG_GET_ACTION: &str = "()Ljava/lang/String;";
const JAVA_METHOD_NAME_GET_RESOURCE: &str = "getResource";
const JAVA_METHOD_SIG_GET_RESOURCE: &str = "()Lio/jans/cedarling/bridge/authz/EntityData;";
const JAVA_METHOD_NAME_GET_CONTEXT: &str = "getContext";
const JAVA_METHOD_SIG_GET_CONTEXT: &str = "()Lio/jans/cedarling/bridge/authz/Context;";


static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaAuthorizeRequestUnsigned <'local> {

    jobj: JObject<'local>,
    get_principals_method: JMethodID,
    get_action_method: JMethodID,
    get_resource_method: JMethodID,
    get_context_method: JMethodID
}

impl <'local> JavaAuthorizeRequestUnsigned <'local> {

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
            JAVA_METHOD_NAME_GET_PRINCIPALS,
            JAVA_METHOD_SIG_GET_PRINCIPALS
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
    ) -> Result<Option<JavaAuthorizeRequestUnsigned<'local>>> {

        if jobj.is_null() {

            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_principals_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_PRINCIPALS,
            JAVA_METHOD_SIG_GET_PRINCIPALS
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
            Some (JavaAuthorizeRequestUnsigned {
                jobj: jobj,
                get_principals_method: cache.get_instance_method(&get_principals_key)?,
                get_action_method: cache.get_instance_method(&get_action_key)?,
                get_resource_method: cache.get_instance_method(&get_resource_key)?,
                get_context_method: cache.get_instance_method(&get_context_key)?,
            })
        )
    }

    pub fn as_cedarling_authz_request_unsigned (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<RequestUnsigned> {

        let principals = self.get_cedarling_principals(env)?;
        let action = require_some(self.get_action(env)?,JAVA_CLS_NAME,"action")?;
        let resource = {
            let resource_obj = require_some(self.get_resource(env)?,JAVA_CLS_NAME,"resource")?;
            resource_obj.as_cedarling_entity_data(env)?
        };
        let context = {
            let context_obj = require_some(self.get_context(env)?,JAVA_CLS_NAME,"context")?;
            context_obj.as_cedarling_context(env)?
        };

        Ok( RequestUnsigned {
            principals: principals,
            action: action,
            resource: resource,
            context: context
        })
    }

    fn get_cedarling_principals (
        &self, 
        env: &mut JNIEnv<'local>
    ) -> Result<Vec<EntityData>> {

        let principals_list = require_some(self.get_principals(env)?,JAVA_CLS_NAME,"principals")?;
        let principals_count = principals_list.size(env)?;

        let mut ret: Vec<EntityData>  = Vec::with_capacity(principals_count as usize);
        for i in 0..principals_count {
            let principal =  {
                JavaEntityData::new(principals_list.get(env,i)?)?.ok_or_else (
                    || CedarlingBridgeError::GenericError("null principal specified in unsigned authorization request".to_string())
                )?
            };
            ret.push(principal.as_cedarling_entity_data(env)?);
        }
        Ok(ret)
    }

    fn get_principals (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaList<'local>>> {

        let method: &JMethodID = &self.get_principals_method;
        let principals_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaList::new(principals_obj)
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