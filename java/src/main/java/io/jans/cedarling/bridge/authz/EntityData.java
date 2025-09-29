/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

/**
 * A class representing Cedarling policy entity data
 * <p>Cedarling entity data is used in two places</p>
 * <ul>
 *    <li>In {@link io.jans.cedarling.bridge.authz.AuthorizeRequest} to represent the resource data which will be used for policy evaluation </li>
 *    <li>In {@link io.jans.cedarling.bridge.authz.AuthorizeRequestUnsigned} to represent principals which will be used for policy evaluation</li>
 * </ul>
 * The fields in the class are:
 * <ul>
 *  <li>
 *      {@link io.jans.cedarling.bridge.authz.EntityData#cedarMapping}. 
 *      This is the entity's cedar mapping properties encapsulated in an object of class
 *      {@link io.jans.cedarling.bridge.authz.CedarEntityMapping}
 *  </li>
 *  <li>
 *      {@link io.jans.cedarling.bridge.authz.EntityData#attributes}. 
 *      These are the entity's attributes. Same as with the {@link io.jans.cedarling.bridge.authz.Context} object 
 *      , a decision was made to make it a string so as not to constraint users in how the JSON representing this 
 *      field is generated. 
 *  </li>
 * </ul>
 */
public class EntityData {

    private CedarEntityMapping cedarMapping;
    private String attributes;

    /**
     * Default constructor
     * Creates an Entity with no cedar mapping and attributes
     */
    public EntityData() {

        cedarMapping = null;
        attributes = "{}";
    }

    /**
     * Creates an entity with the specified cedar mapping and attributes 
     * @param id the entity's id
     * @param type the entity's type 
     */
    public EntityData(CedarEntityMapping cedarMapping, String attributes) {

        this.cedarMapping = cedarMapping;
        this.attributes = attributes;
    }
    

    /**
     * Gets the entity's cedar mapping
     * @return the entity's cedar mapping
     */
    public CedarEntityMapping getCedarMapping() {

        return cedarMapping;
    }

    /**
     * Sets the entity's cedar mapping
     * @param type the entity's cedar mapping
     * @return the current {@code io.jans.cedarling.bridge.authz.EntityData} instance
     */
    public EntityData setCedarMapping(CedarEntityMapping cedarMapping) {

        this.cedarMapping = cedarMapping;
        return this;
    }


    /**
     * Get the entity's attributes 
     * @return the entity's attributes as a JSON string 
     */
    public String getAttributes() {

        return attributes;
    }

    /**
     * Sets the entity's attribute 
     * @param attributes the entity's attributes as a JSON string
     * @return the current {@code io.jans.cedarling.bridge.authz.EntityData} instance
     */
    public EntityData setAttributes(final String attributes) {

        this.attributes = attributes;
        return this;
    }
}
