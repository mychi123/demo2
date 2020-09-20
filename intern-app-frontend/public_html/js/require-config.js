/*
 * Copyright (c) HoGo, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of HoGo,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HoGo.
 */

// Using bower to manage the dependency
// Define module & its deps
// For init app
require.config({

    waitSeconds: 45,
    urlArgs: 'bust=' + new Date().getTime(), // Prevent cache
    baseUrl: 'js',
    paths: {
        // Vendors
        angular: '../vendors/angular/angular.min',
        angular_route: '../vendors/angular-route/angular-route.min',
        angular_cookie: '../vendors/angular-cookies/angular-cookies.min',
        angular_sanitize: '../vendors/angular-sanitize/angular-sanitize.min',
        angular_animate: '../vendors/angular-animate/angular-animate.min',
        
        jquery: '../vendors/jquery/dist/jquery.min',
        jquery_ui: "../vendors/jquery-ui/jquery-ui.min",
        
        i18next: 'libs/i18next-1.9.0.min',
        ng_i18next: 'libs/ng-i18next',
        angular_tags_input: 'libs/ng-tags-input.min',

        ui_router: '../vendors/angular-ui-router/release/angular-ui-router',
        ui_select: '../vendors/angular-ui-select/dist/select.min',
        angular_bootstrap: '../vendors/angular-bootstrap/ui-bootstrap-tpls.min',
        
        lodash: '../vendors/lodash/dist/lodash.min',
        noty: '../vendors/noty/lib/noty.min',
        bootstrap: '../vendors/bootstrap/dist/js/bootstrap.min',
        icheck: '../vendors/iCheck/icheck.min',
        oc_lazyload: '../vendors/oclazyload/dist/ocLazyLoad.min',
        toastr: '../vendors/angular-toastr/dist/angular-toastr.tpls.min',

        // theme's libs
        nprocess: '../vendors/nprogress/nprogress',
        theme: '../theme/js/theme',

        // app's libs
        constant: 'util/constant',
        utils: 'util/util',
        session: 'util/session',
        moment: "../js/libs/moment.min",
        icheck_directive: '../js/common/directives/icheck',
        file_download: '../vendors/jquery-file-download/src/Scripts/jquery.fileDownload'
    },
    // Define dependency for each object
    // If we do this, we dont have to use define
    // There're a lot of things that I can't understand from this config
    shim: {
        'angular': ['jquery'],
        'angular_route': ['angular'],
        'angular_cookie': ['angular'],
        'angular_sanitize': ['angular'],
        'angular_animate': ['angular'],
        'angular_tags_input': ['angular'],
        'ui_router': ['angular'],
        'ui_select': ['angular'],
        'angular_bootstrap': ['angular'],
        'toastr': ['angular'],
        'jquery_ui': [ "jquery" ],
        'file_download': ['jquery'],
        'i18next': {
            'deps': ['jquery'],
            'exports': 'i18next'
        },
        'ng_i18next': {
            'deps': ['angular', 'i18next'],
            'exports': 'ng_i18next'
        },
        'oc_lazyload': ['angular'],
        'bootstrap': ['jquery'],
        'noty': {
            deps: ['jquery'],
            exports: 'noty'
        },
        'moment': ['jquery'],
        'theme': ['jquery', 'bootstrap', 'nprocess'],
        'constant': ['angular'],
        'utils': ['angular', 'constant', 'angular_bootstrap'],
        'session': ['utils', 'angular_cookie'],
        // Main module
        'app': ['angular_route', 'angular_sanitize', 'angular_cookie', 'angular_animate', 'angular_tags_input', 'ui_router', 'ui_select', 'ng_i18next', 'oc_lazyload', 'theme', 'toastr', 'lodash', 'session', 'angular_bootstrap', 'file_download', 'moment','jquery_ui']
    },
    priority: [
        'jquery', 'angular'
    ]
});

// Bootstrap the app
// All modules have to be available here
// It hard to understand with jqLite
require(['app'], function () {
    angular.element().ready(function () {
        // bootstrap the app manually
        angular.bootstrap(document, ['internapp']);
    });
});