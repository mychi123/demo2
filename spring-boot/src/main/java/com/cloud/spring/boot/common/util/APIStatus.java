/*
 * Copyright (c) 689Cloud LLC, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of 689Cloud,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with 689Cloud.
 */
package com.cloud.spring.boot.common.util;

/**
 *
 * @author Quy Duong
 */
public enum APIStatus {
    OK(200, "OK"),
    NO_RESULT(201, "No more result."),
    ERR_INTERNAL_SERVER(500, "Internal server error."),
    //////////////////
    // CLIENT SIDE  //
    //////////////////
    ERR_BAD_REQUEST(400, "Bad request"),
    ERR_UNAUTHORIZED(401, "Unauthorized or Access Token is expired"),
    ERR_FORBIDDEN(403, "Forbidden! Access denied"),
    ERR_NOT_FOUND(404, "Not Found"),
    ERR_BAD_PARAMS(406, "Bad parameters"),
    ERR_ALREADY_EXISTED(407, "Already exsited."),
    ERR_CREATE_RECORD(408, "Can not create new");
    
    private final int code;
    private final String description;

    private APIStatus(int s, String v) {
        code = s;
        description = v;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static APIStatus getEnum(int code) {
        for (APIStatus v : values())
            if (v.getCode() == code) return v;
        throw new IllegalArgumentException();
    }
}
