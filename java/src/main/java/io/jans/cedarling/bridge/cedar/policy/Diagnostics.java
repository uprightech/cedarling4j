/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents Diagnostic data post-cedar policy evaluation, mirroring the
 * {@code Diagnostics} struct in the Cedar policy library.
 * <p>
 *   This class wraps diagnostics information after policy evaluation, which is information
 *   about how a cedar authorization decision was reached.
 *   Documentation about the rust struct can be found <a href="https://docs.rs/cedar-policy/latest/cedar_policy/struct.Diagnostics.html">Here</a>
 * </p>
 */
public class Diagnostics {

    private Set<PolicyId> reason;

    private List<AuthzError> errors;

    private Diagnostics() {

        reason = new HashSet<>();
        errors = new ArrayList<>();
    }

    @SuppressWarnings("unused")
    private void addPolicyId(final PolicyId id) {

        reason.add(id);
    }

    /**
     * Gets the {@code PolicyId}s of the policies that contributed to the authorization decision
     * @return the set of {@code PolicyId}s 
     */
    public Set<PolicyId> getReason() {

        return reason;
    }

    @SuppressWarnings("unused")
    private void addError(final AuthzError error) {

        errors.add(error);
    }

    /**
     * Gets the errors that occured during authorization
     * @return a list of errors 
     */
    public List<AuthzError> getErrors() {

        return errors;
    }
    
}
