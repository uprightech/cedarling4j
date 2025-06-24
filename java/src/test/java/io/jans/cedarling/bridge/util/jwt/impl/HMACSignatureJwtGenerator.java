/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.util.jwt.impl;

import io.jans.cedarling.bridge.util.jwt.JwtGenerator;

import com.nimbusds.jwt.*;

import javax.crypto.SecretKey;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;

public class HMACSignatureJwtGenerator implements JwtGenerator {
    
    private final JWSAlgorithm signingAlgorithm;
    private final MACSigner signer;

    public HMACSignatureJwtGenerator(String signingAlgorithm,SecretKey secretKey) throws Exception {
        this.signingAlgorithm = JWSAlgorithm.parse(signingAlgorithm);
        this.signer = new MACSigner(secretKey.getEncoded());
    }

    @Override
    public String generate(String value) throws Exception {

        JWSHeader header = new JWSHeader(signingAlgorithm);
        SignedJWT signedjwt = new SignedJWT(header,JWTClaimsSet.parse(value));
        signedjwt.sign(signer);
        return signedjwt.serialize();
    }
}
