/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

import java.util.ArrayList;
import java.util.List;

public class AuthorizeRequestUnsigned {

    private List<EntityData> principals;
    private String action;
    private EntityData resource; 
    private Context context;

    public AuthorizeRequestUnsigned() {

        principals = new ArrayList<>();
    }

    public void addPrincipal(EntityData principal) {

        principals.add(principal);
    }

    public void setPrincipals(List<EntityData> principals) {

        this.principals = principals;
    }

    public List<EntityData> getPrincipals() {

        return principals;
    }
    
    public String getAction() {

        return action;
    }

    public void setAction(String action) {

        this.action = action;
    }

    public EntityData getResource() {

        return resource;
    }

    public void setResource(EntityData resource) {

        this.resource = resource;
    }

    public void setContext(Context context) {

        this.context = context;
    }

    public Context getContext() {

        return context;
    }
}
