package io.jans.cedarling.bridge.authz;

public class CedarEntityMapping {
    
    private String id;
    private String entityType;

    public CedarEntityMapping(String id, String entityType) {

        this.id = id;
        this.entityType = entityType;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getEntityType() {

        return entityType;
    }

    public void setEntityType(String entityType) {

         this.entityType = entityType;
    }
}
