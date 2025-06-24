/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */


package io.jans.cedarling.bridge.util;

public class Constraints {
    
    public static void ensureNotNull(final Object obj,final String message) {

        if(obj == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
