// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::*;
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};

use crate::{Result};
use crate::config::*;
use crate::jni::util::*;
use crate::jni::{JniCache};


use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/EntityBuilderConfiguration";

const JAVA_METHOD_NAME_GET_BUILD_WORKLOAD: &str = "getBuildWorkload";
const JAVA_METHOD_SIG_GET_BUILD_WORKLOAD: &str = "()Z";

const JAVA_METHOD_NAME_GET_BUILD_USER: &str = "getBuildUser";
const JAVA_METHOD_SIG_GET_BUILD_USER: &str = "()Z";

const JAVA_METHOD_NAME_GET_ENTITY_NAMES: &str = "getEntityNames";
const JAVA_METHOD_SIG_GET_ENTITY_NAMES: &str = "()Lio/jans/cedarling/bridge/config/EntityNames;";

//const JAVA_METHOD_NAME_GET_UNSIGNED_ROLE_ID_SRC: &str = "getUnsignedRoleIdSrc";
//const JAVA_METHOD_SIG_GET_UNSIGNED_ROLE_ID_SRC: &str = "()Lio/jans/cedarling/bridge/config/UnsignedRoleIdSrc;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaEntityBuilderConfig<'local> {

    jobj: JObject<'local>,
    get_build_workload_method: JMethodID,
    get_build_user_method: JMethodID,
    get_entity_names_method: JMethodID,
    //get_unsigned_role_id_src_method: JMethodID
}

impl <'local> JavaEntityBuilderConfig <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(
            env,
            JAVA_CLS_NAME
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_BUILD_WORKLOAD,
            JAVA_METHOD_SIG_GET_BUILD_WORKLOAD
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_BUILD_USER,
            JAVA_METHOD_SIG_GET_BUILD_USER
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ENTITY_NAMES,
            JAVA_METHOD_SIG_GET_ENTITY_NAMES
        )

        /*cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_UNSIGNED_ROLE_ID_SRC,
            JAVA_METHOD_SIG_GET_UNSIGNED_ROLE_ID_SRC
        )
        */
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaEntityBuilderConfig<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache =  LOCAL_JNI_CACHE.lock()?;

        let get_build_workload_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_BUILD_WORKLOAD,
            JAVA_METHOD_SIG_GET_BUILD_WORKLOAD
        );

        let get_build_user_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_BUILD_USER,
            JAVA_METHOD_SIG_GET_BUILD_USER
        );

        let get_entity_names_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ENTITY_NAMES,
            JAVA_METHOD_SIG_GET_ENTITY_NAMES
        );


        /*let get_unsigned_role_id_src_key = (
            JAVA_CLS_NAME.to_string(),
            JAVA_METHOD_NAME_GET_UNSIGNED_ROLE_ID_SRC.to_string(),
            JAVA_METHOD_SIG_GET_UNSIGNED_ROLE_ID_SRC.to_string()
        );*/

        Ok (
            Some ( JavaEntityBuilderConfig {
                jobj: jobj,
                get_build_workload_method: cache.get_instance_method(&get_build_workload_key)?,
                get_build_user_method: cache.get_instance_method(&get_build_user_key)?,
                get_entity_names_method: cache.get_instance_method(&get_entity_names_key)?,
                //get_unsigned_role_id_src_method: cache.get_instance_method(&get_unsigned_role_id_src_key)?,
            } )
        )
    }

    pub fn as_cedarling_entity_builder_config(
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<EntityBuilderConfig> {

        let build_workload: bool = self.get_build_workload(env)?;
        let build_user: bool = self.get_build_user(env)?;

        let entity_names: EntityNames = {
            let entity_names_obj = require_some(self.get_entity_names(env)?,JAVA_CLS_NAME,"entityNames")?;
            entity_names_obj.as_cedarling_entity_names(env)?
        };

        /*let unsigned_role_id_src: UnsignedRoleIdSrc = {
            let unsigned_role_id_src_obj = require_some(self.get_unsigned_role_id_src(env)?,JAVA_CLS_NAME,"unsignedRoleIdSrc")?;
            unsigned_role_id_src_obj.as_cedarling_unsigned_role_id_src(env)?
        };
        */

        let mut ret = EntityBuilderConfig::default();
        ret.build_workload = build_workload;
        ret.build_user = build_user;
        ret.entity_names = entity_names;

        Ok (ret)
    }

    fn get_build_workload (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_build_workload_method;
        call_jni_bool_method(env,&self.jobj,method,&[])
    }

    fn get_build_user (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_build_user_method;
        call_jni_bool_method(env,&self.jobj,method,&[])
    }

    fn get_entity_names (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaEntityNames<'local>>> {

        let method: &JMethodID = &self.get_entity_names_method;
        let entity_name_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaEntityNames::new(entity_name_obj)
    }

    /*fn get_unsigned_role_id_src (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaUnsignedRoleIdSrc<'local>>> {

        let method: &JMethodID = &self.get_unsigned_role_id_src_method;
        let unsigned_role_id_src_obj = unsafe { call_jni_object_method(env,&self.jobj,method,&[])? };
        Ok(JavaUnsignedRoleIdSrc::new(env,unsigned_role_id_src_obj))
    }*/
}
