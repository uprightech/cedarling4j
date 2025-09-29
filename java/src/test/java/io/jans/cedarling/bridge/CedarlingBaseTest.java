/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge;

import io.jans.cedarling.bridge.config.*;
import io.jans.cedarling.bridge.util.FileUtil;
import io.jans.cedarling.bridge.config.AuthorizationConfiguration;
import io.jans.cedarling.bridge.config.BootstrapConfiguration;
import io.jans.cedarling.bridge.config.CedarlingConfigurationError;
import io.jans.cedarling.bridge.config.EntityBuilderConfiguration;
import io.jans.cedarling.bridge.config.JwtConfiguration;
import io.jans.cedarling.bridge.config.LogLevel;
import io.jans.cedarling.bridge.config.LogType;
import io.jans.cedarling.bridge.config.PolicyStoreConfiguration;

import java.io.File;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.BeforeAll;

public class CedarlingBaseTest {
    
    @TempDir
    protected static File TEST_FILES_DIR = null;

    private static final   String BASE_APPLICATION_NAME = "test-application";
    protected static final String POLICY_STORES_DIR_NAME = "policy-stores";
    protected static final String AUTHZ_DATA_DIR_NAME = "authz-data";

    protected static final String GENERIC_TEST_POLICY_STORE_YAML_FILE = "policy-stores/generic-policy-store.yaml";
    protected static final String POLICY_STORE_OK_YAML_FILE = "policy-stores/policy-store_ok.yaml";
    protected static final String POLICY_STORE_OK_JSON_FILE = "policy-stores/policy-store_ok.json";

    protected static String POLICY_STORE_OK_YAML_DATA = null;
    protected static String POLICY_STORE_OK_JSON_DATA = null;

    protected static final String DEFAULT_JANS_WORKLOAD_ALLOW_RULE = "{ \"===\" : [ {\"var\": \"Jans::Workload\"},\"ALLOW\" ] }";
    protected static final String DEFAULT_JANS_USER_ALLOW_RULE = "{ \"===\" : [ {\"var\": \"Jans::User\"},\"ALLOW\" ] }";
    protected static final String DEFAULT_JSON_RULE = "{ \"and\": [" + DEFAULT_JANS_USER_ALLOW_RULE + "," + DEFAULT_JANS_WORKLOAD_ALLOW_RULE + "]}";

    @BeforeAll
    public static void copyBaseTestFiles() throws Exception {
        
        new File(TEST_FILES_DIR,POLICY_STORES_DIR_NAME).mkdir();
        new File(TEST_FILES_DIR,AUTHZ_DATA_DIR_NAME).mkdir();
        FileUtil.copyResource(TEST_FILES_DIR,GENERIC_TEST_POLICY_STORE_YAML_FILE);
        FileUtil.copyResource(TEST_FILES_DIR,POLICY_STORE_OK_JSON_FILE);
        FileUtil.copyResource(TEST_FILES_DIR,POLICY_STORE_OK_YAML_FILE);
        POLICY_STORE_OK_YAML_DATA = FileUtil.readResourceContent(POLICY_STORE_OK_YAML_FILE);
        POLICY_STORE_OK_JSON_DATA = FileUtil.readResourceContent(POLICY_STORE_OK_JSON_FILE);
    }

    protected BootstrapConfiguration.Builder getBaseBootstrapConfigurationBuilder() throws CedarlingConfigurationError {

        return BootstrapConfiguration.builder()
            .applicationName(getBaseApplicationName())
            .logConfiguration(getBaseLogConfiguration())
            .policyStoreConfiguration(getBasePolicyStoreConfiguration())
            .jwtConfiguration(getBaseJwtConfiguration())
            .authzConfiguration(getBaseAuthzConfiguration())
            .entityBuilderConfiguration(getBaseEntityBuilderConfiguration());
    }

    protected String getBaseApplicationName() {

        return BASE_APPLICATION_NAME;
    }

    protected LogConfiguration getBaseLogConfiguration() {

        LogConfiguration logconfig = new LogConfiguration(LogType.STDOUT,LogLevel.INFO);
        return logconfig;
    }

    protected PolicyStoreConfiguration getBasePolicyStoreConfiguration() {
        
        final File policy_store_file = new File(TEST_FILES_DIR,GENERIC_TEST_POLICY_STORE_YAML_FILE);
        return PolicyStoreConfiguration.fromYamlFile(policy_store_file);
    }

    protected JwtConfiguration getBaseJwtConfiguration() {

        JwtConfiguration jwtconfig = new JwtConfiguration();
        jwtconfig.setJwtCheckSignValidation(false)
            .setJwtCheckStatusValidation(false)
            .allowAllAlgorithms();
        
        return jwtconfig;
    }

    protected AuthorizationConfiguration getBaseAuthzConfiguration() {

        AuthorizationConfiguration authzconfig = new AuthorizationConfiguration();
        authzconfig.setUseUserPrincipal(true)
            .setUseWorkloadPrincipal(true)
            .setDecisionLogDefaultJwtId(AuthorizationConfiguration.DEFAULT_DECISION_LOG_JWT_ID)
            .setIdTokenTrustMode(IdTokenTrustMode.NEVER)
            .setPrincipalBoolOperator(new JsonRule(DEFAULT_JSON_RULE));
        return authzconfig;
    }

    protected EntityBuilderConfiguration getBaseEntityBuilderConfiguration() {

        EntityBuilderConfiguration ebconfig = new EntityBuilderConfiguration();
        ebconfig.buildWorkload().buildUser();
        return ebconfig;
    }

}
