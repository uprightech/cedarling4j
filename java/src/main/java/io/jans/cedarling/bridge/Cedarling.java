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
     *  This is the standard (token-based) interface where the Principal is derived
     *  from a signed JWT.
     * </p>
     * @param request io.jans.cedarling.bridge.authz.AuthorizeRequest the authorization request with signed JWT tokens 
     * @return The authorization result containing details about the success or failure of the operation
     * @throws io.jans.cedarling.bridge.CedarlingError If the authorization request is null or invalid or an error occurs in the Rust bridge
     */
    public native AuthorizeResult authorize(final AuthorizeRequest request) throws CedarlingError;

    /**
     * Evaluates an authorization request with the principal specified as a key value pair (no signed JWT)
     * <p>
     *  This authorization request method , unlike {@link io.jans.cedarling.bridge.Cedarling#authorize} does not
     *  have the requirement of having the principals derived from a signed JWT. 
     * </p>
     * @param request io.jans.cedarling.bridge.authz.AuthorizeRequestUnsigned the unsigned authorization request
     * @return The authorization result containing details about the success or failure of the operation
     * @throws io.jans.cedarling.bridge.CedarlingError If the authorization request is null or invalid or an error occurs in the Rust bridge 
     */
    public native AuthorizeResult authorizeUnsigned(final AuthorizeRequestUnsigned request) throws CedarlingError;

    private native void createNativeCedarling(final BootstrapConfiguration config) throws CedarlingError;
    private native void cleanupCedarling();
}