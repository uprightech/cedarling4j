/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.util.jwt.impl;

import java.security.interfaces.RSAPrivateKey;

import io.jans.cedarling.bridge.util.jwt.JwtGenerator;

import com.nimbusds.jwt.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;

public class RSASignatureJwtGenerator implements JwtGenerator {
    
    private final JWSSigner signer;
    private final String keyId;
    private final JWSAlgorithm signingAlgorithm;

    public RSASignatureJwtGenerator(String signingAlgorithm,String keyId, RSAPrivateKey privateKey) {

        signer = new RSASSASigner(privateKey);
        this.keyId = keyId;
        this.signingAlgorithm = JWSAlgorithm.parse(signingAlgorithm);
    }
    public RSASignatureJwtGenerator(String signingAlgorithm,RSAPrivateKey privateKey) {

        signer = new RSASSASigner(privateKey);
        keyId = null;
        this.signingAlgorithm = JWSAlgorithm.parse(signingAlgorithm);
    }

    @Override
    public String generate(String claims) throws Exception {

        JWSHeader header = null;
        if(keyId == null) {
            header = new JWSHeader(signingAlgorithm);
        }else {
            header = new JWSHeader.Builder(signingAlgorithm).keyID(keyId).build();
        }
        SignedJWT signedjwt = new SignedJWT(header,JWTClaimsSet.parse(claims));
        signedjwt.sign(signer);

        return signedjwt.serialize();
    }
}
