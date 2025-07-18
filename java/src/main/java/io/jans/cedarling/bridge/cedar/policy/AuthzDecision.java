/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy; 

/**
 * Represents an authorization decision, mirroring the
 * {@code Decision} enum in the Cedar policy library.
 * <p>
 *   This enum is used to translate authorization outcomes from the rust bridge 
 *   Documentation about the rust enum can be found <a href="https://docs.rs/cedar-policy/latest/cedar_policy/enum.Decision.html">Here</a>
 * </p>
 */
public enum AuthzDecision {
    ALLOW,
    DENY
}