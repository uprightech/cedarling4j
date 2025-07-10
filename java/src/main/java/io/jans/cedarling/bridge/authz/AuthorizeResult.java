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

/**
 * A lightweight encapsulation around cedarling's authorization result 
 * 
 */
public class AuthorizeResult {
    
    private Optional<PolicyResponse> workload;
    private Optional<PolicyResponse> person;
    private Map<String,PolicyResponse> principals;
    private boolean decision;
    private String requestId;

    /**
     * Constructor 
     */
    public AuthorizeResult() {

        workload = Optional.ofNullable(null);
        person = Optional.ofNullable(null);
        principals = new HashMap<>();
        decision = false;
        requestId = "";
    }

    /**
     * Gets the policy evaluation result for the workload
     * @return an optional policy evaluation result
     */
    public Optional<PolicyResponse> getWorkload() {

        return workload;
    }

    /**
     * Gets the policy evaluation result for the person (principal)
     * @return an optional policy evaluation result
     */
    public Optional<PolicyResponse> getPerson() {

        return person;
    }

    /**
     * Gets a map of the principals involved during the policy evaluation 
     * Each key into the map is a unique string 
     * @return
     */
    public Map<String,PolicyResponse> getPrincipals() {

        return principals;
    }

    /**
     * Gets the authorization decision result 
     * @return {@code true} if the action in the authorization request is allowed. {@code false} otherwise.
     */
    public boolean isAllowed() {

        return decision;
    }

    /**
     * Gets the requestid associated with the authorization request 
     * @return A string representing the requestid 
     */
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
