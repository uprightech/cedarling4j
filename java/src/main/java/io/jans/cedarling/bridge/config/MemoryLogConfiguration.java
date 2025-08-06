/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.util.Optional;

/**
 * Memory logging (sub-) configuration for cedarling 
 * <p>This configuration is only valid when memory logging is selected for cedarling</p>
 */
public class MemoryLogConfiguration {

    private long logTtl;
    private Long maxItems;
    private Long maxItemSize;

    /**
     * Default constructor
     */
    public MemoryLogConfiguration() {

        this.logTtl = 0;
        this.maxItems = null;
        this.maxItemSize = null;
    }

    /**
     * Specifies how long in milliseconds to keep a log entry in memory
     * @param logTtl the log time to live in milliseconds
     */
    public void setLogTtl(long logTtl) {

        this.logTtl = logTtl;
    }

    /**
     * Gets the time-to-live of log entries in milliseconds
     * @return the time-to-live in milliseconds
     */
    public long getLogTtl() {

        return logTtl;
    }

    /**
     * Gets the maximum number of log entries to hold in memory at a time
     * @return the maximum number of log entries to hold in memory
     */
    public Long getMaxItems() {

        return maxItems;
    }

    /**
     * Specifies the maximum log entries to hold in memory at a time
     * @param maxItems
     */
    public void setMaxItems(Long maxItems) {

        this.maxItems = maxItems;
    }

    /**
     * Gets the maximum size of a log entry in bytes
     * @return the maximum size of a log entry in bytes
     */
    public Long getMaxItemSize() {

        return maxItemSize;
    }

    /**
     * Sets the maximum size of a log entry in bytes
     * @param maxItemSize the maximum size of a log entry in bytes
     */
    public void setMaxItemSize(Long maxItemSize) {

        this.maxItemSize = maxItemSize;
    } 
}
