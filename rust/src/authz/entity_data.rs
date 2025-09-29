// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::*;
use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache};
use crate::jni::util::{java_string_to_native_string,call_jni_object_method,require_some};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};

use std::collections::{HashMap};
use std::sync::{Mutex,LazyLock};
use super::{JavaCedarEntityMapping};

type AttrType = HashMap<String,serde_json::Value>;

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/authz/EntityData";

const JAVA_METHOD_NAME_GET_CEDAR_MAPPING: &str = "getCedarMapping";
const JAVA_METHOD_SIG_GET_CEDAR_MAPPING: &str = "()Lio/jans/cedarling/bridge/authz/CedarEntityMapping;";

const JAVA_METHOD_NAME_GET_ATTRIBUTES: &str = "getAttributes";
const JAVA_METHOD_SIG_GET_ATTRIBUTES: &str = "()Ljava/lang/String;";


static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaEntityData<'local> {

    jobj: JObject<'local>,
    get_cedar_mapping_method: JMethodID,
    get_attributes_method: JMethodID
}

impl <'local> JavaEntityData <'local> {

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
            JAVA_METHOD_NAME_GET_CEDAR_MAPPING,
            JAVA_METHOD_SIG_GET_CEDAR_MAPPING
        )?;


        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ATTRIBUTES,
            JAVA_METHOD_SIG_GET_ATTRIBUTES
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaEntityData<'local>>> {

        if jobj.is_null() {

            return Ok(None);
        }

        let get_cedar_mapping_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_CEDAR_MAPPING,
            JAVA_METHOD_SIG_GET_CEDAR_MAPPING
        );

        let get_attributes_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ATTRIBUTES,
            JAVA_METHOD_SIG_GET_ATTRIBUTES
        );

        let cache = LOCAL_JNI_CACHE.lock()?;
        
        Ok( Some(JavaEntityData {
            jobj: jobj,
            get_cedar_mapping_method: cache.get_instance_method(&get_cedar_mapping_key)?,
            get_attributes_method: cache.get_instance_method(&get_attributes_key)?
        }))
    }

    pub fn as_cedarling_entity_data (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<EntityData> {

        let cedar_mapping: CedarEntityMapping = {
            
            let cedar_mapping_obj = require_some(self.get_cedar_entity_mapping(env)?,JAVA_CLS_NAME,"cedar_mapping")?;
            cedar_mapping_obj.as_cedarling_cedar_entity_mapping(env)?
        };
        let attributes: AttrType = {
            let attr_str = require_some(self.get_attributes(env)?,JAVA_CLS_NAME,"attributes")?;
            serde_json::from_str::<'_,AttrType>(&attr_str).map_err(|e|{
                CedarlingBridgeError::JsonError {
                    additional_description: "Parsing authz request attributes failed".to_string(),
                    json_err: e
                }
            })?
        };
        Ok(EntityData {
            cedar_mapping: cedar_mapping,
            attributes: attributes
        })
    }

    fn get_cedar_entity_mapping (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaCedarEntityMapping<'local>>> {

        let method: &JMethodID = &self.get_cedar_mapping_method;
        let cedar_mapping_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaCedarEntityMapping::new(cedar_mapping_obj)
    }

    

    fn get_attributes (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_attributes_method;
        let attrs_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        java_string_to_native_string(env,&attrs_obj)
    }
}