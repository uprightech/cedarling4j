package io.jans.cedarling.bridge.config;

import io.jans.cedarling.bridge.*;
import io.jans.cedarling.bridge.config.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CedarlingBootstrapConfigurationBuilderTest extends CedarlingBaseTest {
    
    private static final File  POLICY_STORE_OK_FILE = new File("policy-stores/policy_store_ok.yaml");
    private static final File  GENERIC_TEST_DATA_FILE  = new File("test-data/generic-test-data.json");
    private static final String GENERIC_APP_NAME = "generic-cedarling-application";

    @Test
    public void shouldReturnValidBootstrapConfiguration () throws Exception {

        assertNotNull(getBaseBootstrapConfigurationBuilder().build());
    }

    @Test
    public void shouldThrowIfApplicationNameIsMissing() throws Exception {

       assertThrows(CedarlingConfigurationError.class,() -> {
        
            getBaseBootstrapConfigurationBuilder().applicationName(null).build();
       });
    }

    @Test
    public void shouldThrowIfLogConfigurationIsMissing() throws Exception {

        assertThrows(CedarlingConfigurationError.class,() -> {
            
            getBaseBootstrapConfigurationBuilder().logConfiguration(null).build();
        });
    }

    @Test
    public void shouldThrowIfPolicyStoreConfigurationIsMissing() throws Exception {
        
        assertThrows(CedarlingConfigurationError.class,() -> {

            getBaseBootstrapConfigurationBuilder().policyStoreConfiguration(null).build();
        });
    }

    @Test
    public void shouldThrowIfJwtConfigurationIsMissing() throws Exception {
        
        assertThrows(CedarlingConfigurationError.class,() -> {

            getBaseBootstrapConfigurationBuilder().jwtConfiguration(null).build();
        });
    }

    @Test
    public void shouldThrowIfAuthorizationConfigurationIsMissing() throws Exception {
        
        assertThrows(CedarlingConfigurationError.class,() -> {

            getBaseBootstrapConfigurationBuilder().authzConfiguration(null).build();
        });  
    }

    @Test
    public void shouldThrowIfEntityBuilderConfiguration() throws Exception {
       
        assertThrows(CedarlingConfigurationError.class,() -> {
            
            getBaseBootstrapConfigurationBuilder().entityBuilderConfiguration(null).build();
        });
    }


   
}
