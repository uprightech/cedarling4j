/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.io.File;

import io.jans.cedarling.bridge.config.PolicyStoreSource;
import io.jans.cedarling.bridge.util.Constraints;

public class PolicyStoreConfiguration {
    
    private final PolicyStoreSource source;
    private final String data;
    private final File dataPath;

    public PolicyStoreConfiguration(final PolicyStoreSource source, final String data) {

        this.source = source; 
        this.data = data;
        this.dataPath = null;
    }

    public PolicyStoreConfiguration(final PolicyStoreSource source, final File dataPath) {

        this.source = source;
        this.data = null;
        this.dataPath = dataPath;
    }

    public PolicyStoreSource getSource() {

        return source;
    }

    public String getData() {

        return data;
    }

    public File getDataPath() {

        return dataPath;
    }

    public static PolicyStoreConfiguration fromJsonString(final String json) {

        Constraints.ensureNotNull(json,"Json data cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.JSON,json);
    }

    public static PolicyStoreConfiguration fromYamlString(final String yaml) {

        Constraints.ensureNotNull(yaml,"Yaml data cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.YAML,yaml);
    }

    public static PolicyStoreConfiguration fromJsonFile(final File jsonfile) {

        Constraints.ensureNotNull(jsonfile,"Json file cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.FILEJSON, jsonfile);
    }

    public static PolicyStoreConfiguration fromYamlFile(final File yamlfile) {

        Constraints.ensureNotNull(yamlfile, "Yaml file cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.FILEYAML, yamlfile);
    }

    public static PolicyStoreConfiguration fromLockmasterStoreId(final String storeid) {

        Constraints.ensureNotNull(storeid,"Lockmaster policy storeid cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.LOCKMASTER,storeid);
    }
}
