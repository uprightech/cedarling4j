// This software is available under the Apache-2.0 license.
// See https://www.apache.org/licenses/LICENSE-2.0.txt for full text.
//
// Copyright (c) 2025, Gluu, Inc.

use cedarling::{AuthorizationConfig,JsonRule,IdTokenTrustMode};
use jni::JNIEnv;
use jni::objects::{JMethodID,JObject};

use crate::{Result};
use crate::jni::util::*;
use crate::jni::{JniCache,JavaList};
use crate::config::{JavaJsonRule,JavaIdTokenTrustMode};

use std::sync::{Mutex,LazyLock};
use std::vec::{Vec};

const JAVA_CLS_NAME: &str = "io/jans/cedarling/bridge/config/AuthorizationConfiguration";

const JAVA_METHOD_NAME_GET_USE_USER_PRINCIPAL: &str = "getUseUserPrincipal";
const JAVA_METHOD_SIG_GET_USE_USER_PRINCIPAL: &str = "()Z";

const JAVA_METHOD_NAME_GET_USE_WORKLOAD_PRINCIPAL: &str = "getUseWorkloadPrincipal";
const JAVA_METHOD_SIG_GET_USE_WORKLOAD_PRINCIPAL: &str = "()Z";

const JAVA_METHOD_NAME_GET_PRINCIPAL_BOOL_OPERATOR: &str = "getPrincipalBoolOperator";
const JAVA_METHOD_SIG_GET_PRINCIPAL_BOOL_OPERATOR: &str  = "()Lio/jans/cedarling/bridge/config/JsonRule;";

const JAVA_METHOD_NAME_GET_DECISION_LOG_USER_CLAIMS: &str = "getDecisionLogUserClaims";
const JAVA_METHOD_SIG_GET_DECISION_LOG_USER_CLAIMS: &str  = "()Ljava/util/List;";

const JAVA_METHOD_NAME_GET_DECISION_LOG_WORKLOAD_CLAIMS: &str = "getDecisionLogWorkloadClaims";
const JAVA_METHOD_SIG_GET_DECISION_LOG_WORKLOAD_CLAIMS: &str = "()Ljava/util/List;";

const JAVA_METHOD_NAME_GET_DECISION_LOG_DEFAULT_JWT_ID: &str = "getDecisionLogDefaultJwtId";
const JAVA_METHOD_SIG_GET_DECISION_LOG_DEFAULT_JWT_ID: &str = "()Ljava/lang/String;";

const JAVA_METHOD_NAME_GET_ID_TOKEN_TRUST_MODE: &str = "getIdTokenTrustMode";
const JAVA_METHOD_SIG_GET_ID_TOKEN_TRUST_MODE: &str  = "()Lio/jans/cedarling/bridge/config/IdTokenTrustMode;";

static LOCAL_JNI_CACHE: LazyLock< Mutex<JniCache> > = LazyLock::new(|| Mutex::new(JniCache::new()));


pub (crate) struct JavaAuthorizationConfig<'local> {
    jobj: JObject<'local>,
    get_use_user_principal_method: JMethodID,
    get_use_workload_principal_method: JMethodID,
    get_principal_bool_operator_method: JMethodID,
    get_decision_log_user_claims_method: JMethodID,
    get_decision_log_workload_claims_method: JMethodID,
    get_decision_log_default_jwt_id_method: JMethodID,
    get_id_token_trust_mode_method: JMethodID
}

impl <'local> JavaAuthorizationConfig<'local> {

    pub fn jni_cache_init (
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
            JAVA_METHOD_NAME_GET_USE_USER_PRINCIPAL,
            JAVA_METHOD_SIG_GET_USE_USER_PRINCIPAL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_USE_WORKLOAD_PRINCIPAL,
            JAVA_METHOD_SIG_GET_USE_WORKLOAD_PRINCIPAL
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_PRINCIPAL_BOOL_OPERATOR,
            JAVA_METHOD_SIG_GET_PRINCIPAL_BOOL_OPERATOR
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DECISION_LOG_USER_CLAIMS,
            JAVA_METHOD_SIG_GET_DECISION_LOG_USER_CLAIMS
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DECISION_LOG_WORKLOAD_CLAIMS,
            JAVA_METHOD_SIG_GET_DECISION_LOG_WORKLOAD_CLAIMS
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DECISION_LOG_DEFAULT_JWT_ID,
            JAVA_METHOD_SIG_GET_DECISION_LOG_DEFAULT_JWT_ID
        )?;

