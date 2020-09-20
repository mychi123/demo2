/*
 * Copyright (c) 689Cloud LLC, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of 689Cloud,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with 689Cloud.
 */
package com.cloud.spring.boot.common.exception;

import com.cloud.spring.boot.common.util.APIStatus;

/**
 * 
 * @author Quy Duong
 */
public class ApplicationException extends RuntimeException {

    private APIStatus apiStatus;
    
    public ApplicationException(APIStatus apiStatus) {
        this.apiStatus = apiStatus;
    }
    
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public APIStatus getApiStatus() {
        return apiStatus;
    }
}
