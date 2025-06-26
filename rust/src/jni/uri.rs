// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use crate::jni::util::{call_jni_method_to_string};
use jni::JNIEnv;
use jni::objects::{JObject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache};
use std::sync::{Mutex,LazyLock};
use url::{Url};


const JAVA_CLS_NAME: &str = "java/net/URI";
static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaURI<'local> {
    jobj: JObject<'local>
}

impl <'local> JavaURI <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaURI<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        Ok( Some (
            JavaURI {
                jobj: jobj
            }
        ))
    } 

    pub fn as_url (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Url> {

        let urlstr = call_jni_method_to_string(env,&self.jobj)?
            .ok_or_else(|| CedarlingBridgeError::GenericError("Could not get string value of java/net/URI object".to_string()))?;
        
        Ok(Url::parse(&urlstr)?)
    }
}