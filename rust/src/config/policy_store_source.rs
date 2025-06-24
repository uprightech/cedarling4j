use cedarling::{PolicyStoreSource};
use jni::JNIEnv;
use jni::objects::{JObject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::util::{call_jni_method_to_string};
use std::path::{Path};

const JAVA_CLS_NAME: &str   = "io/jans/cedarling/bridge/config/PolicyStoreSource";
const JAVA_ENUM_VALUE_JSON: &str = "JSON";
const JAVA_ENUM_VALUE_YAML: &str = "YAML";
const JAVA_ENUM_VALUE_LOCKMASTER: &str = "LOCKMASTER";
const JAVA_ENUM_VALUE_FILEJSON: &str = "FILEJSON";
const JAVA_ENUM_VALUE_FILEYAML: &str = "FILEYAML";


pub (crate) struct JavaPolicyStoreSource {
    name: String
}

impl JavaPolicyStoreSource {

   
    pub fn from_jni_object<'local> (
        env: &mut JNIEnv<'local>,
        jobj: JObject<'local>
    ) -> Result<Option<JavaPolicyStoreSource>> {

        if jobj.is_null() {
            return Ok(None);
        }
        
        Ok( call_jni_method_to_string(env,&jobj)?.map(|source| JavaPolicyStoreSource{ name: source}) )
    } 

    pub fn as_enum<D,P> (
        &self,
        data_fn: D,
        data_path_fn: P
    ) -> Result<PolicyStoreSource> where D: FnOnce() -> Result<String>, P: FnOnce() -> Result<Box<Path>>  {

        let val = self.name.clone();

        match &self.name as &str {
            JAVA_ENUM_VALUE_JSON => Ok( PolicyStoreSource::Json(data_fn()?) ),
            JAVA_ENUM_VALUE_YAML => Ok( PolicyStoreSource::Yaml(data_fn()?) ),
            JAVA_ENUM_VALUE_LOCKMASTER => Ok( PolicyStoreSource::LockServer(data_fn()?) ),
            JAVA_ENUM_VALUE_FILEJSON => Ok( PolicyStoreSource::FileJson(data_path_fn()?) ),
            JAVA_ENUM_VALUE_FILEYAML => Ok( PolicyStoreSource::FileYaml(data_path_fn()?) ),
            _ => Err(CedarlingBridgeError::UnknownEnumValue{enum_cls: JAVA_CLS_NAME, value: val})
        }
    }
}