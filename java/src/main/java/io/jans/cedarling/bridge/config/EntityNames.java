/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Wrapper for entity names used by the {@link io.jans.cedarling.bridge.config.EntityBuilderConfiguration}
 * <p>
 *   This class groups the entity names used in the method 
 *   {@link io.jans.cedarling.bridge.config.EntityBuilderConfiguration#entityNames(EntityNames entityNames)}. <br/>
 * </p>
 */
public class EntityNames {
    
    private static final String DEFAULT_WORKLOAD_ENTITY_NAME = "Jans::Workload";
    private static final String DEFAULT_USER_ENTITY_NAME = "Jans::User";
    private static final String DEFAULT_ISS_ENTITY_NAME  = "Jans::TrustedIssuer";
    private static final String DEFAULT_ROLE_ENTITY_NAME = "Jans::Role";

    private String user;
    private String workload;
    private String role;
    private String iss;

    /**
     * Default Constructor
     * <p>
     *   Creates an object with the following default entity names:
     * </p>
     * <ul>
     *   <li><b>Jans::Workload</b> for the workload entity name</li>
     *   <li><b>Jans::User</b> for the user entity name</li>
     *   <li><b>Jans::TrustedIssuer</b> for the issuer entity name</li>
     *   <li><b>Jans::Role</b> for the role entity name</li>
     * </ul>
     */
    public EntityNames() {

        user = DEFAULT_USER_ENTITY_NAME;
        workload = DEFAULT_WORKLOAD_ENTITY_NAME;
        role = DEFAULT_ROLE_ENTITY_NAME;
        iss = DEFAULT_ISS_ENTITY_NAME;
    }

    /**
     * Gets the name used for User entities
     * @return the name used for User entities as {@code String}
     */
    public String getUser() {

        return user;
    }

    /**
     * Specifies the name to use for User entities
     * <p>
     *   Maps to the {@code CEDARLING_MAPPING_USER} cedarling property
     * </p>
     * @param user the name to use for User entities as {@code String}
     */
    public void setUser(final String user) {

        this.user = user;
    }

    /**
     * Gets the name used for Workload entities
     * @return the name used for Workload entities as {@code String}
     */
    public String getWorkload() {

        return workload;
    }

    /**
     * Specifies the name to use for Workload entities
     * <p>
     *   Maps to the {@code CEDARLING_MAPPING_WORKLOAD} cedarling property
     * </p>
     * @param workload the name to use for Workload entities as {@code String}
     */
    public void setWorkload(final String workload) {

        this.workload = workload;
    }

    /**
     * Specifies the name to use for Role entities
     * <p>
     *   Maps to the {@code CEDARLING_MAPPING_ROLE} cedarling property
     * </p>
     * @param role the name to use for Role entities as {@code String}
     */
    public void setRole(final String role) {

        this.role = role;
    }

    /**
     * Gets the name to use for Role entities
     * @return the name to use for Role entities as {@code String}
     */
    public String getRole() {

        return role;
    }

    /**
     * Specifies the name to use for Issuer entitities 
     * @param iss the name to use for Issuer entities
     */
    public void setIss(final String iss) {

        this.iss = iss;
    }

    /**
     * Gets the name to use for Issuer entities
     * @return the name to use for Issuer entites as {@code String} 
     */
    public String getIss() {

        return iss;
    }
}
