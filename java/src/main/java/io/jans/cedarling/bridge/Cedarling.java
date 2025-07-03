/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */


package io.jans.cedarling.bridge;

import java.io.Closeable;
import io.jans.cedarling.bridge.config.BootstrapConfiguration;
import io.jans.cedarling.bridge.authz.*;

/**
 * Encapsulates the Cedarling engine and marshalls data between the Java and the Rust components of the library
 *  <p>
 *      This is the heart of the library. 
 *      <br/>Implicitly , this class loads the rust library by calling {@link java.lang.System#loadLibrary(String)}
 * </p>
 */
public class Cedarling implements Closeable {
    
    private static boolean initialized = false;

    private static final String LIBRARY_NAME = "cedarling4j";
    private long cedarlingRef = 0;

    static {

        System.loadLibrary(LIBRARY_NAME);
        initJniCache();
    }

    /**
     * Constructs a new {@code Cedarling} instance with the specified configuration 
     * @param config the {@code BootstrapConfiguration used to initialize the cedarling instance}
     * @throws io.jans.cedarling.bridge.CedarlingError if the provided configuration is null or invalid
     */
    public Cedarling(final BootstrapConfiguration config) throws CedarlingError {

        createNativeCedarling(config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        
        cleanupCedarling();
    }

    private static native void initJniCache();

    /**
     * Evaluates an authorization request 
     * <p>
     *  This is the standard (token-based) interface where the Principal is extracted
     *  from a JWT.
     * </p>
     * @param request
     * @return
     */
    public native AuthorizeResult authorize(final AuthorizeRequest request) throws CedarlingError;
    public native AuthorizeResult authorizeUnsigned(final AuthorizeRequestUnsigned request) throws CedarlingError;

    private native void createNativeCedarling(final BootstrapConfiguration config) throws CedarlingError;
    private native void cleanupCedarling();
}