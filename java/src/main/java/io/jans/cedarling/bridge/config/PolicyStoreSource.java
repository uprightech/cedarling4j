/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Enum specifying the source of the policy store
 */
public enum PolicyStoreSource {
    /**
     * The policy store is a json string
     */
    JSON,
    /**
     * The policy store is a yaml string
     */
    YAML,
    /**
     * The policy store is from jans lock
     */
    LOCKMASTER,
    /**
     * The policy store is a json file
     */
    FILEJSON,
    /**
     * The policy store is yaml file
     */
    FILEYAML
}
