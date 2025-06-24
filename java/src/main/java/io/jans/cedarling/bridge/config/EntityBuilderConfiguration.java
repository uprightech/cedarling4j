/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

public class EntityBuilderConfiguration {
    
   

    private EntityNames entityNames;
    private boolean buildWorkload;
    private boolean buildUser;
    private UnsignedRoleIdSrc unsignedRoleIdSrc;

    public EntityBuilderConfiguration() {

        entityNames = new EntityNames();
        buildWorkload = false;
        buildUser = false;
        unsignedRoleIdSrc = new UnsignedRoleIdSrc();
    }

    public EntityBuilderConfiguration buildWorkload() {

        return buildWorkload(true);
    }

    public EntityBuilderConfiguration buildWorkload (boolean value) {

        buildWorkload = value;
        return this;
    }

    public boolean getBuildWorkload() {

        return buildWorkload;
    }

    public EntityBuilderConfiguration buildUser() {

        return buildUser(true);
    }
    
    public EntityBuilderConfiguration buildUser(boolean value) {

        buildUser = value;
        return this;
    }


    public boolean getBuildUser() {

        return buildUser;
    }

    public void entityNames(final EntityNames entityNames) {

        this.entityNames = entityNames;
    }

    public EntityNames getEntityNames() {

        return entityNames;
    }

    public EntityBuilderConfiguration unsignedRoleIdSrc(final UnsignedRoleIdSrc unsignedIdRoleSrc) {

        this.unsignedRoleIdSrc = unsignedIdRoleSrc;
        return this;
    }

    public UnsignedRoleIdSrc getUnsignedRoleIdSrc() {

        return unsignedRoleIdSrc;
    }
}
