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
import io.jans.cedarling.bridge.util.crypto.HMACSha256SecretKey;
import io.jans.cedarling.bridge.util.jwt.JwtGenerator;
import io.jans.cedarling.bridge.util.jwt.impl.HMACSignatureJwtGenerator;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class CedarlingAuthorizationWithoutJwtValidationTest extends CedarlingBaseTest {
    
    private static final String SIGNATURE_ALGORITHM_TO_USE = "HS256";
    private static final String POLICY_PERMIT_AUTHZ_DATA_FILE = "authz-data/without-jwt-validation/policy_permit.json";
    private static final String POLICY_DENY_AUTHZ_DATA_FILE = "authz-data/without-jwt-validation/policy_deny.json";
    private static HMACSha256SecretKey HMAC_SIGNATURE_KEY = null;
    private static CedarlingAuthzTestData POLICY_PERMIT_AUTHZ_DATA = null;
    private static CedarlingAuthzTestData POLICY_DENY_AUTHZ_DATA = null;

    @BeforeAll
    public static final void setupTestResources() throws Exception {

        HMAC_SIGNATURE_KEY = HMACSha256SecretKey.createInstance();
        JwtGenerator unsigned_jwt_gen = new HMACSignatureJwtGenerator(SIGNATURE_ALGORITHM_TO_USE, HMAC_SIGNATURE_KEY.getSecretKey());
        POLICY_PERMIT_AUTHZ_DATA = CedarlingAuthzTestData.fromResourceFile(new File(POLICY_PERMIT_AUTHZ_DATA_FILE), unsigned_jwt_gen);
        POLICY_DENY_AUTHZ_DATA = CedarlingAuthzTestData.fromResourceFile(new File(POLICY_DENY_AUTHZ_DATA_FILE), unsigned_jwt_gen);
    }

    private BootstrapConfiguration getDefaultBootstrapConfiguration() throws Exception  {

        EntityBuilderConfiguration eb_config = getBaseEntityBuilderConfiguration();
        AuthorizationConfiguration authz_config = getBaseAuthzConfiguration();
        final File policy_store_file = new File(TEST_FILES_DIR,POLICY_STORE_OK_YAML_FILE);

        authz_config.addDecisionLogUserClaim("client_id");
        authz_config.addDecisionLogUserClaim("username");
        authz_config.addDecisionLogWorkloadClaims("org_id");
        authz_config.setPrincipalBoolOperator(new JsonRule(DEFAULT_JSON_RULE));

        return getBaseBootstrapConfigurationBuilder()
            .authzConfiguration(authz_config)
            .policyStoreConfiguration(PolicyStoreConfiguration.fromYamlFile(policy_store_file))
            .entityBuilderConfiguration( getBaseEntityBuilderConfiguration())
            .build();

    }

    @Test
    public void authzShouldSucceedWhenActionIsPermitted() throws Exception {

        BootstrapConfiguration bootstrap_config = getDefaultBootstrapConfiguration();

        try(Cedarling cedarling = new Cedarling(bootstrap_config)) {

            AuthorizeRequest request = POLICY_PERMIT_AUTHZ_DATA.toAuthorizeRequest();
            AuthorizeResult  result  = cedarling.authorize(request);
            assertTrue(result.isAllowed());
        }
    }


    
    @Test
    public void authzShouldFailWhenActionIsNotPermitted() throws Exception {

        BootstrapConfiguration bootstrap_config  = getDefaultBootstrapConfiguration();
        try (Cedarling cedarling = new Cedarling(bootstrap_config)) {

            AuthorizeRequest request = POLICY_DENY_AUTHZ_DATA.toAuthorizeRequest();
            AuthorizeResult result = cedarling.authorize(request);
            assertFalse(result.isAllowed());
        }
    }
}
