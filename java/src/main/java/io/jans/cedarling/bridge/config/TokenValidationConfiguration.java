/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

public class TokenValidationConfiguration {

    private boolean requireIssClaim = false;
    private boolean requireAudClaim = false;
    private boolean requireSubClaim = false;
    private boolean requireJtiClaim = false;
    private boolean requireIatClaim = false;
    private boolean requireExpClaim = false;
    private boolean requireNbfClaim = false;
    
    public static TokenValidationConfiguration createDefault() {

        return new TokenValidationConfiguration();
    }
    public static TokenValidationConfiguration forAccessToken() {

        TokenValidationConfiguration ret = new TokenValidationConfiguration();
        ret.requireIssClaim = true;
        ret.requireJtiClaim = true;
        ret.requireExpClaim = true;
        ret.requireNbfClaim = false;
        ret.requireAudClaim = false;
        ret.requireSubClaim = false;
        ret.requireIatClaim = false;
        return ret;
    }

    public static TokenValidationConfiguration forIdToken() {

        TokenValidationConfiguration ret = new TokenValidationConfiguration();
        ret.requireIssClaim = true;
        ret.requireAudClaim = true;
        ret.requireSubClaim = true;
        ret.requireExpClaim = true;
        ret.requireIatClaim = false;
        ret.requireJtiClaim = false;
        ret.requireNbfClaim = false;
        return ret;
    }

    public static TokenValidationConfiguration forUserInfoToken() {

        TokenValidationConfiguration ret = new TokenValidationConfiguration();
        ret.requireIssClaim = true;
        ret.requireAudClaim = true;
        ret.requireSubClaim = true;
        ret.requireExpClaim = true;
        ret.requireJtiClaim = false;
        ret.requireIatClaim = false;
        ret.requireNbfClaim = false;
        return ret;
    }

    public void requireIssClaim(boolean required) {

        requireIssClaim = required;
    }

    public boolean isIssClaimRequired() {

        return requireIssClaim;
    }

    public void requireAudClaim(boolean required) {

        requireAudClaim = required;
    }

    public boolean isAudClaimRequired() {

        return requireAudClaim;
    }

    public void requireSubClaim(boolean required) {

        requireSubClaim = required;
    }

    public boolean isSubClaimRequired() {

        return requireSubClaim;
    }

    public void requireJtiClaim(boolean required) {

        requireJtiClaim = required;
    }

    public boolean isJtiClaimRequired() {

        return requireJtiClaim;
    }

    public void requireIatClaim(boolean required) {

        requireIatClaim = required;
    }

    public boolean isIatClaimRequired() {

        return requireIatClaim;
    }

    public void requireExpClaim(boolean required) {

        requireExpClaim = required;
    }

    public boolean isExpClaimRequired() {

        return requireExpClaim;
    }

    public void requireNbfClaim(boolean required) {

        requireNbfClaim = required;
    }

    public boolean isNbfClaimRequired() {

        return requireNbfClaim;
    }
}