        cache.add_instance_method (
            env,
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ID_TOKEN_TRUST_MODE,
            JAVA_METHOD_SIG_GET_ID_TOKEN_TRUST_MODE
        )
    }

    pub fn new (
        jobj: JObject<'local>
    ) -> Result<Option<JavaAuthorizationConfig<'local>>> {

        if jobj.is_null() {
            return Ok(None);
        }

        let cache =  LOCAL_JNI_CACHE.lock()?;

        let get_use_user_principal_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_USE_USER_PRINCIPAL,
            JAVA_METHOD_SIG_GET_USE_USER_PRINCIPAL
        );

        let get_use_workload_principal_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_USE_WORKLOAD_PRINCIPAL,
            JAVA_METHOD_SIG_GET_USE_WORKLOAD_PRINCIPAL
        );

        let get_principal_bool_operator_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_PRINCIPAL_BOOL_OPERATOR,
            JAVA_METHOD_SIG_GET_PRINCIPAL_BOOL_OPERATOR
        );

        let get_decision_log_user_claims_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DECISION_LOG_USER_CLAIMS,
            JAVA_METHOD_SIG_GET_DECISION_LOG_USER_CLAIMS
        );

        let get_decision_log_workload_claims_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DECISION_LOG_WORKLOAD_CLAIMS,
            JAVA_METHOD_SIG_GET_DECISION_LOG_WORKLOAD_CLAIMS
        );

        let get_decision_log_default_jwt_id_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_DECISION_LOG_DEFAULT_JWT_ID,
            JAVA_METHOD_SIG_GET_DECISION_LOG_DEFAULT_JWT_ID
        );

        let get_id_token_trust_mode_key = (
            JAVA_CLS_NAME,
            JAVA_METHOD_NAME_GET_ID_TOKEN_TRUST_MODE,
            JAVA_METHOD_SIG_GET_ID_TOKEN_TRUST_MODE
        );
        
        Ok (
            Some ( JavaAuthorizationConfig {
                jobj: jobj,
                get_use_user_principal_method: cache.get_instance_method(&get_use_user_principal_key)?,
                get_use_workload_principal_method: cache.get_instance_method(&get_use_workload_principal_key)?,
                get_principal_bool_operator_method: cache.get_instance_method(&get_principal_bool_operator_key)?,
                get_decision_log_user_claims_method: cache.get_instance_method(&get_decision_log_user_claims_key)?,
                get_decision_log_workload_claims_method: cache.get_instance_method(&get_decision_log_workload_claims_key)?,
                get_decision_log_default_jwt_id_method: cache.get_instance_method(&get_decision_log_default_jwt_id_key)?,
                get_id_token_trust_mode_method: cache.get_instance_method(&get_id_token_trust_mode_key)?
            } )
        )
    }

    pub fn as_cedarling_authz_config (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<AuthorizationConfig> {

        let principal_bool_op: JsonRule = {
            let json_rule_obj = require_some(self.get_principal_bool_operator(env)?,JAVA_CLS_NAME,"principalBoolOperator")?;
            json_rule_obj.as_cedarling_json_rule(env)?
        };

        let dl_user_claims: Vec<String> = {

            let claims_obj = require_some(self.get_decision_log_user_claims(env)?,JAVA_CLS_NAME,"decisionLogUserClaims")?;
            let claims_count = claims_obj.size(env)?;
            let mut ret: Vec<String> = Vec::with_capacity(claims_count as usize);
            for i in 0..claims_count {
                let claim_obj = claims_obj.get(env,i)?;
                let opt_str = java_string_to_native_string(env,&claim_obj)?;
                match opt_str {
                    Some(claim) => { ret.push(claim); () },
                    _ => ()
                }
            }
            ret
        };

        let dl_workload_claims: Vec<String> = {

            let claims_obj = require_some(self.get_decision_log_workload_claims(env)?,JAVA_CLS_NAME,"decisionLogWorkloadClaims")?;
            let claims_count = claims_obj.size(env)?;
            let mut ret: Vec<String> = Vec::with_capacity(claims_count as usize);
            for i in 0..claims_count {
                let claim_obj = claims_obj.get(env,i)?;
                let opt_str = java_string_to_native_string(env,&claim_obj)?;
                match opt_str {
                    Some(claim) => { ret.push(claim); () },
                    _ => ()
                }
            }
            ret
        };

        let dl_default_jwt_id: String = {
            require_some(self.get_decision_log_default_jwt_id(env)?,JAVA_CLS_NAME,"decisionLogDefaultJwtId")?
        };

        let id_token_trust_mode: IdTokenTrustMode = {
            let trust_mode_obj = require_some(self.get_id_token_trust_mode(env)?,JAVA_CLS_NAME,"idTokenTrustMode")?;
            trust_mode_obj.as_enum()?
        };

        Ok( 
            AuthorizationConfig {
                use_user_principal: self.get_use_user_principal(env)?,
                use_workload_principal: self.get_use_workload_principal(env)?,
                principal_bool_operator: principal_bool_op,
                decision_log_user_claims: dl_user_claims,
                decision_log_workload_claims: dl_workload_claims,
                decision_log_default_jwt_id: dl_default_jwt_id,
                id_token_trust_mode: id_token_trust_mode
            } 
        )
    }

    fn get_use_user_principal (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_use_user_principal_method;
        Ok( call_jni_bool_method(env,&self.jobj,method,&[])? )
    }

    fn get_use_workload_principal (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<bool> {

        let method: &JMethodID = &self.get_use_workload_principal_method;
        Ok( call_jni_bool_method(env,&self.jobj,method,&[])? )
    }

    fn get_principal_bool_operator (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaJsonRule<'local>>> {

        let method: &JMethodID = &self.get_principal_bool_operator_method;
        let java_obj  = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaJsonRule::new(java_obj)
    }

    fn get_decision_log_user_claims (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaList<'local>>> {

        let method: &JMethodID = &self.get_decision_log_user_claims_method;
        let list_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaList::new(list_obj)
    }

    fn get_decision_log_workload_claims (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaList<'local>>> {

        let method: &JMethodID = &self.get_decision_log_workload_claims_method;
        let list_obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaList::new(list_obj)
    }

    fn get_decision_log_default_jwt_id (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<String>> {

        let method: &JMethodID = &self.get_decision_log_default_jwt_id_method;
        call_jni_string_method(env,&self.jobj,method,&[])
    }

    fn get_id_token_trust_mode (
        &self,
        env: &mut JNIEnv<'local>
    ) -> Result<Option<JavaIdTokenTrustMode>> {

        let method: &JMethodID = &self.get_id_token_trust_mode_method;
        let obj = call_jni_object_method(env,&self.jobj,method,&[])?;
        JavaIdTokenTrustMode::from_jni_object(env,obj)
    }
}