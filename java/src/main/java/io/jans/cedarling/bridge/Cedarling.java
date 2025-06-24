/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */


package io.jans.cedarling.bridge;

import java.io.Closeable;
import io.jans.cedarling.bridge.config.BootstrapConfiguration;
import io.jans.cedarling.bridge.authz.*;

public class Cedarling implements Closeable {
    
    private static boolean initialized = false;

    private static final String LIBRARY_NAME = "cedarling4j";
    private long cedarlingRef = 0;

    static {

        System.loadLibrary(LIBRARY_NAME);
        initJniCache();
    }

    public Cedarling(final BootstrapConfiguration config) {

        createNativeCedarling(config);
    }

    @Override
    public void close() {
        
        cleanupCedarling();
    }

    private static native void initJniCache();

    public native AuthorizeResult authorize(final AuthorizeRequest request);
    public native AuthorizeResult authorizeUnsigned(final AuthorizeRequestUnsigned request);

    private native void createNativeCedarling(final BootstrapConfiguration config);
    private native void cleanupCedarling();
}