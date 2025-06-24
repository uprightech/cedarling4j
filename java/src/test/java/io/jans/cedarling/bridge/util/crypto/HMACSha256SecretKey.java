package io.jans.cedarling.bridge.util.crypto;

import javax.crypto.*;

public class HMACSha256SecretKey {
    
    private static final String HMAC_SHA256_INSTANCE_NAME = "HmacSHA256";

    private final SecretKey secretKey;

    private HMACSha256SecretKey(SecretKey secretKey) {

        this.secretKey = secretKey;
    }

    public SecretKey getSecretKey() {
        
        return secretKey;
    }

    public static HMACSha256SecretKey createInstance() throws Exception {

        return new HMACSha256SecretKey(KeyGenerator.getInstance(HMAC_SHA256_INSTANCE_NAME).generateKey());
    }
}
