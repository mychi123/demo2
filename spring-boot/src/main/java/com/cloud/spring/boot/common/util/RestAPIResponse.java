/*
 * Copyright (c) 689Cloud LLC, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of 689Cloud,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with 689Cloud.
 */
package com.cloud.spring.boot.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * 
 * @author Quy Duong
 * @param <T> 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestAPIResponse<T extends Object> implements Serializable {

    /**
     * status & message fields have not setter. They are assigned value when
     * initial by APIStatus parameter
     */
    private int status;
    private String message;
    private T data;
    private String description;

    public RestAPIResponse(APIStatus apiStatus, T data) {

        if (apiStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }

        this.status = apiStatus.getCode();
        this.message = apiStatus.getDescription();
        this.data = data;
        this.description = "";
    }

    public RestAPIResponse(APIStatus apiStatus, T data, String description) {

        if (apiStatus == null) {
            throw new IllegalArgumentException("APIStatus must not be null");
        }

        this.status = apiStatus.getCode();
        this.message = apiStatus.getDescription();
        this.data = data;
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
