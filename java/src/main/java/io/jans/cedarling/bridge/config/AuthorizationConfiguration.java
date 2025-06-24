/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.util.ArrayList;
import java.util.List;

public class AuthorizationConfiguration {
    
    public static final String DEFAULT_DECISION_LOG_JWT_ID = "jti";

    private boolean useUserPrincipal;
    private boolean useWorkloadPrincipal;
    private JsonRule principalBoolOperator;
    private List<String> decisionLogUserClaims;
    private List<String> decisionLogWorkloadClaims;
    private String decisionLogDefaultJwtId;
    private IdTokenTrustMode idTokenTrustMode;

    public AuthorizationConfiguration() {
        useUserPrincipal = true;
        useWorkloadPrincipal = true;
        principalBoolOperator = new JsonRule("{}");
        decisionLogUserClaims = new ArrayList<>();
        decisionLogWorkloadClaims = new ArrayList<>();
        decisionLogDefaultJwtId = DEFAULT_DECISION_LOG_JWT_ID;
        idTokenTrustMode = IdTokenTrustMode.STRICT;
    }

    public boolean getUseUserPrincipal() {

        return useUserPrincipal;
    }

    public AuthorizationConfiguration setUseUserPrincipal(boolean use) {

        useUserPrincipal = use;
        return this;
    }

    public boolean getUseWorkloadPrincipal() {

        return useWorkloadPrincipal;
    }

    public AuthorizationConfiguration setUseWorkloadPrincipal(boolean use) {

        useWorkloadPrincipal = use;
        return this;
    }

    public AuthorizationConfiguration setPrincipalBoolOperator(final JsonRule operator) {

        principalBoolOperator = operator;
        return this;
    }

    public JsonRule getPrincipalBoolOperator() {

        return principalBoolOperator;
    }

    public AuthorizationConfiguration setDecisionLogUserClaims(List<String> claims) {

        decisionLogUserClaims = claims;
        return this;
    }

    public AuthorizationConfiguration addDecisionLogUserClaim(final String claim) {

        decisionLogUserClaims.add(claim);
        return this;
    }

    public List<String> getDecisionLogUserClaims() {

        return decisionLogUserClaims;
    }

    public AuthorizationConfiguration setDecisionLogWorkloadClaims(List<String> claims) {

        decisionLogWorkloadClaims = claims;
        return this;
    }

    public AuthorizationConfiguration addDecisionLogWorkloadClaims(final String claim) {

        decisionLogWorkloadClaims.add(claim);
        return this;
    }

    public List<String> getDecisionLogWorkloadClaims() {

        return decisionLogWorkloadClaims;
    }


    public AuthorizationConfiguration setDecisionLogDefaultJwtId(final String jwtId) {

        decisionLogDefaultJwtId = jwtId;
        return this;
    }

    public String getDecisionLogDefaultJwtId() {

        return decisionLogDefaultJwtId;
    }

    public AuthorizationConfiguration setIdTokenTrustMode(final IdTokenTrustMode trustMode) {

        idTokenTrustMode = trustMode;
        return this;
    }

    public IdTokenTrustMode getIdTokenTrustMode() {

        return idTokenTrustMode;
    }
}
