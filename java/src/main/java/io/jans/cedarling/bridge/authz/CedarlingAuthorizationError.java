/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

import io.jans.cedarling.bridge.CedarlingError;

public class CedarlingAuthorizationError extends CedarlingError {
    
    public CedarlingAuthorizationError(final String message) {
        super(message);
    }
}
