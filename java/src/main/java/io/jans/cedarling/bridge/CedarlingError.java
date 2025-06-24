/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge;

public class CedarlingError extends Exception {

    public CedarlingError(final String message) {
        super(message);
    }

    public CedarlingError(final String message, Throwable cause) {
        super(message,cause);
    }
}
