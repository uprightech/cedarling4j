/*
 * Janssen Project software is available under the Apache License (2004). See http://www.apache.org/licenses/ for full text.
 *
 * Copyright (c) 2025, Janssen Project
 */

package io.jans.cedarling.bridge.config;

/**
 * Enum specifying the levels of validation for the id token
 * <p>
 *   There are currently two modes supported:
 * </p>
 * <ul>
 *  <li><b>Strict</b>. It requires the following:
 *     <ul> 
 *        <li>The {@code aud} attribute in the idtoken to match the {@code client_id} in the acess_token</li>
 *        <li>
 *            If a userinfo token is present , the {@code sub} attribute matches the id_token {@code sub} attribute and the
 *            {@code aud} attribute matches the access_token {@code client_id} attribute.
 *        </li>
 *     </ul>
 *  </li>
 *  <li>
 *      <b>None</b>. No validation is applied 
 *  </li>
 * </ul>
 */
public enum IdTokenTrustMode {
    NONE,
    STRICT
}
