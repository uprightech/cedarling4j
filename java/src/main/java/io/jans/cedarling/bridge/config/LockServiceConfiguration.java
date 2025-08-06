/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.net.URI;
import java.time.Duration;

import io.jans.cedarling.bridge.config.LogLevel;

/**
 * Lock service configuration 
 * <p>
 *   The Cedarling can be configured to work with a lock logger, which can be configured
 *   using the settings here.
 * </p>
 */
public class LockServiceConfiguration {

    private LogLevel logLevel;
    private URI configUri;
    private boolean dynamicConfig;
    private String ssaJwt;
    private Duration logInterval;
    private Duration healthInterval;
    private Duration telemetryInterval;
    private boolean listenSse;
    private boolean acceptInvalidCerts;

    /**
     * Default constructor
     */
    public LockServiceConfiguration() {

        acceptInvalidCerts = false;
    }

    /**
     * Specify the log level to use for the lock service
     * @param logLevel
     * @return the log level for the lock service
     */
    public LockServiceConfiguration setLogLevel(LogLevel logLevel) {

        this.logLevel = logLevel;
        return this;
    }

    /**
     * Get the log level to use for the lock service
     * @return
     */
    public LogLevel getLogLevel() {

        return this.logLevel;
    }
    
    /**
     * Specifies the uri cedarling uses to get metadata about the lock server
     * @param configUri
     * @return the current instance of this configuration 
     */
    public LockServiceConfiguration setConfigUri(final URI configUri) {

        this.configUri = configUri;
        return this;
    }

    /**
     * Get the uri cedarling uses to obtain metadata about the lock server
     * @return the uri 
     */
    public URI getConfigUri() {

        return configUri;
    }

    /**
     * Specifies a flag which when {@code true} enables cedarling to listen for SSE config updates
     * @param dynamicConfig the flag which toggles SSE configuration updates
     * @return the current instance of this configuration 
     */
    public LockServiceConfiguration setDynamicConfig(boolean dynamicConfig) {

        this.dynamicConfig = dynamicConfig;
        return this;
    }

    /**
     * Get the flag which toggles SSE configuration updates
     * @return a flag used to toggle SSE configuration update 
     */
    public boolean getDynamicConfig() {

        return dynamicConfig;
    }

    /**
     * Specifies the Software Statement Assertion JWT that cedarling uses for dynamic client registration
     * @param ssaJwt the Software Statement JWT as a json string 
     * @return the current instance of this configuration
     */
    public LockServiceConfiguration setSsaJwt(final String ssaJwt) {

        this.ssaJwt = ssaJwt;
        return this;
    }

    /**
     * Get the Software Statement Assertion JWT that cedarling uses for dynamic client registration
     * @return the Software Statement Assertion JWT 
     */
    public String getSsaJwt() {

        return ssaJwt;
    }

    /**
     * Specifies the interval to send logs to the lock server 
     * @param logInterval the interval specified as a {@code Duration}
     * @return the current instance of this configuration
     */
    public LockServiceConfiguration setLogInterval(final Duration logInterval) {

        this.logInterval = logInterval;
        return this;
    }

    /**
     * Gets the interval to send logs to the lock server
     * @return the log interval 
     */
    public Duration getLogInterval() {

        return logInterval;
    }

    /**
     * Specifies the interval to send health messages to the lock server
     * @param healthInterval the interval specified as a {@code Duration}
     * @return the current instance of this configuration
     */
    public LockServiceConfiguration setHealthInterval(final Duration healthInterval) {

        this.healthInterval = healthInterval;
        return this;
    }

    /**
     * Gets the interval to send health messages to the lock server
     * @return the health interval 
     */
    public Duration getHealthInterval() {

        return this.healthInterval;
    }

    /**
     * Specifies the interval to send telemetry data to the lock server 
     * @param telemetryInterval
     * @return the current instance of this configuration 
     */
    public LockServiceConfiguration setTelemetryInterval(final Duration telemetryInterval) { 

        this.telemetryInterval = telemetryInterval;
        return this;
    }

    /**
     * Get the interval to send telemetry data to the lock server
     * @return the telemetry interval 
     */
    public Duration getTelemetryInterval() {

        return telemetryInterval;
    }

    /**
     * Specifies the value of a flag controlling if cedarling should listen for updates from the lock server
     * @param listenSse
     * @return the current instance of this configuration 
     */
    public LockServiceConfiguration setListenSse(final boolean listenSse) {

        this.listenSse = listenSse;
        return this;
    }

    /**
     * Gets the value of the flag controlling if cedarling should listen for updates from the lock server
     * @return the flag controlling if cedarling should listen for updates from the lock server 
     */
    public boolean getListenSse() {

        return listenSse;
    }

    /**
     * Specifies a flag that toggles acceptance of invalid certificates from the lock server.
     * <p>This should be used for testing purposes only, and the default value is {@code false}</p>
     * @param acceptInvalidCerts
     * @return the current instance of this configuration
     */
    public LockServiceConfiguration setAcceptInvalidCerts(final boolean acceptInvalidCerts) {

        this.acceptInvalidCerts = acceptInvalidCerts;
        return this;
    }

    /**
     * Gets the value of a flag that toggles acceptance of invalid certificates from the lock server.
     * @return the value of the flag
     */
    public boolean getAcceptInvalidCerts() {

        return acceptInvalidCerts;
    }
}