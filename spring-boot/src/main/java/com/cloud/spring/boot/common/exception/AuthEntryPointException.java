/*
 * Copyright (c) 689Cloud LLC, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of 689Cloud,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with 689Cloud.
 */
package com.cloud.spring.boot.common.exception;

import com.cloud.spring.boot.common.util.APIStatus;
import com.cloud.spring.boot.common.util.RestAPIResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author Quy Duong
 */

@Component
public class AuthEntryPointException implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException { 

        // This is invoked when user tries to access a secured REST resource without supplying any credentials
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new RestAPIResponse(APIStatus.ERR_UNAUTHORIZED, null)));
    }
    
}
