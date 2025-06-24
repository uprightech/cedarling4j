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

    

    public EntityData getResource() {

        return resource;
    }

    public Context getContext() {

        return context;
    }

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
