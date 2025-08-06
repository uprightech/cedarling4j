/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Enum specifying the levels of validation for the id token
 */
public enum IdTokenTrustMode {
    /**
     * Always
     */
    ALWAYS,
    /**
     * Never
     */
    NEVER,
    /**
     * If Present
     */
    IF_PRESENT,
    /**
     * Strict
     */
    STRICT
}
