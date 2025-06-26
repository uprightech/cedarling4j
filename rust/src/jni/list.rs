// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use jni::sys::{jint,jvalue};

use crate::{Result};
use crate::jni::util::*;
use crate::jni::{JniCache};

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "java/util/List";

const JAVA_METHOD_NAME_SIZE: &str = "size";
const JAVA_METHOD_SIG_SIZE: &str  = "()I";

const JAVA_METHOD_NAME_GET: &str  = "get";
const JAVA_METHOD_SIG_GET: &str   = "(I)Ljava/lang/Object;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaList<'local> {
    jobj: JObject<'local>,
    size_method: JMethodID,
    get_method: JMethodID
}

impl <'local> JavaList <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>,
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class (
            env,
            JAVA_CLS_NAME
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SIZE,
            JAVA_METHOD_SIG_SIZE
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET,
            JAVA_METHOD_SIG_GET
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaList<'local>>> {

        if jobj.is_null()  {
            
            return Ok(None);
        }

        let cache  = LOCAL_JNI_CACHE.lock()?;

        let size_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_SIZE,
            JAVA_METHOD_SIG_SIZE
        );

        let get_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET,
            JAVA_METHOD_SIG_GET
        );

        Ok (Some (
            JavaList {
                jobj: jobj,
                size_method: cache.get_instance_method(&size_key)?,
                get_method: cache.get_instance_method(&get_key)?
            }
        ))
    } 

    pub fn size(
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<jint> {

        let method: &JMethodID = &self.size_method;
        call_jni_int_method(env,&self.jobj,method,&[])
    }

    pub fn get (
        &self,
        env: &mut JNIEnv<'local>,
        index: jint
    ) -> Result<JObject<'local>> {

        let method: &JMethodID = &self.get_method;
        let params: [jvalue; 1] = [jvalue{i: index}]; 
        call_jni_object_method(env,&self.jobj,method,&params)
    }
}