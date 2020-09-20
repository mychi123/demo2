/*
 * Copyright (c) HOGO, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of HOGO,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HOGO.
 */
package com.cloud.spring.boot.common.exception;

import com.cloud.spring.boot.common.util.APIStatus;
import com.cloud.spring.boot.common.util.ResponseUtil;
import com.cloud.spring.boot.common.util.RestAPIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Application exception handler
 *
 */
@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseUtil responseUtil;

    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<RestAPIResponse> handleApplicationException(ApplicationException ex, WebRequest request) {

        LOGGER.debug("handleApplicationException", ex);

        ResponseEntity<RestAPIResponse> response;
        if (ex.getApiStatus() == APIStatus.ERR_BAD_REQUEST) {
            // handle bad request
            response = responseUtil.buildResponse(APIStatus.ERR_BAD_REQUEST, ex.getMessage(), HttpStatus.MULTI_STATUS);
        } else {
            response = responseUtil.buildResponse(ex.getApiStatus(), ex.getMessage(), HttpStatus.OK);
        }

        return response;
    }

    // when missing parameter
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        return new ResponseEntity<Object>(new RestAPIResponse(APIStatus.ERR_BAD_PARAMS, ex.getMessage()), headers, status);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestAPIResponse> handleUncatchException(Exception ex, WebRequest request) {

        LOGGER.error("handleUncatchException", ex);
        return responseUtil.buildResponse(APIStatus.ERR_INTERNAL_SERVER, "Please contact System SysAdmin to resolve problem", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
