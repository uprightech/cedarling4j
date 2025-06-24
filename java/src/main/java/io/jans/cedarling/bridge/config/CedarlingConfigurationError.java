/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import io.jans.cedarling.bridge.CedarlingError;

public class CedarlingConfigurationError extends CedarlingError {
    
    public CedarlingConfigurationError(final String message) {
        super(message);
    }
    
    public CedarlingConfigurationError(final String message, Throwable cause) {
        super(message,cause);
    }
}
