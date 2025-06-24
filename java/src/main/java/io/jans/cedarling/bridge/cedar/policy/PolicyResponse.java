/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy;

public class PolicyResponse {

    private AuthzDecision decision;
    private Diagnostics diagnostics;

    private PolicyResponse(AuthzDecision decision, Diagnostics diagnostics) {

        this.decision = decision;
        this.diagnostics = diagnostics;
    }

    public AuthzDecision getDecision() {

        return decision;
    }

    public Diagnostics getDiagnostics() {

        return diagnostics;
    }
}
