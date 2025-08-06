/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Unsigned RoleId Source 
 */
public class UnsignedRoleIdSrc {
    
    private static final String DEFAULT_UNSIGNED_ROLE_ID_SRC = "role";
    private String value;

    /**
     * Default Constructor 
     */
    public UnsignedRoleIdSrc() {

        value = DEFAULT_UNSIGNED_ROLE_ID_SRC;
    }
    
    /**
     * Constructor 
     * @param value
     */
    public UnsignedRoleIdSrc(final String value) {

        this.value = value;
    }

    /**
     * Gets value
     * @return
     */
    public String getValue() {

        return value;
    }
}
