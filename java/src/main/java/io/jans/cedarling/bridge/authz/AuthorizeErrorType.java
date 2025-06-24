/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

public enum AuthorizeErrorType {
    ProcessTokens("ProcessTokens"),
    AccessTokenEntities("AccessTokenEntities"),
    CreateIdTokenEntity("CreateIdTokenEntity"),
    CreateUserinfoTokenEntity("CreateUserinfoTokenEntity"),
    CreateUserEntity("CreateUserEntity"),
    ResourceEntity("ResourceEntity"),
    RoleEntity("RoleEntity"),
    Action("Action"),
    CreateContext("CreateContext"),
    CreateRequestWorkloadEntity("CreateRequestWorkloadEntity"),
    CreateRequestUserEntity("CreateRequestUserEntity"),
    Entities("Entities");

    private final String value;

    private AuthorizeErrorType(final String value) {
        this.value = value;
    }
    
    public static AuthorizeErrorType fromString(final String str) {

        for(final AuthorizeErrorType type: values()) {
            if(type.value.equals(str)) {
                return type;
            }   
        }
        return null;
    }
}
