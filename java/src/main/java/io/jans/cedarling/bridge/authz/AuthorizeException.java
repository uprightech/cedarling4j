/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.authz;

public class AuthorizeException extends RuntimeException {
    
    private AuthorizeErrorType type;

    public AuthorizeException(final AuthorizeErrorType type, final String msg) {
        super(msg);
        this.type = type;
    }
    
    public AuthorizeException(final AuthorizeErrorType type, final String msg, Throwable cause) {
        super(msg,cause);
        this.type = type;
    }
}
