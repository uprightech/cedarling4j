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
import io.jans.cedarling.bridge.config.PolicyStoreConfiguration;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


public class CedarlingInstantiationTest extends CedarlingBaseTest {

    
    private static final String MISSING_POLICY_STORE_YAML_FILE_NAME = "policy-stores/missing-policy-store.yaml";
    
    @Test
    public void shouldSucceedWhenAllConfigurationPropertiesAreValid() throws Exception {

         BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
         try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {
            
         }
    }

    @Test
    public void shouldThrowExceptionWhenApplicationNameIsMissing() throws Exception {

        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setApplicationName(null);
        assertThrows(CedarlingConfigurationError.class,() -> {
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    @Test
    public void shouldThrowExceptionWhenLogConfigurationIsMissing() throws Exception {

        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setLogConfiguration(null);
        assertThrows(CedarlingConfigurationError.class,() -> {
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    @Test
    public void shouldThrowExceptionWhenPolicyStoreConfigurationIsMissing() throws Exception {
        
        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setPolicyStoreConfiguration(null);
        assertThrows(CedarlingConfigurationError.class,() -> {
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    @Test
    public void shouldThrowExceptionWhenJwtConfigurationIsMissing() throws Exception {
        
        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setJwtConfiguration(null);
        assertThrows(CedarlingConfigurationError.class,() -> {
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    @Test
    public void shouldThrowExceptionWhenAuthzConfigurationIsMissing() throws Exception {
        
        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setAuthzConfiguration(null);
        assertThrows(CedarlingConfigurationError.class,() -> {
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

     @Test
    public void shouldThrowExceptionWhenEntityBuilderConfigurationIsMissing() throws Exception {
        
        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setEntityBuilderConfiguration(null);
        assertThrows(CedarlingConfigurationError.class,() -> {
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    @Test
    public void shouldThrowExceptionWhenMemoryLogConfigurationIsInvalid() throws Exception {

        
        assertThrows(CedarlingConfigurationError.class,()-> {
            BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
            bootstrapconfig.setLogConfiguration(new LogConfiguration(LogType.MEMORY,LogLevel.INFO));
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }


    @Test
    public void shouldSucceedWhenPolicyStoreIsValidYamlString() throws Exception {
        
        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setPolicyStoreConfiguration(PolicyStoreConfiguration.fromYamlString(POLICY_STORE_OK_YAML_DATA));
        try (Cedarling cedarling = new Cedarling (bootstrapconfig)) {

        }
    }

    @Test
    public void shouldSucceedWhenPolicyStoreIsValidJsonString() throws Exception {

        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setPolicyStoreConfiguration(PolicyStoreConfiguration.fromJsonString(POLICY_STORE_OK_JSON_DATA));
        try(Cedarling cedarling = new Cedarling(bootstrapconfig)) {

        }
    }

    @Test
    public void shouldFailWhenPolicyStoreIsEmptyJsonString() throws Exception {

        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setPolicyStoreConfiguration(PolicyStoreConfiguration.fromJsonString("{}"));
        assertThrows(CedarlingConfigurationError.class,()-> {

            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    @Test
    public void shouldFailWhenPolicyStoreIsEmptyYamlString() throws Exception {

        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        bootstrapconfig.setPolicyStoreConfiguration(PolicyStoreConfiguration.fromYamlString(""));
        assertThrows(CedarlingConfigurationError.class,() -> {
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    @Test
    public void shouldFailWhenMissingPolicyStoreFileIsSpecified() throws Exception {

        BootstrapConfiguration bootstrapconfig = getBaseBootstrapConfigurationBuilder().build();
        assertThrows(CedarlingConfigurationError.class,()-> {
            File missing_yaml_policy_store = new File(TEST_FILES_DIR,MISSING_POLICY_STORE_YAML_FILE_NAME);
            bootstrapconfig.setPolicyStoreConfiguration(PolicyStoreConfiguration.fromYamlFile(missing_yaml_policy_store));
            try (Cedarling cedarling = new Cedarling(bootstrapconfig)) {

            }
        });
    }

    
}
