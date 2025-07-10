/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

import java.util.ArrayList;
import java.util.List;

/**
 * An unsigned authorization request representation
 * <p>
 *  This class represents an unsigned authorization request
 *  as used in {@link io.jans.cedarling.bridge.Cedarling#authorizeUnsigned}. <br/>
 *  Authorization requests follow the PARC model as described in {@link io.jans.cedarling.bridge.authz.AuthorizeRequest}
 *  except for one difference. Instead of the principal(s) being derived from Signed JWTs, it is directly specified via 
 *  the {@link io.jans.cedarling.bridge.authz.AuthorizeRequestUnsigned#principals} field. 
 * </p>
 * 
 */
public class AuthorizeRequestUnsigned {

    private List<EntityData> principals;
    private String action;
    private EntityData resource; 
    private Context context;

    public AuthorizeRequestUnsigned() {

        principals = new ArrayList<>();
    }

    /**
     * Adds a principal to the request 
     * @param principal the principal to add
     */
    public void addPrincipal(EntityData principal) {

        principals.add(principal);
    }

    /**
     * Adds all the required principals at once to the request
     * @param principals a list of principals to add
     */
    public void setPrincipals(List<EntityData> principals) {

        this.principals = principals;
    }

    /**
     * Obtain the  list of principals used for the request
     * @return the list of principals used for the request 
     */
    public List<EntityData> getPrincipals() {

        return principals;
    }
    
    /**
     * Obtain the action used for the request
     * @return
     */
    public String getAction() {

        return action;
    }

    /**
     * Specify the action used for the request 
     * @param action
     */
    public void setAction(String action) {

        this.action = action;
    }

    /**
     * Obtain the resource used for the request
     * @return the resource object 
     */
    public EntityData getResource() {

        return resource;
    }

    /**
     * Specify the resource to use for the request 
     * @param resource
     */
    public void setResource(EntityData resource) {

        this.resource = resource;
    }

    /**
     * Specify the context to use for the request
     * @param context
     */
    public void setContext(Context context) {

        this.context = context;
    }

    /**
     * Obtain the context to use for the request
     * @return
     */
    public Context getContext() {

        return context;
    }
}
