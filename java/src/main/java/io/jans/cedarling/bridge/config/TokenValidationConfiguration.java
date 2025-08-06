/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Token validation configuration used in cedarling
 */
public class TokenValidationConfiguration {

    private boolean requireIssClaim = false;
    private boolean requireAudClaim = false;
    private boolean requireSubClaim = false;
    private boolean requireJtiClaim = false;
    private boolean requireIatClaim = false;
    private boolean requireExpClaim = false;
    private boolean requireNbfClaim = false;
    
    /**
     * Creates the default token validation configuration 
     * @return an instance of the token validatin configuration with default values
     */
    public static TokenValidationConfiguration createDefault() {

        return new TokenValidationConfiguration();
    }

    /**
     * Creates a token validation configuration for an access token
     * <p>
     *   Access token validation is configured as follows:
     * </p>
     * <ul>
     *   <li>The <b>iss</b> claim is required</li>
     *   <li>The <b>jti</b> claim is required</li>
     *   <li>The <b>exp</b> claim is required</li>
     *   <li>The <b>nbf</b> claim is not required</li>
     *   <li>The <b>aud</b> claim is not required</li>
     *   <li>The <b>sub</b> claim is not required</li>
     *   <li>The <b>iat</b> claim is not required</li>
     * </ul>
     * @return an instance of the token validation configured that validates an access token
     */
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

   /**
     * Creates a token validation configuration for an id token
     * <p>
     *   Id token validation is configured as follows:
     * </p>
     * <ul>
     *   <li>The <b>iss</b> claim is required</li>
     *   <li>The <b>jti</b> claim is required</li>
     *   <li>The <b>exp</b> claim is required</li>
     *   <li>The <b>nbf</b> claim is not required</li>
     *   <li>The <b>aud</b> claim is not required</li>
     *   <li>The <b>sub</b> claim is not required</li>
     *   <li>The <b>iat</b> claim is not required</li>
     * </ul>
     * @return an instance of the token validation configured that validates an access token
     */
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

    /**
     * Creates a token validation configuration for a userinfo token
     * <p>
     *   Userinfo token validation is configured as follows:
     * </p>
     * <ul>
     *   <li>The <b>iss</b> claim is required</li>
     *   <li>The <b>jti</b> claim is required</li>
     *   <li>The <b>exp</b> claim is required</li>
     *   <li>The <b>nbf</b> claim is not required</li>
     *   <li>The <b>aud</b> claim is not required</li>
     *   <li>The <b>sub</b> claim is not required</li>
     *   <li>The <b>iat</b> claim is not required</li>
     * </ul>
     * @return an instance of the token validation configured that validates an access token
     */
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

    /**
     * Specifies if the <b>iss</b>  claim is required
     * @param required a flag which toggles if the claim is required or not
     */
    public void requireIssClaim(boolean required) {

        requireIssClaim = required;
    }

    /**
     * Checks whether the <b>iss</b> claim is required for token validation 
     * @return a flag which is {@code true} if the claim is required and false otherwise
     */
    public boolean isIssClaimRequired() {

        return requireIssClaim;
    }

    /**
     * Specifies if the <b>aud</b>  claim is required
     * @param required a flag which toggles if the claim is required or not
     */
    public void requireAudClaim(boolean required) {

        requireAudClaim = required;
    }

    /**
     * Checks whether the <b>aud</b> claim is required for token validation 
     * @return a flag which is {@code true} if the claim is required and false otherwise
     */
    public boolean isAudClaimRequired() {

        return requireAudClaim;
    }

    /**
     * Specifies if the <b>sub</b>  claim is required
     * @param required a flag which toggles if the claim is required or not
     */
    public void requireSubClaim(boolean required) {

        requireSubClaim = required;
    }

    /**
     * Checks whether the <b>aud</b> claim is required for token validation 
     * @return a flag which is {@code true} if the claim is required and false otherwise
     */
    public boolean isSubClaimRequired() {

        return requireSubClaim;
    }

    /**
     * Specifies if the <b>jti</b>  claim is required
     * @param required a flag which toggles if the claim is required or not
     */
    public void requireJtiClaim(boolean required) {

        requireJtiClaim = required;
    }

    /**
     * Checks whether the <b>jti</b> claim is required for token validation 
     * @return a flag which is {@code true} if the claim is required and false otherwise
     */
    public boolean isJtiClaimRequired() {

        return requireJtiClaim;
    }

    /**
     * Specifies if the <b>iat</b>  claim is required
     * @param required a flag which toggles if the claim is required or not
     */
    public void requireIatClaim(boolean required) {

        requireIatClaim = required;
    }


    /**
     * Checks whether the <b>iat</b> claim is required for token validation 
     * @return a flag which is {@code true} if the claim is required and false otherwise
     */
    public boolean isIatClaimRequired() {

        return requireIatClaim;
    }

    /**
     * Specifies if the <b>exp</b>  claim is required
     * @param required a flag which toggles if the claim is required or not
     */
    public void requireExpClaim(boolean required) {

        requireExpClaim = required;
    }

    /**
     * Checks whether the <b>exp</b> claim is required for token validation 
     * @return a flag which is {@code true} if the claim is required and false otherwise
     */
    public boolean isExpClaimRequired() {

        return requireExpClaim;
    }

    /**
     * Specifies if the <b>exp</b>  claim is required
     * @param required a flag which toggles if the claim is required or not
     */
    public void requireNbfClaim(boolean required) {

        requireNbfClaim = required;
    }

    /**
     * Checks whether the <b>exp</b> claim is required for token validation 
     * @return a flag which is {@code true} if the claim is required and false otherwise
     */
    public boolean isNbfClaimRequired() {

        return requireNbfClaim;
    }
}
