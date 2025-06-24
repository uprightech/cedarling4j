/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

public class Context {
    
    private String data;

    public Context(String data) {

        this.data = data;
    }

    public String getData() {

        return data;
    }

    public void setData(String data) {

        this.data = data;
    }
}
