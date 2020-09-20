/*
 * Copyright (c) 689Cloud LLC, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of 689Cloud,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with 689Cloud.
 */
package com.cloud.spring.boot.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author Quy Duong
 */
@Component
public class ResponseUtil {
    private ObjectMapper mapper;

    @Autowired
    public ResponseUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    private RestAPIResponse _createResponse(APIStatus apiStatus, Object data) {
        return new RestAPIResponse(apiStatus, data);
    }

    private RestAPIResponse _createResponse(APIStatus apiStatus, Object data, String description) {
        return new RestAPIResponse(apiStatus, data, description);
    }

    // base method
    public ResponseEntity<RestAPIResponse> buildResponse(APIStatus apiStatus, Object data, HttpStatus httpStatus) {
        return new ResponseEntity(_createResponse(apiStatus, data), httpStatus);
    }

    // base method
    public ResponseEntity<RestAPIResponse> buildResponse(APIStatus apiStatus, Object data, String description, HttpStatus httpStatus) {
        return new ResponseEntity(_createResponse(apiStatus, data, description), httpStatus);
    }

    public ResponseEntity<RestAPIResponse> successResponse(Object data) {
        return buildResponse(APIStatus.OK, data, HttpStatus.OK);
    }

    public ResponseEntity<RestAPIResponse> successResponse(String description) {
        return buildResponse(APIStatus.OK, "", description, HttpStatus.OK);
    }

    public ResponseEntity<RestAPIResponse> successResponse(Object data, String desciption) {
        return buildResponse(APIStatus.OK, data, desciption,HttpStatus.OK);
    }
    
    public String createJsonCommonResponse(APIStatus apiStatus, Object results) throws JsonProcessingException {
        return mapper.writeValueAsString(_createResponse(apiStatus, results));
    }
    
    public String createJsonSubErrorResponse(APIStatus errStatus, Object results) throws JsonProcessingException {
        return mapper.writeValueAsString(_createResponse(errStatus, results));
    }
}
