package io.jans.cedarling.bridge;

import io.jans.cedarling.bridge.CedarlingBaseTest;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

public class CedarlingUnsignedAuthorizationTest extends CedarlingBaseTest {
    

    @BeforeAll
    public static final void setupTestResources() throws Exception {


    }

    private BootstrapConfiguration getDefaultBootstrapConfiguration() throws Exception  {

        EntityBuilderConfiguration eb_config = getBaseEntityBuilderConfiguration();
        AuthorizationConfiguration authz_config = getBaseAuthzConfiguration();
        final File policy_store_file = new File(TEST_FILES_DIR,POLICY_STORE_OK_YAML_FILE);

        authz_config.addDecisionLogUserClaim("client_id");
        authz_config.addDecisionLogUserClaim("org_id");
        authz_config.addDecisionLogWorkloadClaims("org_id");
        authz_config.setPrincipalBoolOperator(new JsonRule(DEFAULT_JANS_USER_ALLOW_RULE));

        return getBaseBootstrapConfigurationBuilder()
            .policyStoreConfiguration(PolicyStoreConfiguration.fromYamlFile(policy_store_file))
            .authzConfiguration(authz_config)
            .entityBuilderConfiguration( getBaseEntityBuilderConfiguration())
            .build();

    }

    @Test
    public void authzShouldSucceedWhenActionIsPermitted() throws Exception {

        BootstrapConfiguration bootstrap_config = getDefaultBootstrapConfiguration();

        try(Cedarling cedarling = new Cedarling(bootstrap_config)) {

            
            JSONObject principal_attrs = new JSONObject();
            principal_attrs.put("sub", "some_sub");
            principal_attrs.put("email","email@email.com");
            principal_attrs.put("username","some_username");
            principal_attrs.put("country","US");
            principal_attrs.put("role","SuperUser");

            EntityData principal = new EntityData(new CedarEntityMapping("some_user", "Jans::User"),principal_attrs.toString());

            JSONObject resource_attrs = new JSONObject();
            resource_attrs.put("org_id","some_long_id");
            resource_attrs.put("country","US");
            EntityData resource = new EntityData(new CedarEntityMapping("random_id", "Jans::Issue"),resource_attrs.toString());
    
            AuthorizeRequestUnsigned request = new AuthorizeRequestUnsigned();
            request.addPrincipal(principal);
            request.setAction("Jans::Action::\"Update\"");
            request.setContext(new Context("{}"));
            request.setResource(resource);
            AuthorizeResult result = cedarling.authorizeUnsigned(request);
            assertTrue(result.isAllowed());
        }
    }


    
    @Test
    public void authzShouldFailWhenActionIsNotPermitted() throws Exception {

        BootstrapConfiguration bootstrap_config  = getDefaultBootstrapConfiguration();
        try (Cedarling cedarling = new Cedarling(bootstrap_config)) {

            JSONObject principal_attrs = new JSONObject();
            principal_attrs.put("sub", "some_sub");
            principal_attrs.put("email","email@email.com");
            principal_attrs.put("username","some_username");
            principal_attrs.put("country","US");
            principal_attrs.put("role","HyperUser");

            EntityData principal = new EntityData(new CedarEntityMapping("some_user", "Jans::User"),principal_attrs.toString());

            JSONObject resource_attrs = new JSONObject();
            resource_attrs.put("org_id","some_long_id");
            resource_attrs.put("country","US");
            EntityData resource = new EntityData(new CedarEntityMapping("random_id", "Jans::Issue"),resource_attrs.toString());

            AuthorizeRequestUnsigned request = new AuthorizeRequestUnsigned();
            request.addPrincipal(principal);
            request.setAction("Jans::Action::\"Update\"");
            request.setContext(new Context("{}"));
            request.setResource(resource);
            AuthorizeResult result = cedarling.authorizeUnsigned(request);
            assertFalse(result.isAllowed());
        }
    }
}
