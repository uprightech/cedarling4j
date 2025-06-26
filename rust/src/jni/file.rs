// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use crate::{Result};
use crate::jni::{JniCache};
use std::sync::{Mutex,LazyLock};
use crate::jni::util::{call_jni_string_method};

const JAVA_CLS_NAME: &str = "java/io/File";
const JAVA_METHOD_NAME_GET_ABSOLUTE_PATH: &str = "getAbsolutePath";
const JAVA_METHOD_SIG_GET_ABSOLUTE_PATH: &str = "()Ljava/lang/String;";
static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaFile<'local> {
    jobj: JObject<'local>,
    get_absolute_path_method: JMethodID
}

impl <'local> JavaFile <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ABSOLUTE_PATH,
            JAVA_METHOD_SIG_GET_ABSOLUTE_PATH
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaFile<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_absolute_path_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ABSOLUTE_PATH,
            JAVA_METHOD_SIG_GET_ABSOLUTE_PATH
        );

        Ok( Some (
            JavaFile {
                jobj: jobj,
                get_absolute_path_method: cache.get_instance_method(&get_absolute_path_key)?
            }
        ))
    } 

    pub fn get_absolute_path (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_absolute_path_method;
        call_jni_string_method(env,&self.jobj, method, &[])
    }
}