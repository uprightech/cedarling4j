/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.util.jwt;

import io.jans.cedarling.bridge.util.crypto.HMACSha256SecretKey;

import com.nimbusds.jose.*;
import com.nimbusds.jose.jwk.*;

import org.json.JSONObject;

import java.security.interfaces.RSAPublicKey;
import java.util.Date;

import javax.crypto.SecretKey;

import org.json.JSONArray;

public class Jwks {
    
    private JSONObject jwks;
    private JSONArray  keys;

    public Jwks() {

        jwks = new JSONObject();
        keys = new JSONArray();
        jwks.put("keys",keys);
    }

    public void addHMAC256SecretKey(final String keyId,SecretKey secretkey) {

        JWK jwk = new OctetSequenceKey.Builder(secretkey)
            .keyID(keyId)
            .algorithm(JWSAlgorithm.HS256)
            .issueTime(new Date())
            .build();
        
        JSONObject entry = new JSONObject(jwk.toJSONString());
        keys.put(entry);
    }

    public void addRSAPublicKey(final String keyId, RSAPublicKey publicKey) {

        JWK jwk = new RSAKey.Builder(publicKey)
            .keyUse(KeyUse.SIGNATURE)
            .keyID(keyId)
            .issueTime(new Date())
            .build();
        JSONObject entry = new JSONObject(jwk.toJSONString());
        keys.put(entry);
    }

    public String getJwksAsString() {

        return jwks.toString();
    }
}
