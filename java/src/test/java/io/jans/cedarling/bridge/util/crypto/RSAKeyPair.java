package io.jans.cedarling.bridge.util.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAKeyPair {
    
    public static final int KEY_SIZE_1024_BITS = 1024;
    public static final int KEY_SIZE_2048_BITS = 2048;
    public static final int KEY_SIZE_4096_BITS = 4096; 

    private static final String RSA_ALGORITHM_NAME = "RSA";

    private final KeyPair keyPair;

    private RSAKeyPair(KeyPair keyPair) {

        this.keyPair = keyPair;
    }

    public RSAPrivateKey getPrivateKey() {

        return (RSAPrivateKey) keyPair.getPrivate();
    }

    public RSAPublicKey getPublicKey() {

        return (RSAPublicKey) keyPair.getPublic();
    }

    public static RSAKeyPair generate(int keysize) throws Exception {
        
        KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA_ALGORITHM_NAME);
        generator.initialize(keysize);
        KeyPair keypair = generator.generateKeyPair();
        return new RSAKeyPair(keypair);
    }
}
