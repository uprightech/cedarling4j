/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy;

/**
 * Represents a unique id assigned to cedar policies and templates, mirroring the 
 * {@code PolicyId} struct in the Cedar policy library.
 * <p>
 *   This class wraps the PolicyId cedar struct which is a unique id assigned to cedar policies 
 *   Documentation about the rust struct can be found <a href="https://docs.rs/cedar-policy/latest/cedar_policy/struct.PolicyId.html">Here</a>
 * </p>
 */
public class PolicyId {
    
    private String value;
    
    private PolicyId(final String value) {

        this.value = value;
    }

    /**
     * Gets the id of the Policy
     * @return a string representation of the PolicyId
     */
    public String getValue() {

        return value;
    }
}
