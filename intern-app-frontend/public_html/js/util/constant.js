/*
 * Copyright (c) HoGo, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of HoGo,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HoGo.
 */

// // I put all constant value here
// so that everybody can go here to modify the constant value if any changes
angular.module('internapp.constant', [])

        // App config
        .constant("AppConfig", {

            // Cache module using ocLazyLoad
            OCLAZY_CACHE_MODULE: true, // Should use "true" when deploy to server
            //local
            API_PATH: 'http://localhost:8080/api/v1'

        })

        // patterns
        .constant("Patterns", {
            EMAIL_PATTERN: /^[_A-Za-z0-9-\+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9-\]+)*(\.[A-Za-z]{2,})$/,
        })

        // APIs
        .constant('API', {

            GET_USER_LIST: {path: '/auth/logout', method: "POST"}
        })

        // API status
        .constant('APIStatus', [

            {status: 200, mgsKey: 'api.status.200', message: "OK"}
            ,
            //////////////////
            // CLIENT SIDE  //
            //////////////////
            {status: 400, mgsKey: 'api.status.400', message: "Bad request"}
            ,
            {status: 401, mgsKey: 'api.status.401', message: "Unauthorized or Access Token is expired"}
            ,
            {status: 403, mgsKey: 'api.status.403', message: "Forbidden! Access denied"}
            ,
            {status: 406, mgsKey: 'api.status.406', message: "Bad parameters"}

        ]);
