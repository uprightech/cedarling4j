use cedarling::{JwtConfig};
use crate::{Result,CedarlingBridgeError};
use crate::jni::{JniCache,JavaList};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};
use jsonwebtoken::{Algorithm};
use std::sync::{Mutex,LazyLock};
use crate::jni::util::*;
use super::{JavaJwtAlgorithm};
use std::collections::{HashSet};


const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/JwtConfiguration";
const JAVA_METHOD_NAME_GET_JWKS: &str = "getJwks";
const JAVA_METHOD_SIG_GET_JWKS: &str = "()Ljava/lang/String;";
const JAVA_METHOD_NAME_GET_JWT_CHECK_SIGN_VALIDATION: &str = "getJwtCheckSignValidation";
const JAVA_METHOD_SIG_GET_JWT_CHECK_SIGN_VALIDATION: &str  = "()Z";
const JAVA_METHOD_NAME_GET_JWT_CHECK_STATUS_VALIDATION: &str = "getJwtCheckStatusValidation";
const JAVA_METHOD_SIG_GET_JWT_CHECK_STATUS_VALIDATION: &str = "()Z";
const JAVA_METHOD_NAME_GET_SUPPORTED_SIGNATURE_ALGORITHMS: &str = "getSupportedSignatureAlgorithms";
const JAVA_METHOD_SIG_GET_SUPPORTED_SIGNATURE_ALGORITHMS: &str = "()Ljava/util/List;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));

pub (crate) struct JavaJwtConfig<'local> {
    jobj: JObject<'local>,
    get_jwks_method: JMethodID,
    get_jwt_check_sign_validation_method: JMethodID,
    get_jwt_check_status_validation_method: JMethodID,
    get_supported_signature_algorithms_method: JMethodID
}

impl <'local> JavaJwtConfig<'local> {


    pub fn jni_cache_init (
        env: &mut JNIEnv<'local>
    ) -> Result<()> {

        let mut cache = LOCAL_JNI_CACHE.lock()?;

        cache.add_class(env,JAVA_CLS_NAME)?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWKS,
            JAVA_METHOD_SIG_GET_JWKS
        )?;
        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWT_CHECK_SIGN_VALIDATION,
            JAVA_METHOD_SIG_GET_JWT_CHECK_SIGN_VALIDATION
        )?;
        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWT_CHECK_STATUS_VALIDATION,
            JAVA_METHOD_SIG_GET_JWT_CHECK_STATUS_VALIDATION
        )?;
        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_SUPPORTED_SIGNATURE_ALGORITHMS,
            JAVA_METHOD_SIG_GET_SUPPORTED_SIGNATURE_ALGORITHMS
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaJwtConfig<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache = LOCAL_JNI_CACHE.lock()?;

        let get_jwks_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWKS,
            JAVA_METHOD_SIG_GET_JWKS
        );

        let get_jwt_check_sign_validation_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWKS,
            JAVA_METHOD_SIG_GET_JWKS
        );

        let get_jwt_check_status_validation_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_JWT_CHECK_STATUS_VALIDATION,
            JAVA_METHOD_SIG_GET_JWT_CHECK_SIGN_VALIDATION
        );

        let get_supported_signature_algorithms_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_SUPPORTED_SIGNATURE_ALGORITHMS,
            JAVA_METHOD_SIG_GET_SUPPORTED_SIGNATURE_ALGORITHMS
        );

        Ok (
            Some ( JavaJwtConfig {
                jobj: jobj,
                get_jwks_method: cache.get_instance_method(&get_jwks_key)?,
                get_jwt_check_sign_validation_method: cache.get_instance_method(&get_jwt_check_sign_validation_key)?,
                get_jwt_check_status_validation_method: cache.get_instance_method(&get_jwt_check_status_validation_key)?,
                get_supported_signature_algorithms_method: cache.get_instance_method(&get_supported_signature_algorithms_key)?
            } )
        )
    }

    pub fn as_cedarling_jwt_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<JwtConfig> {

        let jwks = self.get_jwks(env)?;
        let jwt_sig_validation = self.get_jwt_check_sign_validation(env)?;
        let jwt_status_validation = self.get_jwt_check_status_validation(env)?;
        
        let mut supported_algorithms : HashSet<Algorithm> = HashSet::new();
        let supported_algs = require_some(self.get_supported_signature_algorithms(env)?,JAVA_CLS_NAME,"supportedSignatureAlgorithms")?;
        let supported_algs_count = supported_algs.size(env)?;
        for i in 0..supported_algs_count {
            let tmp_alg = supported_algs.get(env,i)?;
            let alg = self.to_algorithm(env,tmp_alg)?;
            supported_algorithms.insert(alg);
        }
        
        Ok(JwtConfig {
            jwks: jwks,
            jwt_sig_validation: jwt_sig_validation,
            jwt_status_validation: jwt_status_validation,
            signature_algorithms_supported: supported_algorithms
        })
    }
    
    fn get_jwks(
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_jwks_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_jwt_check_sign_validation (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_jwt_check_sign_validation_method;
        call_jni_bool_method(env,&self.jobj,method,&[])
    }

    fn get_jwt_check_status_validation (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_jwt_check_status_validation_method;
        call_jni_bool_method(env,&self.jobj,method,&[])
    }

    fn get_supported_signature_algorithms (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaList<'local>>> {

        let method: &JMethodID = &self.get_supported_signature_algorithms_method;
        let algs_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaList::new(algs_obj)
    }

    fn to_algorithm (
        &self,
        env: &mut JNIEnv<'local>,
        alg_obj: JObject<'local>
    ) -> Result<Algorithm> {

        JavaJwtAlgorithm::from_jni_object(env,alg_obj)?.ok_or(
            CedarlingBridgeError::GenericError("null value provided for jwt algorithm".to_string())
        )?.as_enum()
    }
}