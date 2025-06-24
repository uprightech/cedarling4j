use cedarling::{MemoryLogConfig};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use jni::sys::{jlong};
use crate::{Result};
use crate::jni::{JniCache};
use crate::jni::util::*;

use std::sync::{Mutex,LazyLock};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/MemoryLogConfiguration";

const JAVA_METHOD_NAME_GET_LOG_TTL:  &str = "getLogTtl";
const JAVA_METHOD_SIG_GET_LOG_TTL:   &str = "()J";
const JAVA_METHOD_NAME_GET_MAX_ITEMS: &str = "getMaxItems";
const JAVA_METHOD_SIG_GET_MAX_ITEMS: &str = "()Ljava/lang/Long;";
const JAVA_METHOD_NAME_GET_MAX_ITEM_SIZE: &str = "getMaxItemSize";
const JAVA_METHOD_SIG_GET_MAX_ITEM_SIZE: &str = "()Ljava/lang/Long;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaMemoryLogConfig<'local> {
    jobj: JObject<'local>,
    get_log_ttl_method: JMethodID,
    get_max_items_method: JMethodID,
    get_max_item_size_method: JMethodID
}

impl <'local> JavaMemoryLogConfig <'local> {

    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_TTL,
            JAVA_METHOD_SIG_GET_LOG_TTL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_MAX_ITEMS,
            JAVA_METHOD_SIG_GET_MAX_ITEMS
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_MAX_ITEM_SIZE,
            JAVA_METHOD_SIG_GET_MAX_ITEM_SIZE
        )
    }

    pub fn new (
        jobj: JObject<'local>
    )-> Result<Option<JavaMemoryLogConfig<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_log_ttl_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_LOG_TTL,
            JAVA_METHOD_SIG_GET_LOG_TTL
        );

        let get_max_items_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_MAX_ITEMS,
            JAVA_METHOD_SIG_GET_MAX_ITEMS
        );

        let get_max_item_size_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_MAX_ITEM_SIZE,
            JAVA_METHOD_SIG_GET_MAX_ITEM_SIZE
        );

        Ok (
            Some ( JavaMemoryLogConfig {
                jobj: jobj,
                get_log_ttl_method: cache.get_instance_method(&get_log_ttl_key)?,
                get_max_items_method: cache.get_instance_method(&get_max_items_key)?,
                get_max_item_size_method: cache.get_instance_method(&get_max_item_size_key)?
            })
        )
    }

    pub fn as_cedarling_memory_log_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<MemoryLogConfig> {

        let log_ttl = self.get_log_ttl(env)? as u64;
        let max_items: Option<usize> = self.get_max_items(env)?.map(|v| v as usize );
        let max_item_size: Option<usize> = self.get_max_item_size(env)?.map(|v| v as usize);
        Ok (MemoryLogConfig {
            log_ttl: log_ttl,
            max_items: max_items,
            max_item_size: max_item_size
        })
    }

    fn get_log_ttl (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<jlong> {

        let method: &JMethodID = &self.get_log_ttl_method;
        call_jni_long_method(env,&self.jobj,method,&[])
    }

    fn get_max_items (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<jlong>> {
        
        let method: &JMethodID = &self.get_max_items_method;
        call_jni_opt_long_method(env,&self.jobj,method,&[])
    }

    fn get_max_item_size ( 
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<jlong>> {

        let method: &JMethodID = &self.get_max_item_size_method;
        call_jni_opt_long_method(env,&self.jobj,method,&[])
    }
}
