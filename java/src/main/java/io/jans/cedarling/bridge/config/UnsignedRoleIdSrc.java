/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

public class UnsignedRoleIdSrc {
    
    private static final String DEFAULT_UNSIGNED_ROLE_ID_SRC = "role";
    private String value;

    public UnsignedRoleIdSrc() {

        value = DEFAULT_UNSIGNED_ROLE_ID_SRC;
    }
    
    public UnsignedRoleIdSrc(final String value) {

        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
