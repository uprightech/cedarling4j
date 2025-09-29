package io.jans.cedarling.bridge; 

import io.jans.cedarling.bridge.CedarlingBaseTest;
import io.jans.cedarling.bridge.authz.AuthorizeRequest;
import io.jans.cedarling.bridge.authz.AuthorizeRequestUnsigned;
import io.jans.cedarling.bridge.authz.AuthorizeResult;
import io.jans.cedarling.bridge.authz.CedarEntityMapping;
import io.jans.cedarling.bridge.authz.Context;
import io.jans.cedarling.bridge.authz.EntityData;
import io.jans.cedarling.bridge.config.AuthorizationConfiguration;
import io.jans.cedarling.bridge.config.BootstrapConfiguration;
import io.jans.cedarling.bridge.config.EntityBuilderConfiguration;
import io.jans.cedarling.bridge.config.JsonRule;
import io.jans.cedarling.bridge.config.PolicyStoreConfiguration;
import io.jans.cedarling.bridge.util.CedarlingAuthzTestData;
import io.jans.cedarling.bridge.util.crypto.RSAKeyPair;
import io.jans.cedarling.bridge.util.jwt.JwtGenerator;
import io.jans.cedarling.bridge.util.jwt.Jwks;
import io.jans.cedarling.bridge.util.jwt.impl.HMACSignatureJwtGenerator;
import io.jans.cedarling.bridge.util.jwt.impl.RSASignatureJwtGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;



public class CedarlingAuthorizationWithJwtValidationTest {
    
    private static final String TEST_SIGNATURE_ALGORITHM = "RS256";

    private static final String POLICY_PERMIT_AUTHZ_DATA_FILE = "authz-data/with-jwt-validation/policy_permit.json";
    private static final String POLICY_DENY_AUTHZ_DATA_FILE = "authz-data/with-jwt-validation/policy_deny.json";

    private static RSAKeyPair RSA_SIGNING_KEY_PAIR = null;
    private static CedarlingAuthzTestData POLICY_PERMIT_AUTHZ_DATA = null;
    private static CedarlingAuthzTestData POLICY_DENY_AUTHZ_DATA = null;

    private static String GENERATED_JWKS = null;

    @BeforeAll
    public static final void setupTestResources() throws Exception {

        RSA_SIGNING_KEY_PAIR = RSAKeyPair.generate(RSAKeyPair.KEY_SIZE_2048_BITS);
        String rsa_key_id = UUID.randomUUID().toString();
        Jwks jwks = new Jwks();
        jwks.addRSAPublicKey(rsa_key_id,RSA_SIGNING_KEY_PAIR.getPublicKey());
        JwtGenerator jwt_gen = new RSASignatureJwtGenerator(TEST_SIGNATURE_ALGORITHM, RSA_SIGNING_KEY_PAIR.getPrivateKey());
        POLICY_PERMIT_AUTHZ_DATA = CedarlingAuthzTestData.fromResourceFile(new File(POLICY_PERMIT_AUTHZ_DATA_FILE), jwt_gen);
        POLICY_DENY_AUTHZ_DATA = CedarlingAuthzTestData.fromResourceFile(new File(POLICY_DENY_AUTHZ_DATA_FILE), jwt_gen);
        GENERATED_JWKS = jwks.getJwksAsString();
    }

}

