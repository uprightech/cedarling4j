/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Log type or log destination 
 */
public enum LogType {
    /**
     * No logging 
     */
    OFF,
    /**
     * Logs to memory
     */
    MEMORY,
    /**
     * Logs to stdout
     */
    STDOUT,
    /**
     * Logs to the Jans Lock Server
     */
    LOCK
}
