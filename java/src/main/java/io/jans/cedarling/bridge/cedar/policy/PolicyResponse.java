/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy;

/**
 * Represents the cedar authorization response returned from the authorizer, mirroring the
 * {@code Response} struct in the Cedar policy library.
 * <p>
 *   This class wraps the cedar authorization response returned from the authorizer.
 *   Documentation about the rust struct can be found <a href="https://docs.rs/cedar-policy/latest/cedar_policy/struct.Response.html">Here</a>
 * </p>
 */
public class PolicyResponse {

    private AuthzDecision decision;
    private Diagnostics diagnostics;

    private PolicyResponse(AuthzDecision decision, Diagnostics diagnostics) {

        this.decision = decision;
        this.diagnostics = diagnostics;
    }

    /**
     * Gets the authorization decision
     * @return a representation of the authorization decision
     */
    public AuthzDecision getDecision() {

        return decision;
    }

    /**
     * Gets the diagnostics information 
     * @return a representation of the diagnostics information 
     */
    public Diagnostics getDiagnostics() {

        return diagnostics;
    }
}
