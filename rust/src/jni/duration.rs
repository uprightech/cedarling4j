use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use jni::sys::jlong;
use crate::{Result};
use crate::jni::{JniCache};
use std::sync::{Mutex,LazyLock};
use std::time::{Duration};
use crate::jni::util::{call_jni_long_method};

const JAVA_CLS_NAME: &str = "java/time/Duration";
const JAVA_METHOD_NAME_TO_NANOS: &str = "toNanos";
const JAVA_METHOD_SIG_TO_NANOS: &str = "()J";
static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaDuration<'local> {
    jobj: JObject<'local>,
    to_nanos_method: JMethodID
}

impl <'local> JavaDuration <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_TO_NANOS,
            JAVA_METHOD_SIG_TO_NANOS
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaDuration<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let to_nanos_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_TO_NANOS,
            JAVA_METHOD_SIG_TO_NANOS
        );

        Ok( Some (
            JavaDuration {
                jobj: jobj,
                to_nanos_method: cache.get_instance_method(&to_nanos_key)?
            }
        ))
    }

    pub fn to_duration (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Duration> {

        let method: &JMethodID = &self.to_nanos_method;
        let duration_nanos: jlong = call_jni_long_method(env,&self.jobj, method, &[])?;
        Ok(Duration::from_nanos(duration_nanos as u64))
    }
}