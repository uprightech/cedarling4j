/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Wrapper around a <a href="https://jsonlogic.com/">JsonLogic</a> rule specified as a string
 * <p>
 *    The main use for this class is in the {@link io.jans.cedarling.bridge.config.AuthorizationConfiguration}
 *    where it will be eventually used by the cedarling engine to determine which principals are used during
 *    authorization evaluation. An example of JsonRule could be
 * </p>
 * <pre>
 * {@code 
 *   {
 *       "and" : [
 *          {
 *            "===" : [{"var": "Jans::Workload"},"ALLOW"]
 *          },
 *          {
 *            "===" : [{"var": "Jans::User"},"ALLOW"]
 *          }
 *       ]
 *   }
 * }
 * </pre>
 */
public class JsonRule {

    private String value;

    /**
     * Constructor. 
     * @param value the jsonlogic rule as a {@code String}
     */
    public JsonRule(final String value) {

        this.value = value;
    }

    /**
     * Gets the stored jsonlogic rule as a {@code String}
     * @return
     */
    public String getValue() {

        return value;
    }

    /**
     * Specifies the jsonlogic rule as a {@code String}
     * @param value
     */
    public void setValue(final String value) {

        this.value = value;
    }
}
