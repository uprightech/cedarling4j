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

    public Set<PolicyId> getReason() {

        return reason;
    }

    @SuppressWarnings("unused")
    private void addError(final AuthzError error) {

        errors.add(error);
    }

    public List<AuthzError> getErrors() {

        return errors;
    }
    
}
