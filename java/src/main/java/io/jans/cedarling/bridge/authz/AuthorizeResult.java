/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

import io.jans.cedarling.bridge.cedar.policy.*;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

public class AuthorizeResult {
    
    private Optional<PolicyResponse> workload;
    private Optional<PolicyResponse> person;
    private Map<String,PolicyResponse> principals;
    private boolean decision;
    private String requestId;

    public AuthorizeResult() {

        workload = Optional.ofNullable(null);
        person = Optional.ofNullable(null);
        principals = new HashMap<>();
        decision = false;
        requestId = "";
    }

    public Optional<PolicyResponse> getWorkload() {

        return workload;
    }

    public Optional<PolicyResponse> getPerson() {

        return person;
    }

    public Map<String,PolicyResponse> getPrincipals() {

        return principals;
    }

    public boolean isAllowed() {

        return decision;
    }

    public String getRequestId() {

        return requestId;
    }

    @SuppressWarnings("unused")
    private void setWorkload(final PolicyResponse workload) {

        this.workload = Optional.ofNullable(workload);
    }

    @SuppressWarnings("unused")
    private void setPerson(final PolicyResponse person) {

        this.person = Optional.ofNullable(person);
    }

    @SuppressWarnings("unused")
    private void addPrincipal(final String key, PolicyResponse value) {

        principals.put(key,value);
    }

    @SuppressWarnings("unused")
    private void setDecision(boolean decision) {

        this.decision = decision;
    }

    @SuppressWarnings("unused")
    private void setRequestId(final String requestId) {
        
        this.requestId = requestId;
    }
}
