/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.util.ArrayList;
import java.util.List;

/**
 * Cedarling bootstrap authorization configuration
 * <p>
 *   This class wraps authorization settings used in the {@link io.jans.cedarling.bridge.config.BootstrapConfiguration} class.
 *   <br/>. 
 *   Each field maps to a property in the <a href="https://docs.jans.io/stable/cedarling/cedarling-properties">Cedarling Bootstrap Configuration File</a>
 * </p>
 
 */
public class AuthorizationConfiguration {
    
    public static final String DEFAULT_DECISION_LOG_JWT_ID = "jti";

    private boolean useUserPrincipal;
    private boolean useWorkloadPrincipal;
    private JsonRule principalBoolOperator;
    private List<String> decisionLogUserClaims;
    private List<String> decisionLogWorkloadClaims;
    private String decisionLogDefaultJwtId;
    private IdTokenTrustMode idTokenTrustMode;

    /**
     * Constructor
     */
    public AuthorizationConfiguration() {
        useUserPrincipal = true;
        useWorkloadPrincipal = true;
        principalBoolOperator = new JsonRule("{}");
        decisionLogUserClaims = new ArrayList<>();
        decisionLogWorkloadClaims = new ArrayList<>();
        decisionLogDefaultJwtId = DEFAULT_DECISION_LOG_JWT_ID;
        idTokenTrustMode = IdTokenTrustMode.STRICT;
    }

    /**
     * Gets the value of the flag indicating if authorization is queried for a user principal
     * <p>Maps to the {@code CEDARLING_USER_AUTHZ} cedarling property.</p>
     * @return true if the flag is set and false otherwise
     */
    public boolean getUseUserPrincipal() {

        return useUserPrincipal;
    }

    /**
     * Sets the value of the flag indicating if authorization is queried for a user principal
     * <p>Maps to the {@code CEDARLING_USER_AUTHZ} cedarling property.</p>
     * @param use the value set the flag to
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration setUseUserPrincipal(boolean use) {

        useUserPrincipal = use;
        return this;
    }
    
    /**
     * Gets the value of the flag indicating if authorization is queried for a workload principal
     * <p>Maps to the {@code CEDARLING_WORKLOAD_AUTHZ} cedarling property.</p>
     * @return true if the flag is set and false otherwise
     */
    public boolean getUseWorkloadPrincipal() {

        return useWorkloadPrincipal;
    }

    /**
     * Sets the value of the flag indicating if authorization is queried for a workload principal
     * <p>Maps to the {@code CEDARLING_WORKLOAD_AUTHZ} cedarling property.</p>
     * @param use the value to set the flag to
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration setUseWorkloadPrincipal(boolean use) {

        useWorkloadPrincipal = use;
        return this;
    }

    /**
     * Specifies a rule which is used to make decisions about which principals are used during authorization
     * 
     * <p>
     *   Specifies a rule which will determine whether to authorize the {@code User}, {@code Workload} or both when
     *   making authorization decisions. <br/>
     *   Maps to the {@code CEDARLING_PRINCIPAL_BOOLEAN_OPERATION} cedarling property.
     * </p>
     * @param operator the value of the rule
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration setPrincipalBoolOperator(final JsonRule operator) {

        principalBoolOperator = operator;
        return this;
    }

    /**
     * Gets the rule which is used to make decisions about which principals are used during authorization 
     * <p>
     *   Maps to the {@code CEDARLING_PRINCIPAL_BOOLEAN_OPERATION} cedarling property.
     * </p>
     * @return the value of the rule 
     */
    public JsonRule getPrincipalBoolOperator() {

        return principalBoolOperator;
    }

    /**
     * Specifies a list of claims to log from the User entity, such as "sub", "email", etc..
     * <p>
     *   Maps to the {@code CEDARLING_DECISION_LOG_USER_CLAIMS} cedarling property.
     * </p>
     * @param claims the list of claims to set
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration setDecisionLogUserClaims(List<String> claims) {

        decisionLogUserClaims = claims;
        return this;
    }

    /**
     * Adds a claim to be logged from the User entity, such as "sub", "email", etc ...
     * <p>
     *   Maps to the {@code CEDARLING_DECISION_LOG_USER_CLAIMS} cedarling property.
     * </p>
     * @param claim the claim to be added to the existing list of claims 
     * @return the current instance of the authorization configuration 
     */
    public AuthorizationConfiguration addDecisionLogUserClaim(final String claim) {

        decisionLogUserClaims.add(claim);
        return this;
    }

    /**
     * Gets the list of claims to log from the User entity 
     * @return the list of user entity claims to log
     */
    public List<String> getDecisionLogUserClaims() {

        return decisionLogUserClaims;
    }

    /**
     * Specifies a list of claims to log from the Worload entity, such as "client_id", "rpid", etc..
     * <p>
     *   Maps to the {@code CEDARLING_DECISION_LOG_WORKLOAD_CLAIMS} cedarling property.
     * </p>
     * @param claims the list of claims to set
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration setDecisionLogWorkloadClaims(List<String> claims) {

        decisionLogWorkloadClaims = claims;
        return this;
    }

    /**
     * Adds a claim to be logged from the Workload entity, such as "client_id", "rpid", etc ...
     * @param claim the claim to be added to the list of existing claims 
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration addDecisionLogWorkloadClaims(final String claim) {

        decisionLogWorkloadClaims.add(claim);
        return this;
    }

    /**
     * Gets the list of claims to log from the Workload entity
     * @return the list of workload entity claims to log
     */
    public List<String> getDecisionLogWorkloadClaims() {

        return decisionLogWorkloadClaims;
    }

    /**
     * Specifies a token claim that will be used for decision logging.
     * <p>
     *   Maps to the {@code CEDARLING_DECISION_LOG_DEFAULT_JWT_ID} cedarling property.
     * </p>
     * @param jwtId the name of the claim to be used for decision logging
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration setDecisionLogDefaultJwtId(final String jwtId) {

        decisionLogDefaultJwtId = jwtId;
        return this;
    }

    /**
     * Gets the token claim to be used for decision logging 
     * @return the token claim to be used for decision logging 
     */
    public String getDecisionLogDefaultJwtId() {

        return decisionLogDefaultJwtId;
    }

    /**
     * Specifies varying levels of token validation based on the preference of the developer.
     * <p>
     *   Maps to the {@code CEDARLING_ID_TOKEN_TRUST_MODE} cedarling property.
     * </p>
     * @param trustMode trust level to use for id token validation
     * @return the current instance of the authorization configuration
     */
    public AuthorizationConfiguration setIdTokenTrustMode(final IdTokenTrustMode trustMode) {

        idTokenTrustMode = trustMode;
        return this;
    }

    /**
     * Gets the level of token validation to be used for authorization
     * @return the level of validation to be be used for id tokens
     */
    public IdTokenTrustMode getIdTokenTrustMode() {

        return idTokenTrustMode;
    }
}
