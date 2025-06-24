/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.util.Optional;

public class MemoryLogConfiguration {

    private long logTtl;
    private Long maxItems;
    private Long maxItemSize;

    public MemoryLogConfiguration() {

        this.logTtl = 0;
        this.maxItems = null;
        this.maxItemSize = null;
    }

    public void setLogTtl(long logTtl) {

        this.logTtl = logTtl;
    }

    public long getLogTtl() {

        return logTtl;
    }

    public Long getMaxItems() {

        return maxItems;
    }

    public void setMaxItems(Long maxItems) {

        this.maxItems = maxItems;
    }

    public Long getMaxItemSize() {

        return maxItemSize;
    }

    public void setMaxItemSize(Long maxItemSize) {

        this.maxItemSize = maxItemSize;
    } 
}
