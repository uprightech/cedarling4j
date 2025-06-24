/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

public class EntityData {

    private String id;
    private String type;
    private String attributes;

    public EntityData() {

        attributes = "{}";
    }

    public EntityData(String id, String type) {

        this.id   = id;
        this.type = type;
        attributes = "{}";
    }
    
    public EntityData(String id, String type, String attributes) {

        this.id = id;
        this.type = type;
        this.attributes = attributes;
    }

    public String getType() {

        return type;
    }

    public EntityData setType(String type) {

        this.type = type;
        return this;
    }

    public String getId() {

        return id;
    }

    public EntityData setId(String id) {

        this.id = id;
        return this;
    }

    public String getAttributes() {

        return attributes;
    }

    public EntityData setAttributes(final String attributes) {

        this.attributes = attributes;
        return this;
    }
}
