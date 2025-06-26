// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedar_policy::{PolicyId};
use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::*;
use jni::JNIEnv;
use jni::objects::{GlobalRef,JClass,JMethodID,JObject,JString};
use jni::sys::{jvalue};

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/cedar/policy/PolicyId";

const JAVA_CLS_CTOR: &str = "<init>";
const JAVA_CLS_CTOR_SIG: &str = "(Ljava/lang/String;)V";


static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaPolicyId <'r> {
    policy_id: &'r PolicyId,
    ctor_method: JMethodID,
}

impl <'r> JavaPolicyId <'r> {

    pub fn jni_cache_init<'local> (
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
            JAVA_CLS_CTOR,
            JAVA_CLS_CTOR_SIG
        )
    }

    pub fn new<'local> (
        policy_id: &'r PolicyId
    ) -> Result<JavaPolicyId<'r>> {

        let cache = LOCAL_JNI_CACHE.lock()?;

        let ctor_key = (
            JAVA_CLS_NAME,
            JAVA_CLS_CTOR,
            JAVA_CLS_CTOR_SIG
        );

        Ok(JavaPolicyId {
            policy_id: policy_id,
            ctor_method: cache.get_instance_method(&ctor_key)?,
        })
    }
    
    pub fn as_java_object<'local> (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<JObject<'local>> {

        let pid_obj: JString<'local> = env.new_string(&self.policy_id)?;
        let pid_obj_ref: GlobalRef = env.new_global_ref(&pid_obj)?;
        let args: [jvalue;1] = [jvalue{ l: pid_obj_ref.as_raw() }];

        let cache = LOCAL_JNI_CACHE.lock()?;
        let class: JClass<'local> = cache.get_class(env,JAVA_CLS_NAME)?;
        call_jni_object_constructor(env,&class,&self.ctor_method,&args)
    }
}