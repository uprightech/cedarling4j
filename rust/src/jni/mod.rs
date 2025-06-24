pub (crate) mod duration;
pub (crate) mod jni_cache;
pub (crate) mod file;
pub (crate) mod list;
pub (crate) mod uri;
pub (crate) mod util;

pub (crate) use duration::JavaDuration as JavaDuration;
pub (crate) use jni_cache::JniCache as JniCache;
pub (crate) use file::JavaFile as JavaFile;
pub (crate) use list::JavaList as JavaList;
pub (crate) use uri::JavaURI as JavaURI;

use jni::JNIEnv;
use crate::{Result};

pub (crate) fn jni_cache_init<'local> (
    env: &mut JNIEnv<'local>
) -> Result<()> {

    JavaDuration::jni_cache_init(env)?;
    JavaFile::jni_cache_init(env)?;
    JavaList::jni_cache_init(env)?;
    JavaURI::jni_cache_init(env)?;
    util::jni_cache_init(env)
}
