/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import io.jans.cedarling.bridge.config.AuthorizationConfiguration;
import io.jans.cedarling.bridge.config.CedarlingConfigurationError;
import io.jans.cedarling.bridge.config.EntityBuilderConfiguration;
import io.jans.cedarling.bridge.config.LockServiceConfiguration;
import io.jans.cedarling.bridge.config.LogConfiguration;
import io.jans.cedarling.bridge.util.Constraints;

public class BootstrapConfiguration {
    
    private String applicationName;
    private LogConfiguration logConfiguration;
    private PolicyStoreConfiguration policyStoreConfiguration;
    private JwtConfiguration jwtConfiguration;
    private AuthorizationConfiguration authzConfiguration;
    private EntityBuilderConfiguration entityBuilderConfiguration;
    private LockServiceConfiguration lockConfiguration;

    private BootstrapConfiguration() {
        
    }

    public String getApplicationName() {

        return applicationName;
    }

    public BootstrapConfiguration setApplicationName(final String applicationName) {

        this.applicationName =applicationName;
        return this;
    }

    public LogConfiguration getLogConfiguration() {

        return logConfiguration;
    }

    public BootstrapConfiguration setLogConfiguration(final LogConfiguration logConfiguration) {

        this.logConfiguration = logConfiguration;
        return this;
    }

    public PolicyStoreConfiguration getPolicyStoreConfiguration() {

        return policyStoreConfiguration;
    }

    public BootstrapConfiguration setPolicyStoreConfiguration(final PolicyStoreConfiguration policyStoreConfiguration) {

        this.policyStoreConfiguration = policyStoreConfiguration;
        return this;
    }

    public JwtConfiguration getJwtConfiguration() {

        return jwtConfiguration;
    }

    public BootstrapConfiguration setJwtConfiguration(final JwtConfiguration jwtConfiguration) {

        this.jwtConfiguration =  jwtConfiguration;
        return this;
    }

    public AuthorizationConfiguration getAuthzConfiguration() {

        return authzConfiguration;
    }

    public BootstrapConfiguration setAuthzConfiguration(AuthorizationConfiguration authzConfiguration) {

        this.authzConfiguration = authzConfiguration;
        return this;
    }

    public EntityBuilderConfiguration getEntityBuilderConfiguration() {

        return entityBuilderConfiguration;
    }

    public BootstrapConfiguration setEntityBuilderConfiguration(EntityBuilderConfiguration entityBuilderConfiguration) {

        this.entityBuilderConfiguration = entityBuilderConfiguration;
        return this;
    }

    public LockServiceConfiguration getLockConfiguration() {

        return lockConfiguration;
    }

    public BootstrapConfiguration setLockConfiguration(LockServiceConfiguration lockConfiguration) {

        this.lockConfiguration = lockConfiguration;
        return this;
    }

    public static Builder builder() {

        return new Builder();
    }
    
    public static class Builder {

        private final BootstrapConfiguration config_;

        private Builder()  {

            config_ = new BootstrapConfiguration();
        }

        public Builder applicationName(final String appname) {

            config_.applicationName = appname;
            return this;
        }

        public Builder logConfiguration(final LogConfiguration config) {

            config_.logConfiguration = config;
            return this;
        }

        public Builder policyStoreConfiguration(final PolicyStoreConfiguration config) {

            config_.policyStoreConfiguration = config;
            return this;
        }

        public Builder jwtConfiguration(final JwtConfiguration config) {

            config_.jwtConfiguration = config;
            return this;
        }

        public Builder authzConfiguration(final AuthorizationConfiguration config) {

            config_.authzConfiguration = config;
            return this;
        }

        public Builder entityBuilderConfiguration(final EntityBuilderConfiguration config) {

            config_.entityBuilderConfiguration = config;
            return this;
        }

        public Builder lockConfig(final LockServiceConfiguration config) {

            config_.lockConfiguration = config;
            return this;
        }

        public BootstrapConfiguration build() throws CedarlingConfigurationError {

            try {
                Constraints.ensureNotNull(config_.applicationName,"Application name cannot be null");
                Constraints.ensureNotNull(config_.logConfiguration,"Log configuration cannot be null");
                Constraints.ensureNotNull(config_.policyStoreConfiguration,"Policy store configuration cannot be null");
                Constraints.ensureNotNull(config_.jwtConfiguration,"Jwt configuration cannot be null");
                Constraints.ensureNotNull(config_.authzConfiguration,"Authz configuration cannot be null");
                Constraints.ensureNotNull(config_.entityBuilderConfiguration,"Entity builder configuration cannot be null");
                return config_;
            }catch(IllegalArgumentException e) {
                throw new CedarlingConfigurationError("Bootstrap configuration build failed "+e.getMessage(),e);
            }
        }
    }
}
