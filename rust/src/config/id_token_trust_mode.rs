use cedarling::{IdTokenTrustMode};
use jni::JNIEnv;
use jni::objects::{JObject};
use crate::{Result,CedarlingBridgeError};
use crate::jni::util::{call_jni_method_to_string};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/IdTokenTrustMode";
const JAVA_ID_TOKEN_TRUST_MODE_VALUE_NONE: &str = "NONE";
const JAVA_ID_TOKEN_TRUST_MODE_VALUE_STRICT: &str = "STRICT";

pub (crate) struct JavaIdTokenTrustMode {
    name: String
}

impl JavaIdTokenTrustMode {

   
    pub fn from_jni_object<'local> (
        env: &mut JNIEnv<'local>,
        jobj: JObject<'local>
    ) -> Result<Option<JavaIdTokenTrustMode>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let name = call_jni_method_to_string(env,&jobj)?;
        Ok(name.map(|val| JavaIdTokenTrustMode{name: val}))
    }

    pub fn as_enum (
        &self
    ) ->Result<IdTokenTrustMode> {

        match &self.name as &str {
            JAVA_ID_TOKEN_TRUST_MODE_VALUE_NONE => Ok( IdTokenTrustMode::None ),
            JAVA_ID_TOKEN_TRUST_MODE_VALUE_STRICT => Ok( IdTokenTrustMode::Strict ),
            _ => Err( CedarlingBridgeError::UnknownEnumValue{
                enum_cls: JAVA_CLS_NAME,
                value: self.name.clone()
            })
        }
    }
}