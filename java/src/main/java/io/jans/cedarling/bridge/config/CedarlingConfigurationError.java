/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import io.jans.cedarling.bridge.CedarlingError;

/**
 * Cedarling configuration error
 * <p> Usually thrown when an error occurs using the BootstrapConfiguration's builder Fluent API 
 */
public class CedarlingConfigurationError extends CedarlingError {
    /**
     * Constructor
     * @param message error message
     */
    public CedarlingConfigurationError(final String message) {
        super(message);
    }
    
    /**
     * Constructor 
     * @param message error message
     * @param cause exception cause
     */
    public CedarlingConfigurationError(final String message, Throwable cause) {
        super(message,cause);
    }
}
