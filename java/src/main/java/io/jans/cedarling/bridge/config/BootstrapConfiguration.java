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

/**
 * Configuration wrapper for initializing and configuring the Cedarling framework.
 * <p>
 *    This class holds the configuration settings required to bootstrap a Cedarling instance. <br/>
 *    Although it is possible to set each configuration item using setters, it's better to <br/>
 *    rely on the fluent API provided by the {@link io.jans.cedarling.bridge.config.BootstrapConfiguration.Builder Builder}. <br/>
 *    This ensures a valid configuration (from the standpoint of Rust) is passed to Cedarling. <br/>
 *    Each field is be mapped to a single or composite properties in the cedarling properties configuration, <br/>
 *    which can be found <a href="https://docs.jans.io/stable/cedarling/cedarling-properties/">Here</a>. 
 * </p>
 */
public class BootstrapConfiguration {
    
    
    private String applicationName;
    private LogConfiguration logConfiguration;
    private PolicyStoreConfiguration policyStoreConfiguration;
    private JwtConfiguration jwtConfiguration;
    private AuthorizationConfiguration authzConfiguration;
    private EntityBuilderConfiguration entityBuilderConfiguration;
    private LockServiceConfiguration lockConfiguration;

    /**
     * Constructor
     */
    public BootstrapConfiguration() {
        
    }

    /**
     * Gets the application name specified in this bootstrap configuration.
     * <p>>
     *    The application name is a human readable for a Cedarling instance. <br/>
     *    Maps to the {@code CEDARLING_APPLICATION_NAME} property in the cedarling booststrap properties
     * </p>
     * @return the application name for this bootstrap configuration
     */
    public String getApplicationName() {

        return applicationName;
    }

    /**
     * Specifies the application name to use in this bootstrap configuration
     * @param applicationName the application name to be set
     * @return the current instance of the bootstrap configuration
     */
    public BootstrapConfiguration setApplicationName(final String applicationName) {

        this.applicationName = applicationName;
        return this;
    }

    /**
     * Gets the log settings specified in this bootstrap configuration
     * @return the instance's log configuration
     */
    public LogConfiguration getLogConfiguration() {

        return logConfiguration;
    }

    /**
     * Specifies the log settings to be used in this bootstrap configuration
     * @param logConfiguration the log configuration to be set 
     * @return the current instance of the bootstrap configuration 
     */
    public BootstrapConfiguration setLogConfiguration(final LogConfiguration logConfiguration) {

        this.logConfiguration = logConfiguration;
        return this;
    }

    /**
     * Gets the policy store settings in the bootstrap configuration
     * @return the instance's policy store configuration
     */
    public PolicyStoreConfiguration getPolicyStoreConfiguration() {

        return policyStoreConfiguration;
    }

    /**
     * Specifies the policy store settings to be used in this configuration
     * @param policyStoreConfiguration the policy store configuration to set
     * @return the current instance of the bootstrap configuration 
     */
    public BootstrapConfiguration setPolicyStoreConfiguration(final PolicyStoreConfiguration policyStoreConfiguration) {

        this.policyStoreConfiguration = policyStoreConfiguration;
        return this;
    }

    /**
     * Gets the jwt settings to be used in this configuration
     * @return the instance's jwt configuration 
     */
    public JwtConfiguration getJwtConfiguration() {

        return jwtConfiguration;
    }

    /**
     * Specifies the jwt settings to be used in this configuration
     * @param jwtConfiguration the jwt configuration to set
     * @return the current instance of the bootstrap configuration 
     */
    public BootstrapConfiguration setJwtConfiguration(final JwtConfiguration jwtConfiguration) {

        this.jwtConfiguration =  jwtConfiguration;
        return this;
    }

    /**
     * Gets the authorization settings to be used in this configuration
     * @return the instance's authorization configuration 
     */
    public AuthorizationConfiguration getAuthzConfiguration() {

        return authzConfiguration;
    }

