/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Cedarling bootstrap entity builder configuration
 * <p>
 *    Cedarling internally uses an EntityBuilder to create the entities based on the token
 *    data or unsigned data passed to it during authorization decision evaluation. <br/>
 *    This configuration class controls its behaviour.
 * </p>
 */
public class EntityBuilderConfiguration {
    
   

    private EntityNames entityNames;
    private boolean buildWorkload;
    private boolean buildUser;
    private UnsignedRoleIdSrc unsignedRoleIdSrc;

    /**
     * Default constructor
     * <p> Creates an entity builder configuration with the following settings: </p>
     * <ul>
     *   <li>
     *       The {@code entityNames} is populated with the default values.
     *       Check out the documentation for the said class {@link io.jans.cedarling.bridge.config.EntityNames here}
     *   </li>
     *   <li>
     *       Building the workload entity is disabled (false)
     *   </li>
     *   <li>
     *       Building the user entity is disabled (false)
     *   </li>
     *   <li>
     *       The {@code unsignedRoleIdSrc} is populated with it's default value
     *       Check out the documentation for the said class {@link io.jans.cedarling.bridge.config.UnsignedRoleIdSrc here}
     *   </li>
     * </ul>
     */
    public EntityBuilderConfiguration() {

        entityNames = new EntityNames();
        buildWorkload = false;
        buildUser = false;
        unsignedRoleIdSrc = new UnsignedRoleIdSrc();
    }

    /**
     * Configures the entity builder to build the workload
     * <p> This is syntactic sugar to enable building the workload entity </p>
     * @return the  current instance of this configuration 
     */
    public EntityBuilderConfiguration buildWorkload() {

        return buildWorkload(true);
    }

    /**
     * Configures the entity builder to build the workload entity
     * @param value true if the workload entity building is enabled. false otherwise
     * @return the current instance of this configuration 
     */
    public EntityBuilderConfiguration buildWorkload (boolean value) {

        buildWorkload = value;
        return this;
    }

    /**
     * Gets the value of the flag indicating if the entity builder will build a workload entity
     * @return true if the workload entity building is enabled. false otherwise.
     */
    public boolean getBuildWorkload() {

        return buildWorkload;
    }

    /**
     * Configures the entity builder to build the workload
     * <p> This is syntactic sugar to enable building the user entity </p>
     * @return the  current instance of this configuration 
     */
    public EntityBuilderConfiguration buildUser() {

        return buildUser(true);
    }
    
    /**
     * Configures the entity builder to build the workload entity
     * @param value true if the workload entity building is enabled. false otherwise
     * @return the current instance of this configuration 
     */
    public EntityBuilderConfiguration buildUser(boolean value) {

        buildUser = value;
        return this;
    }

    /**
     * Gets the value of the flag indicating if the entity builder will build a user entity
     * @return true if the user entity building is enabled. false otherwise.
     */
    public boolean getBuildUser() {

        return buildUser;
    }

    /**
     * Specifies the names to assign to the entities the EntityBuilder creates
     * @param entityNames the name of the entities 
     */
    public void entityNames(final EntityNames entityNames) {

        this.entityNames = entityNames;
    }

    /**
     * Gets the names to assign to the entities the EntityBuilder creates 
     * @return the names of said entities 
     */
    public EntityNames getEntityNames() {

        return entityNames;
    }

    /**
     * Specifies the attribute that will be used to create the {@code Role} entity when using the unsigned authorization interface
     * @param unsignedIdRoleSrc the attribute 
     * @return the current instance of the configuration
     */
    public EntityBuilderConfiguration unsignedRoleIdSrc(final UnsignedRoleIdSrc unsignedIdRoleSrc) {

        this.unsignedRoleIdSrc = unsignedIdRoleSrc;
        return this;
    }

    /**
     * Gets the attribute that will be used to create the {@code Role} entity when using the unsigned authorization interface
     * @return the attribute to be used for {@code Role} entity creation
     */
    public UnsignedRoleIdSrc getUnsignedRoleIdSrc() {

        return unsignedRoleIdSrc;
    }
}
