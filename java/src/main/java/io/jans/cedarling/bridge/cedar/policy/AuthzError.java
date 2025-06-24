/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy;

public class AuthzError {
    
    private String description;

    private AuthzError(final String descriptor) {
        this.description = descriptor;
    }

    public String getDescription() {

        return description;
    }
}
