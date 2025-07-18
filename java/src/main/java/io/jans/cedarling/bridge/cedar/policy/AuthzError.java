/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy;

/**
 * Represents an authorization error, mirroring the
 * {@code AuthorizationError} enum in the Cedar policy library.
 * <p>
 *   This class is used to translate errors from the rust bridge.
 *   Since the rust class is an error class, and instead of replicating 
 *   all of its enum values, this class contains only the error message 
 *   to keep things simple.
 *   Documentation about the rust enum can be found <a href="https://docs.rs/cedar-policy/latest/cedar_policy/enum.AuthorizationError.html">Here</a>
 * </p>
 */
public class AuthzError {
    
    private String description;

    private AuthzError(final String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }
}
