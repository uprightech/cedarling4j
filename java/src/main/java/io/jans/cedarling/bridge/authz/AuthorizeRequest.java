/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

import io.jans.cedarling.bridge.util.Constraints;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * An authorization request representation with signed tokens for principal derivation
 * <p>
 *  This class represents an authorization request with signed JWT tokens 
 *  as used in {@link io.jans.cedarling.bridge.Cedarling#authorize}. <br/>
 *  Authorization requests follow the PARC model:
 * </p>
 * <ul>
 *  <li>
 *      The <i>action</i> is represented by the {@link io.jans.cedarling.bridge.authz.AuthorizeRequest#action} field. <br/> 
 *      An example action would be Jans::Action::&quot;Update&quot;
 *  </li>
 *  <li>
 *      The <i>resource</i> is represented by the {@link io.jans.cedarling.bridge.authz.AuthorizeRequest#resource} field. <br/>
 *      The resource has an id, type and associated attributes. See {@link io.jans.cedarling.bridge.authz.EntityData} for more information.
 *  </li>
 *  <li>
 *      The <i>context</i> is represented by the {@link io.jans.cedarling.bridge.authz.AuthorizeRequest#context}. <br/>
 *      This is a serialized json object, wrapped in a {@link io.jans.cedarling.bridge.authz.Context}. See the latter class for more information.
 *  </li>
 * </ul>
 * <p>
 *  In order to prevent ill formed objects,  authorization requests are instantiated using <br/>
 *  a {@link io.jans.cedarling.bridge.authz.AuthorizeRequest.Builder Builder} class which can itself 
 *  be instantiated by calling the 
 * {@link io.jans.cedarling.bridge.authz.AuthorizeRequest#builder()} static method.
 * </p>
 */
public class AuthorizeRequest {
    
    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String ID_TOKEN_KEY = "id_token";
    private static final String USER_INFO_TOKEN_KEY = "userinfo_token";

    private Map<String,String> tokens;
    private String action;
    private EntityData resource;
    private Context context;

    private AuthorizeRequest() {

        tokens = new HashMap<>();
    }

    /**
     * Gets authorization request resouce 
     */
    public EntityData getResource() {

        return resource;
    }

    /**
     * 
     */
    public Context getContext() {

        return context;
    }

    /**
     * 
     */
    public String getAction() {

        return action;
    }


    public void addToken(final String name, final String value) {

        tokens.put(name,value);
    }

    public String getToken(final String name) {

        return tokens.get(name);
    }

    @SuppressWarnings("unused")
    private List<String> getTokenNames() {

        List<String> ret = new ArrayList<>();
        ret.addAll(tokens.keySet());
        return ret;
    }
    
    public static Builder builder() {

        return new Builder();
    }

    public static class Builder {

        private final AuthorizeRequest request_;

        public Builder() {
            request_ = new AuthorizeRequest();
        }

        public Builder token(final String token, final String value) {

            request_.addToken(token, value);
            return this;
        }

        public Builder tokens(Map<String,String> tokens) { 

            request_.tokens = tokens;
            return this;
        }

        public Builder accessToken(final String value) {

            request_.addToken(ACCESS_TOKEN_KEY,value);
            return this;
        }

        public Builder idToken(final String value) {

            request_.addToken(ID_TOKEN_KEY,value);
            return this;
        }

        public Builder userInfoToken(final String value) {

            request_.addToken(USER_INFO_TOKEN_KEY,value);
            return this;
        }

        public Builder action(final String action ) {

            request_.action = action;
            return this;
        }

        public Builder resource(final EntityData resource) {

            request_.resource = resource;
            return this;
        }

        public Builder context(final Context context) {

            request_.context = context;
            return this;
        }

        public AuthorizeRequest build() {

            Constraints.ensureNotNull(request_.getToken(ACCESS_TOKEN_KEY),"Missing action token");
            Constraints.ensureNotNull(request_.getToken(ID_TOKEN_KEY),"Missing id token");
            Constraints.ensureNotNull(request_.getToken(USER_INFO_TOKEN_KEY),"Missing user info token");
            Constraints.ensureNotNull(request_.action,"Action cannot be null");
            Constraints.ensureNotNull(request_.resource,"Resource cannot be null");
            Constraints.ensureNotNull(request_.context,"Context cannot be null");

            return request_;
        }

    }
}
