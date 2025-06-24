/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge;

import io.jans.cedarling.bridge.CedarlingBaseTest;

import io.jans.cedarling.bridge.authz.*;
import io.jans.cedarling.bridge.config.*;
import io.jans.cedarling.bridge.util.*;
import io.jans.cedarling.bridge.util.crypto.*;
import io.jans.cedarling.bridge.util.jwt.*;
import io.jans.cedarling.bridge.util.jwt.impl.*;
import io.jans.cedarling.bridge.config.BootstrapConfiguration;
import io.jans.cedarling.bridge.config.JwtConfiguration;
import io.jans.cedarling.bridge.config.PolicyStoreConfiguration;

import java.io.File;
import java.util.stream.Stream;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CedarlingAuthorizationTest extends CedarlingBaseTest {
    
    private static final File POLICY_PERMIT_TEST_DATA_FILE = new File("authz-data/without-jwt-validation/policy_permit.json");
    private static final File POLICY_DENY_TEST_DATA_FILE = new File("authz-data/without-jwt-validation/policy_deny.json");

    private static final int RSA_KEY_SIZE = RSAKeyPair.KEY_SIZE_4096_BITS;
    private static final HashMap<String,HMACSha256SecretKey> TEST_HMAC_SHA_256_SECRET_KEYS = new HashMap<>();
    private static final HashMap<String,RSAKeyPair> TEST_RSA_KEY_PAIRS = new HashMap<>();
    private static String TEST_JWKS = null;
    private static final String TEST_HMAC_SIGNATURE = "HS256";
    private static final String TEST_RSA_SIGNATURE = "RS256";

    private static final List<PolicyStoreConfiguration> allPolicyStoreConfigurations() {

        return Arrays.asList(
            PolicyStoreConfiguration.fromYamlFile(new File(TEST_FILES_DIR,POLICY_STORE_OK_YAML_FILE)),
            PolicyStoreConfiguration.fromJsonFile(new File(TEST_FILES_DIR,POLICY_STORE_OK_JSON_FILE)),
            PolicyStoreConfiguration.fromYamlString(POLICY_STORE_OK_YAML_DATA),
            PolicyStoreConfiguration.fromJsonString(POLICY_STORE_OK_JSON_DATA)
        );
    }

    private static Stream<Arguments> shouldPermitActionGivenAuthzRequestParams() throws Exception {

        Stream.Builder<Arguments> ret = Stream.builder();
        
        JwtConfiguration jwtconfig_without_sign_validation = new JwtConfiguration()
            .setJwtCheckSignValidation(false)
            .setJwtCheckStatusValidation(false);
        
        HMACSha256SecretKey default_secret_key = HMACSha256SecretKey.createInstance();
        JwtGenerator default_jwt_generator = new HMACSignatureJwtGenerator(TEST_HMAC_SIGNATURE,default_secret_key.getSecretKey());
        AuthorizeRequest req_without_signed_tokens = CedarlingAuthzTestData
            .fromResourceFile(POLICY_PERMIT_TEST_DATA_FILE,default_jwt_generator)
            .toAuthorizeRequest();
        allPolicyStoreConfigurations().forEach((psconfig) -> {
            ret.add(Arguments.of(psconfig,jwtconfig_without_sign_validation,req_without_signed_tokens));
        });

        
        allPolicyStoreConfigurations().forEach((psconfig) -> {

            JwtConfiguration jwtconfig_with_sign_validation = new JwtConfiguration()
                .setJwks(TEST_JWKS)
                .setJwtCheckSignValidation(true)
                .setJwtCheckStatusValidation(true)
                .addSupportedSignatureAlgorithm(JwtAlgorithm.HS256);
            
            TEST_HMAC_SHA_256_SECRET_KEYS.forEach((kid,sk) -> {

                try {
                    JwtGenerator gen = new HMACSignatureJwtGenerator(TEST_HMAC_SIGNATURE,sk.getSecretKey());
                    AuthorizeRequest req = CedarlingAuthzTestData.fromResourceFile(POLICY_PERMIT_TEST_DATA_FILE,gen).toAuthorizeRequest();
                    ret.add(Arguments.of(psconfig,jwtconfig_with_sign_validation,req));
                }catch(Exception e) {
                    
                    throw new RuntimeException("HMAC-256 test data generation failed",e);
                }

            });
        });

        allPolicyStoreConfigurations().forEach((psconfig) -> {

            JwtConfiguration jwtconfig_with_sign_validation = new JwtConfiguration()
                .setJwks(TEST_JWKS)
                .setJwtCheckSignValidation(true)
                .setJwtCheckStatusValidation(true)
                .addSupportedSignatureAlgorithm(JwtAlgorithm.RS256);
            
            TEST_RSA_KEY_PAIRS.forEach((kid,kp) -> {

                try {
                    JwtGenerator gen = new RSASignatureJwtGenerator(TEST_RSA_SIGNATURE,kp.getPrivateKey());
                    AuthorizeRequest req = CedarlingAuthzTestData.fromResourceFile(POLICY_PERMIT_TEST_DATA_FILE,gen).toAuthorizeRequest();
                    ret.add(Arguments.of(psconfig,jwtconfig_with_sign_validation,req));
                }catch(Exception e) {
                    
                    throw new RuntimeException("RSA-256 test data generation failed",e);
                }

            });
        });

        return ret.build();
    }

    private static Stream<Arguments> shouldDenyActionGivenAuthzRequestParams() throws Exception {

        Stream.Builder<Arguments> ret = Stream.builder();
        JwtConfiguration jwtconfig_without_sign_validation = new JwtConfiguration()
            .setJwtCheckSignValidation(false)
            .setJwtCheckStatusValidation(false);
        
        HMACSha256SecretKey default_secret_key = HMACSha256SecretKey.createInstance();
        JwtGenerator default_jwt_generator = new HMACSignatureJwtGenerator(TEST_HMAC_SIGNATURE,default_secret_key.getSecretKey());
        AuthorizeRequest req_without_signed_tokens = CedarlingAuthzTestData
            .fromResourceFile(POLICY_DENY_TEST_DATA_FILE,default_jwt_generator)
            .toAuthorizeRequest();
        allPolicyStoreConfigurations().forEach((psconfig) -> {
            ret.add(Arguments.of(psconfig,jwtconfig_without_sign_validation,req_without_signed_tokens));
        });

        allPolicyStoreConfigurations().forEach((psconfig) -> {

            JwtConfiguration jwtconfig_with_sign_validation = new JwtConfiguration()
                .setJwks(TEST_JWKS)
                .setJwtCheckSignValidation(true)
                .setJwtCheckStatusValidation(true)
                .addSupportedSignatureAlgorithm(JwtAlgorithm.HS256);
            
            TEST_HMAC_SHA_256_SECRET_KEYS.forEach((kid,sk) -> {

                try {
                    JwtGenerator gen = new HMACSignatureJwtGenerator(TEST_HMAC_SIGNATURE,sk.getSecretKey());
                    AuthorizeRequest req = CedarlingAuthzTestData.fromResourceFile(POLICY_DENY_TEST_DATA_FILE,gen).toAuthorizeRequest();
                    ret.add(Arguments.of(psconfig,jwtconfig_with_sign_validation,req));
                }catch(Exception e) {
                    
                    throw new RuntimeException("HMAC-256 test data generation failed",e);
                }

            });
        });

        allPolicyStoreConfigurations().forEach((psconfig) -> {

            JwtConfiguration jwtconfig_with_sign_validation = new JwtConfiguration()
                .setJwks(TEST_JWKS)
                .setJwtCheckSignValidation(true)
                .setJwtCheckStatusValidation(true)
                .addSupportedSignatureAlgorithm(JwtAlgorithm.RS256);
            
            TEST_RSA_KEY_PAIRS.forEach((kid,kp) -> {

                try {
                    JwtGenerator gen = new RSASignatureJwtGenerator(TEST_RSA_SIGNATURE,kp.getPrivateKey());
                    AuthorizeRequest req = CedarlingAuthzTestData.fromResourceFile(POLICY_DENY_TEST_DATA_FILE,gen).toAuthorizeRequest();
                    ret.add(Arguments.of(psconfig,jwtconfig_with_sign_validation,req));
                }catch(Exception e) {
                    
                    throw new RuntimeException("RSA-256 test data generation failed",e);
                }

            });
        });
        return ret.build();
    }

    private static final String newKeyId() {

        return java.util.UUID.randomUUID().toString();
    }
    
    private static final void setupTestHmacCryptographicMaterial() throws Exception {

        TEST_HMAC_SHA_256_SECRET_KEYS.put(newKeyId(),HMACSha256SecretKey.createInstance());
    }

    private static final void setupTestRsaCryptographicMaterial() throws Exception {

        TEST_RSA_KEY_PAIRS.put(newKeyId(),RSAKeyPair.generate(RSA_KEY_SIZE));
    }

    private static final void generateJwks() throws Exception {

        Jwks jwks = new Jwks();
        TEST_HMAC_SHA_256_SECRET_KEYS.forEach((keyid,value) -> {
            jwks.addHMAC256SecretKey(keyid,value.getSecretKey());
        });

        TEST_RSA_KEY_PAIRS.forEach((keyid,value) -> {
            jwks.addRSAPublicKey(keyid,value.getPublicKey());
        });

        TEST_JWKS = jwks.getJwksAsString();
    }

    @BeforeAll
    public static final void setupTestResources() throws Exception {

        setupTestHmacCryptographicMaterial();
        setupTestRsaCryptographicMaterial();
        generateJwks();
    }

    @ParameterizedTest
    @MethodSource({"shouldPermitActionGivenAuthzRequestParams"})
    public void shouldPermitActionGivenAuthzRequest(PolicyStoreConfiguration psconfig, JwtConfiguration jwtconfig,AuthorizeRequest request) throws Exception {

        
        BootstrapConfiguration config = getBaseBootstrapConfigurationBuilder()
            .policyStoreConfiguration(psconfig)
            .jwtConfiguration(jwtconfig)
            .build();
        
        try (Cedarling cedarling = new Cedarling(config)) {
            
            AuthorizeResult result = cedarling.authorize(request);
            assertTrue(result.isAllowed());
            assertNotNull(result.getRequestId());
        }
    }

    @ParameterizedTest
    @MethodSource({"shouldDenyActionGivenAuthzRequestParams"})
    public void shouldDenyActionGivenAuthzRequest(PolicyStoreConfiguration psconfig, JwtConfiguration jwtconfig,AuthorizeRequest request) throws Exception { 

        BootstrapConfiguration config = getBaseBootstrapConfigurationBuilder()
            .policyStoreConfiguration(psconfig)
            .jwtConfiguration(jwtconfig)
            .build();
        
        try (Cedarling cedarling = new Cedarling(config)) {
            File test_data_file = POLICY_DENY_TEST_DATA_FILE;
            AuthorizeResult result = cedarling.authorize(request);
            assertTrue(!result.isAllowed());
            assertNotNull(result.getRequestId());
        }
    }
}
