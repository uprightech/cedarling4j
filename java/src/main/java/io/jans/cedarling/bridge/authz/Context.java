/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;
/**
 * Authorization request context wrapper 
 * <p> This class serves as a wrapper around the context data passed with <br/>
 *  Cedarling authorization requests. This is a json string  stored in the <br/>
 *  {@link io.jans.cedarling.bridge.authz.Context#data} field. There are two motivations 
 *  behind the decision for adopting this data model:
 * </p>
 * <ul>
 *  <li>
 *      Wrapping the string in a class guarantees to an extend changes to the underlying native 
 *      representation of the context in rust doesn't affect existing code relying on it too much <br/>
 *  </li>
 *  <li>
 *      Although the string is json, the choice of not going for a direct JSON representation allows flexibility
 *      for library users to choose how they will generate the JSON. 
 *  </li>
 * </ul>
 */
public class Context {
    
    private String data;

    /**
     * Constructor 
     * @param data the context data as a json string
     */
    public Context(String data) {

        this.data = data;
    }

    /**
     * Gets the context data
     * @return the context data as a json string
     */
    public String getData() {

        return data;
    }

    /**
     * Sets the context data 
     * @param data the context data as a json string
     */
    public void setData(String data) {

        this.data = data;
    }
}
