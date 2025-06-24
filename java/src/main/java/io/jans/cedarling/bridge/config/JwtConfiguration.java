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

public class JwtConfiguration {
    
    private String jwks;
    private boolean jwtCheckSignValidation;
    private boolean jwtCheckStatusValidation;
    private List<JwtAlgorithm> supportedSignatureAlgorithms;

    public JwtConfiguration() {

        jwks = null;
        jwtCheckSignValidation = true;
        jwtCheckStatusValidation = true;
        supportedSignatureAlgorithms = new ArrayList<>();
    }

    public static JwtConfiguration withoutValidation() {

        JwtConfiguration ret = new JwtConfiguration();
        ret.jwtCheckSignValidation = false;
        ret.jwtCheckStatusValidation = false;
        ret.allowAllAlgorithms();
        return ret;
    }

    public String getJwks() {

        return jwks;
    }

    public JwtConfiguration setJwks(final String jwks) {

        this.jwks = jwks;
        return this;
    }

    public JwtConfiguration setJwtCheckSignValidation(final boolean enabled) {

        jwtCheckSignValidation = enabled;
        return this;
    }

    public boolean getJwtCheckSignValidation() {

        return jwtCheckSignValidation;
    }

    public boolean getJwtCheckStatusValidation() {

        return jwtCheckStatusValidation;
    }

    public JwtConfiguration setJwtCheckStatusValidation(final boolean enabled) {

        jwtCheckStatusValidation = enabled;
        return this;
    }

    public JwtConfiguration addSupportedSignatureAlgorithm(final JwtAlgorithm algorithm) {

        supportedSignatureAlgorithms.add(algorithm);
        return this;
    }

    public JwtConfiguration setSupportedSignatureAlgorithms(final List<JwtAlgorithm> algorithms) {

        supportedSignatureAlgorithms = algorithms;
        return this;
    }
    
    public List<JwtAlgorithm> getSupportedSignatureAlgorithms() {

        return supportedSignatureAlgorithms;
    }

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