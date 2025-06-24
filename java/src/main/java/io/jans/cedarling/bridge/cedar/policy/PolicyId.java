/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.cedar.policy;

public class PolicyId {
    
    private String value;
    
    private PolicyId(final String value) {

        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
