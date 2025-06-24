/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.net.URI;
import java.time.Duration;

import io.jans.cedarling.bridge.config.LogLevel;

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

    public LockServiceConfiguration() {

        acceptInvalidCerts = false;
    }

    public LockServiceConfiguration setLogLevel(LogLevel logLevel) {

        this.logLevel = logLevel;
        return this;
    }

    public LogLevel getLogLevel() {

        return this.logLevel;
    }

    public LockServiceConfiguration setConfigUri(final URI configUri) {

        this.configUri = configUri;
        return this;
    }

    public URI getConfigUri() {

        return configUri;
    }

    public LockServiceConfiguration setDynamicConfig(boolean dynamicConfig) {

        this.dynamicConfig = dynamicConfig;
        return this;
    }

    public boolean getDynamicConfig() {

        return dynamicConfig;
    }

    public LockServiceConfiguration setSsaJwt(final String ssaJwt) {

        this.ssaJwt = ssaJwt;
        return this;
    }

    public String getSsaJwt() {

        return ssaJwt;
    }

    public LockServiceConfiguration setLogInterval(final Duration logInterval) {

        this.logInterval = logInterval;
        return this;
    }

    public Duration getLogInterval() {

        return logInterval;
    }

    public LockServiceConfiguration setHealthInterval(final Duration healthInterval) {

        this.healthInterval = healthInterval;
        return this;
    }

    public Duration getHealthInterval() {

        return this.healthInterval;
    }

    public LockServiceConfiguration setTelemetryInterval(final Duration telemetryInterval) { 

        this.telemetryInterval = telemetryInterval;
        return this;
    }

    public Duration getTelemetryInterval() {

        return telemetryInterval;
    }

    public LockServiceConfiguration setListenSse(final boolean listenSse) {

        this.listenSse = listenSse;
        return this;
    }

    public boolean getListenSse() {

        return listenSse;
    }

    public LockServiceConfiguration setAcceptInvalidCerts(final boolean acceptInvalidCerts) {

        this.acceptInvalidCerts = acceptInvalidCerts;
        return this;
    }

    public boolean getAcceptInvalidCerts() {

        return acceptInvalidCerts;
    }
}