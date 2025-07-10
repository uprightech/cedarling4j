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
 *      {@link io.jans.cedarling.bridge.authz.EntityData#type}. This is the entity's type
 *  </li>
 *  <li>
 *      {@link io.jans.cedarling.bridge.authz.EntityData#id}. 
 *      This is the entity's unique id which helps discriminate it from other entities of the same type
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

    private String id;
    private String type;
    private String attributes;

    /**
     * Default constructor
     * Creates an Entity with no id , type and attributes 
     */
    public EntityData() {

        attributes = "{}";
    }

    /**
     * Creates an entity with the specified id and type but no attributes 
     * @param id the entity's id
     * @param type the entity's type 
     */
    public EntityData(String id, String type) {

        this.id   = id;
        this.type = type;
        attributes = "{}";
    }
    
    /**
     * Creates an entity with the specified id , type and attributes 
     * @param id the entity's id
     * @param type the entity's type 
     * @param attributes the entity's attributes as a json string 
     */
    public EntityData(String id, String type, String attributes) {

        this.id = id;
        this.type = type;
        this.attributes = attributes;
    }

    /**
     * Gets the entity's type
     * @return the entity's type 
     */
    public String getType() {

        return type;
    }

    /**
     * Sets the entity's type
     * @param type the entity's type
     * @return the current {@code io.jans.cedarling.bridge.authz.EntityData} instance
     */
    public EntityData setType(String type) {

        this.type = type;
        return this;
    }

    /**
     * Get the entity's id
     * @return the entity's id
     */
    public String getId() {

        return id;
    }

    /**
     * Set the entity's id
     * @param id the entity's id
     * @return the current {@code io.jans.cedarling.bridge.authz.EntityData} instance
     */
    public EntityData setId(String id) {

        this.id = id;
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
