/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.jans.cedarling.bridge.config.JwtAlgorithm;
import io.jans.cedarling.bridge.config.TokenValidationConfiguration;

/**
 * Cedarling Jwt Configuration 
 * <p>
 *   Contains 
 * </p>
 */
public class JwtConfiguration {
    
    private String jwks;
    private boolean jwtCheckSignValidation;
    private boolean jwtCheckStatusValidation;
    private List<JwtAlgorithm> supportedSignatureAlgorithms;

    /**
     * Default constructor
     * <p>The default jwt configuration is very restrictive: </p>
     * <ul>
     *  <li>No jwks is specified ({@link io.jans.cedarling.bridge.config.JwtConfiguration#jwks} is {@code null})</li>
     *  <li>Signature validation is disabled ({@link io.jans.cedarling.bridge.config.JwtConfiguration#jwtCheckSignValidation} is {@code false})</li>
     *  <li>Status validation is disabled ({@link io.jans.cedarling.bridge.config.JwtConfiguration#jwtCheckStatusValidation} is {@code false})</li>
     *  <li>No supported signature algorithms ({@link io.jans.cedarling.bridge.config.JwtConfiguration#supportedSignatureAlgorithms} is empty)</li>
     * </ul>
     */
    public JwtConfiguration() {

        jwks = null;
        jwtCheckSignValidation = true;
        jwtCheckStatusValidation = true;
        supportedSignatureAlgorithms = new ArrayList<>();
    }

    /**
     * Helper to create a Jwt configuration with validation disabled and support
     * for all JWT validation algorithms 
     * @return the jwt configuration to the aforementionned specifications
     */
    public static JwtConfiguration withoutValidation() {

        JwtConfiguration ret = new JwtConfiguration();
        ret.jwtCheckSignValidation = false;
        ret.jwtCheckStatusValidation = false;
        ret.allowAllAlgorithms();
        return ret;
    }

    /**
     * Gets the JWKS with public keys.
     * @return a string containing the JWKS
     */
    public String getJwks() {

        return jwks;
    }

    /**
     * Specifies the local JWKS to be used 
     * <p>
     *   If this is used , cedarling will no longer try to fetch the JWKS stores from
     *   a trusted identity provider and will stick to using the local JWKS. The jwks
     *   is optional.
     * </p>
     * @param jwks
     * @return
     */
    public JwtConfiguration setJwks(final String jwks) {

        this.jwks = jwks;
        return this;
    }

    /**
     * Enables jwt signature validation 
     * @param enabled the flag indicating validation being enabled ({@code true}) or disabled
     * @return the current instance of this configuration
     */
    public JwtConfiguration setJwtCheckSignValidation(final boolean enabled) {

        jwtCheckSignValidation = enabled;
        return this;
    }

    /**
     * Checks if jwt signature validation is enabled
     * @return {@code true} if jwt signature validation is enabled. {@code false} otherwise.
     */
    public boolean getJwtCheckSignValidation() {

        return jwtCheckSignValidation;
    }

    /**
     * Checks if jwt status validation is enabled 
     * @return {@code true} if jwt status validation is enabled. {@code false} otherwise.
     */
    public boolean getJwtCheckStatusValidation() {

        return jwtCheckStatusValidation;
    }

    /**
     * Enables jwt status validation. 
     * <p>
     *   More information about jwt status validation can be found in the following 
     *   <a href="https://datatracker.ietf.org/doc/draft-ietf-oauth-status-list/">RFC</a>
     * </p>
     * @param enabled {@code true} to enable jwt status validation. {@code false} otherwise.
     * @return the current instance of this configuration
     */
    public JwtConfiguration setJwtCheckStatusValidation(final boolean enabled) {

        jwtCheckStatusValidation = enabled;
        return this;
    }

    /**
     * Adds a signature algorithm to the list of supported jwt signature algorithms
     * @param algorithm the algorithm to add
     * @return the current instance of this configuration
     */
    public JwtConfiguration addSupportedSignatureAlgorithm(final JwtAlgorithm algorithm) {

        supportedSignatureAlgorithms.add(algorithm);
        return this;
    }

    /**
     * Set supported the jwt signature validation algorithms
     * @param algorithms the algorithms provided as a list
     * @return the current instance of this configuration
     */
    public JwtConfiguration setSupportedSignatureAlgorithms(final List<JwtAlgorithm> algorithms) {

        supportedSignatureAlgorithms = algorithms;
        return this;
    }
    
    /**
     * Gets the list of supported jwt signature validation algorithms
     * @return a list of supported jwt signature algorithms
     */
    public List<JwtAlgorithm> getSupportedSignatureAlgorithms() {

        return supportedSignatureAlgorithms;
    }

    /**
     * Allows all supported jwt signature algorithms to be added to the supported list of algorithms
     * @return the currnet instance of this configuration
     */
    public JwtConfiguration allowAllAlgorithms() {

        supportedSignatureAlgorithms = new ArrayList<>(
            Arrays.asList(
                JwtAlgorithm.HS256,
                JwtAlgorithm.HS384,
                JwtAlgorithm.HS512,
                JwtAlgorithm.ES256,
                JwtAlgorithm.ES384,
                JwtAlgorithm.RS256,
                JwtAlgorithm.RS384,
                JwtAlgorithm.RS512,
                JwtAlgorithm.PS256,
                JwtAlgorithm.PS384,
                JwtAlgorithm.PS512,
                JwtAlgorithm.EdDSA
            )
        );
        
        return this;
    }

    

}