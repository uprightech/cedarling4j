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
 *      The <i>principal</i>  is derived from the signed JWT tokens in the request. Their derivation is out of the scope 
 *      of this document, but can be found <a href="https://docs.jans.io/stable/cedarling/cedarling-entitities">here</a>
 *  </li>
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
     * Gets authorization request resource which will be used to evaluate an authorization request
     * @return the resource used for an authorization request 
     */
    public EntityData getResource() {

        return resource;
    }

    /**
     * Gets the authorization request context which will be used to evaluate an authorization request 
     * @return the context used for an authorization request
     */
    public Context getContext() {

        return context;
    }

    /**
     * Gets the action which will be used to evaluate an authorization request
     * @return the action used for an authorization request
     */
    public String getAction() {

        return action;
    }


    /**
     * 
     * @param name the token's name , e.g. &quot; access_token &quot;
     * @param value the token's value specified as a igned JWT string 
     */
    public void addToken(final String name, final String value) {

        tokens.put(name,value);
    }

    /**
     * 
     * @param name the token' name 
     * @return the token's value as a signed JWT string
     */
    public String getToken(final String name) {

        return tokens.get(name);
    }

    @SuppressWarnings("unused")
    private List<String> getTokenNames() {

        List<String> ret = new ArrayList<>();
        ret.addAll(tokens.keySet());
        return ret;
    }
    
    /**
     * Creates an {@link io.jans.cedarling.bridge.authz.AuthorizeRequest} builder  
     * @return an instance of the builder
     */
    public static Builder builder() {

        return new Builder();
    }

    /**
     * Builder to facilitate the creation of {@link io.jans.cedarling.bridge.authz.AuthorizeRequest} objects
     */
    public static class Builder {

        private final AuthorizeRequest request_;

        /**
         * Constructor
         */
        public Builder() {
            request_ = new AuthorizeRequest();
        }

        /**
         * Adds a token to the authoriation request being built 
         * @param token the token's name. See {@link io.jans.cedarling.bridge.authz.AuthorizeRequest#addToken(String, String)}
         * @param value the token's value as a signed JWT string. See {@link io.jans.cedarling.bridge.authz.AuthorizeRequest#addToken(String, String)}
         * @return the current builder's instance
         */
        public Builder token(final String token, final String value) {

            request_.addToken(token, value);
            return this;
        }

        /**
         * Adds several tokens at once to the authoriation request being built 
         * @param tokens A map containing the tokens where the key is the token's name and the value it's signed JWT value
         * @return the current builder instance
         */
        public Builder tokens(Map<String,String> tokens) { 

            request_.tokens = tokens;
            return this;
        }

        /**
         * Adds an access token to the authoriation request being built
         * This method is equivalent to {@code builder.token("access_token",value)}
         * @param value the access token as a signed JWT token 
         * @return the  current builder instance
         */
        public Builder accessToken(final String value) {

            request_.addToken(ACCESS_TOKEN_KEY,value);
            return this;
        }

        /**
         * Adds an id token to the authorization request being built 
         * This method is equivalent to {@code builder.token("id_token",value)}
         * @param value the id token as a signed JWT token
         * @return the current builder instance 
         */
        public Builder idToken(final String value) {

            request_.addToken(ID_TOKEN_KEY,value);
            return this;
        }

        /**
         * Adds a userinfo token to the authorization request being built
         * This method is equivalent to {@code builder.token("userinfo_token",value)}
         * @param value the userinfo token as a signed JWT token
         * @return the current builder instance
         */
        public Builder userInfoToken(final String value) {

            request_.addToken(USER_INFO_TOKEN_KEY,value);
            return this;
        }

        /**
         * Specify the action that will be used when performing authorization against a request
         * @param action the authorization request's action
         * @return the current builder instance
         */
        public Builder action(final String action ) {

            request_.action = action;
            return this;
        }

        /**
         * Specify the resource that will be used when performing authorization against a request
         * @param resource the resource 
         * @return the current builder instance 
         */
        public Builder resource(final EntityData resource) {

            request_.resource = resource;
            return this;
        }

        /**
         * Specify the context that will be used when performing authorization against a request
         * @param context the context
         * @return the current builder instance
         */
        public Builder context(final Context context) {

            request_.context = context;
            return this;
        }

        /**
         * Builds the authorization request after performing basic health checks on the request's elements.
         * @return an instance of the authorization request built to the specifications of the caller 
         * @throws IllegalArgumentException
         */
        public AuthorizeRequest build() throws IllegalArgumentException {

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