    /**
     * Specifies the authorization settings to be used in this configuration
     * @param authzConfiguration the authorization configuration to be set 
     * @return the current instance of the bootstrap configuration
     */
    public BootstrapConfiguration setAuthzConfiguration(AuthorizationConfiguration authzConfiguration) {

        this.authzConfiguration = authzConfiguration;
        return this;
    }

    /**
     * Gets the entity builder settings to be used in this configuration
     * @return the instance's entity builder configuration 
     */
    public EntityBuilderConfiguration getEntityBuilderConfiguration() {

        return entityBuilderConfiguration;
    }

    /**
     * Specifies the entity builder settings to be used in this configuration 
     * @param entityBuilderConfiguration the entity builder configuration to be set
     * @return the current instance of the bootstrap configuration 
     */
    public BootstrapConfiguration setEntityBuilderConfiguration(EntityBuilderConfiguration entityBuilderConfiguration) {

        this.entityBuilderConfiguration = entityBuilderConfiguration;
        return this;
    }

    /**
     * Gets the lock service settings to be used in this configuration
     * @return the instance's lock service configuration
     */
    public LockServiceConfiguration getLockConfiguration() {

        return lockConfiguration;
    }

    /**
     * Specifies the lock service settings to be used for this configuration
     * @param lockConfiguration the lock service's configuration
     * @return the current instance of the bootstrap configuration 
     */
    public BootstrapConfiguration setLockConfiguration(LockServiceConfiguration lockConfiguration) {

        this.lockConfiguration = lockConfiguration;
        return this;
    }

    /**
     * Gets an instance of a BootstrapConfiguration builder 
     * @return a new instance of the builder
     */
    public static Builder builder() {

        return new Builder();
    }
    /**
     * BootstrapConfiguration Builder class 
     * <p>
     *    This class provides a fluent API to create a valid bootstrap configuration 
     *    object.
     * </p>
     */
    public static class Builder {

        private final BootstrapConfiguration config_;

        private Builder()  {

            config_ = new BootstrapConfiguration();
        }

        /**
         * Specifies the application name for the configuration being built
         * @param appname the application name to be set
         * @return the current builder's instance 
         */
        public Builder applicationName(final String appname) {

            config_.applicationName = appname;
            return this;
        }

        /**
         * Specifies the log settings for the configuration being built
         * @param config the log configuration to set
         * @return the current builder's instance
         */
        public Builder logConfiguration(final LogConfiguration config) {

            config_.logConfiguration = config;
            return this;
        }

        /**
         * Specifies the policy store settings for the configuration being built
         * @param config the policy store to set
         * @return the current builder's instance 
         */
        public Builder policyStoreConfiguration(final PolicyStoreConfiguration config) {

            config_.policyStoreConfiguration = config;
            return this;
        }

        /**
         * Specifies the jwt settings for the configuration being built
         * @param config the jwt configuration to set
         * @return the current builder's instance
         */
        public Builder jwtConfiguration(final JwtConfiguration config) {

            config_.jwtConfiguration = config;
            return this;
        }

        /**
         * Specifies the authorization settings for the configuration being built
         * @param config the authorization configuration to set
         * @return the current builder's instance
         */
        public Builder authzConfiguration(final AuthorizationConfiguration config) {

            config_.authzConfiguration = config;
            return this;
        }

        /**
         * Specifies the entity builder settings for the configuration being built
         * @param config the entity builder configuration to set
         * @return the current builder's instance
         */
        public Builder entityBuilderConfiguration(final EntityBuilderConfiguration config) {

            config_.entityBuilderConfiguration = config;
            return this;
        }

        /**
         * Specifies the lock settings for the configuration being built
         * @param config the lock configuration to set
         * @return the current builder's instance 
         */
        public Builder lockConfig(final LockServiceConfiguration config) {

            config_.lockConfiguration = config;
            return this;
        }

        /**
         * Builds an instance of the BoostrapConfiguration
         * <p>
         *  The instance will be created with the configuration data passed to the builder and creation 
         *  will fail if a required configuration item is missing.
         * </p>
         * @return an instance of the cedarling BootstrapConfiguration
         * @throws CedarlingConfigurationError if the bootstrap configuration is in an invalid state
         */
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
