/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

import java.io.File;

import io.jans.cedarling.bridge.config.PolicyStoreSource;
import io.jans.cedarling.bridge.util.Constraints;

/**
 * Policy store configuration
 * <p>
 *   Configure the cedarling policy store, mostly it's source which can be 
 *   either json or yaml provided in either a file or raw string data
 * </p>
 */
public class PolicyStoreConfiguration {
    
    private final PolicyStoreSource source;
    private final String data;
    private final File dataPath;

    /**
     * Constructor
     * <p>
     *   This constructor should be used to create a policy store configuration 
     *   with the source being string data
     * </p>
     * @param source
     * @param data
     */
    public PolicyStoreConfiguration(final PolicyStoreSource source, final String data) {

        this.source = source; 
        this.data = data;
        this.dataPath = null;
    }

    /**
     * Constructor
     * <p>
     *   This constructor should be used to create a policy store configuration 
     *   with the source being a file
     * </p>
     * @param source
     * @param dataPath
     */
    public PolicyStoreConfiguration(final PolicyStoreSource source, final File dataPath) {

        this.source = source;
        this.data = null;
        this.dataPath = dataPath;
    }

    /**
     * Get the source of the policy store data in this configuration
     * @return the source of the policy store data
     */
    public PolicyStoreSource getSource() {

        return source;
    }

    /**
     * Get the string containing policy store data
     * @return the policy store data as a string 
     */
    public String getData() {

        return data;
    }

    /**
     * Get the path to a file containing the policy store data
     * @return
     */
    public File getDataPath() {

        return dataPath;
    }

    /**
     * Creates a policy store configuration from a json string
     * @param json the policy store data in json format
     * @return an instance of the policy store configuration
     */
    public static PolicyStoreConfiguration fromJsonString(final String json) {

        Constraints.ensureNotNull(json,"Json data cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.JSON,json);
    }

    /**
     * Creates a policy store configuration from a yaml string
     * @param yaml the policy store data in yaml format
     * @return an instance of the policy store configuration
     */
    public static PolicyStoreConfiguration fromYamlString(final String yaml) {

        Constraints.ensureNotNull(yaml,"Yaml data cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.YAML,yaml);
    }

    /**
     * Creates a policy store configuration from a json file
     * @param jsonfile the path to the file containing policy store data in json
     * @return an instance of the policy store configuration 
     */
    public static PolicyStoreConfiguration fromJsonFile(final File jsonfile) {

        Constraints.ensureNotNull(jsonfile,"Json file cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.FILEJSON, jsonfile);
    }

    /**
     * Creates a policy store configuration from a yaml file
     * @param yamlfile
     * @return an instance of the policy store configuration
     */
    public static PolicyStoreConfiguration fromYamlFile(final File yamlfile) {

        Constraints.ensureNotNull(yamlfile, "Yaml file cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.FILEYAML, yamlfile);
    }

    /**
     * Creates a policy store configuration from Jans lock using a Lockmaster store id
     * @param storeid the store id of the policy store in jans lock 
     * @return an instance of the policy store configuration
     */
    public static PolicyStoreConfiguration fromLockmasterStoreId(final String storeid) {
        
        Constraints.ensureNotNull(storeid,"Lockmaster policy storeid cannot be null");
        return new PolicyStoreConfiguration(PolicyStoreSource.LOCKMASTER,storeid);
    }
}
