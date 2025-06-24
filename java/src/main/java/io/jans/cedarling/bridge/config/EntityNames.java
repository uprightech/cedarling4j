/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

public class EntityNames {
    
    private static final String DEFAULT_WORKLOAD_ENTITY_NAME = "Jans::Workload";
    private static final String DEFAULT_USER_ENTITY_NAME = "Jans::User";
    private static final String DEFAULT_ISS_ENTITY_NAME  = "Jans::TrustedIssuer";
    private static final String DEFAULT_ROLE_ENTITY_NAME = "Jans::Role";

    private String user;
    private String workload;
    private String role;
    private String iss;

    public EntityNames() {

        user = DEFAULT_USER_ENTITY_NAME;
        workload = DEFAULT_WORKLOAD_ENTITY_NAME;
        role = DEFAULT_ROLE_ENTITY_NAME;
        iss = DEFAULT_ISS_ENTITY_NAME;
    }

    public String getUser() {

        return user;
    }

    public void setUser(final String user) {

        this.user = user;
    }

    public String getWorkload() {

        return workload;
    }

    public void setWorkload(final String workload) {

        this.workload = workload;
    }

    public void setRole(final String role) {

        this.role = role;
    }

    public String getRole() {

        return role;
    }

    public void setIss(final String iss) {

        this.iss = iss;
    }

    public String getIss() {

        return iss;
    }
}
