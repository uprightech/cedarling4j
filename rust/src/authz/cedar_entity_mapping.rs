// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::*;

use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::*;
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/authz/CedarEntityMapping";

const JAVA_METHOD_NAME_GET_ID: &str = "getId";
const JAVA_METHOD_SIG_GET_ID: &str = "()Ljava/lang/String;";
const JAVA_METHOD_NAME_GET_ENTITY_TYPE: &str = "getEntityType";
const JAVA_METHOD_SIG_GET_ENTITY_TYPE: &str = "()Ljava/lang/String;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaCedarEntityMapping<'local> {

    jobj: JObject<'local>,
    get_id_method: JMethodID,
    get_entity_type_method: JMethodID
}

impl <'local> JavaCedarEntityMapping <'local> {

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
            JAVA_METHOD_NAME_GET_ID,
            JAVA_METHOD_SIG_GET_ID
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ENTITY_TYPE,
            JAVA_METHOD_SIG_GET_ENTITY_TYPE
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaCedarEntityMapping<'local>>> {

        if jobj.is_null() {

            return Ok(None);
        }

        let get_id_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ID,
            JAVA_METHOD_SIG_GET_ID
        );

        let get_entity_type_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ENTITY_TYPE,
            JAVA_METHOD_SIG_GET_ENTITY_TYPE
        );

        let cache = LOCAL_JNI_CACHE.lock()?;

        Ok(Some ( JavaCedarEntityMapping {
            jobj: jobj, 
            get_id_method: cache.get_instance_method(&get_id_key)?,
            get_entity_type_method: cache.get_instance_method(&get_entity_type_key)?
        }))
    }

    pub fn as_cedarling_cedar_entity_mapping (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<CedarEntityMapping> {

        let id: String = require_some(self.get_id(env)?,JAVA_CLS_NAME,"id")?;
        let entity_type: String = require_some(self.get_entity_type(env)?,JAVA_CLS_NAME,"entity_type")?;
        Ok(CedarEntityMapping {
            id: id,
            entity_type: entity_type
        })
    }

    fn get_id (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_id_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_entity_type (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_entity_type_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }
}

