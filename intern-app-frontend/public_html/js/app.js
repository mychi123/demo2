/*
 * Copyright (c) HoGo, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of HoGo,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HoGo.
 */
'use strict';

define(['theme', 'nprocess'], function (theme, NProgress) {

    var internapp = angular.module('internapp', [
        'ngRoute',
        'ngCookies',
        'ngSanitize',
        'ngAnimate',
        'ui.router',
        'ui.select',
        'jm.i18next',
        'oc.lazyLoad',
        'toastr',
        'ui.bootstrap',
        'internapp.utils',
        'internapp.constant',
        'internapp.session',
        'ngTagsInput'
    ])
            // Config ocLazy loading
            .config(['$ocLazyLoadProvider', 'AppConfig', function ($ocLazyLoadProvider, AppConfig) {

                    var modules = [
                        {
                            name: 'resellerListModule',
                            files: [
                                'vendors/jquery/dist/jquery.min',
                                'vendors/iCheck/skins/flat/blue.css',
                                'vendors/iCheck/icheck.min.js',
                                'js/common/directives/directives.js',
                                'js/common/directives/icheck.js',
                                'js/components/reseller-management/list/list_reseller_ctrl.js'
                            ]
                        },
                        {
                            name: 'resellerCreateModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'js/common/directives/directives.js',
                                'js/components/reseller-management/create/create_reseller_ctrl.js'
                            ]
                        },
                        {
                            name: 'resellerDetailModule',
                            files: [
                                'vendors/iCheck/skins/flat/blue.css',
                                'js/common/directives/directives.js',
                                'js/components/reseller-management/detail/reseller_detail_ctrl.js'
                            ]
                        }
                    ];

                    // Read config value
                    if (angular.isDefined(AppConfig.OCLAZY_CACHE_MODULE)) {
                        angular.forEach(modules, function (v) {
                            v.cache = AppConfig.OCLAZY_CACHE_MODULE;
                        });
                    }

                    // We define some files for a specific module
                    $ocLazyLoadProvider.config({
                        modules: modules
                    });
                }])

            /**
             * UI-Router config with ocLazy load module
             *
             * @param {type} $stateProvider
             * @param {type} $urlRouterProvider
             * @returns {undefined}
             */
            .config(['$stateProvider', '$urlRouterProvider', '$urlMatcherFactoryProvider', '$locationProvider', function ($stateProvider, $urlRouterProvider, $urlMatcherFactoryProvider, $locationProvider) {

                    var DEVELOPMENT_MODE = true;
                    // Configure path URL
                    if (!DEVELOPMENT_MODE) {
                        // see "http://stackoverflow.com/questions/16677528/location-switching-between-html5-and-hashbang-mode-link-rewriting"
                        $locationProvider.html5Mode(true).hashPrefix('!');

                        // false to match URL in a case sensitive manner; otherwise true
                        $urlMatcherFactoryProvider.caseInsensitive(true);
                        // false to match trailing slashes in URLs
                        $urlMatcherFactoryProvider.strictMode(false);
                    }

                    $urlRouterProvider.otherwise('reseller/list');

                    $stateProvider
                            .state({
                                name: 'authorized',
                                abstract: true,
                                templateUrl: 'js/components/template/authorized.html'
                            })
                            //reseller
                            .state({
                                name: 'reseller',
                                parent: 'authorized',
                                abstract: true,
                                url: '/reseller',
                                template: '<div ui-view></div>'
                            })
                            .state({
                                name: 'reseller.list',
                                url: '/list',
                                templateUrl: 'js/components/reseller-management/list/list_reseller_tmpl.html',
                                controller: 'ListResellerCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('resellerListModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'reseller.create',
                                url: '/create',
                                templateUrl: 'js/components/reseller-management/create/create_reseller_tmpl.html',
                                controller: 'CreateResellerCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('resellerCreateModule');
                                        }]
                                }
                            })
                            .state({
                                name: 'reseller.detail',
                                url: '/detail/{id}',
                                templateUrl: 'js/components/reseller-management/detail/reseller_detail_tmpl.html',
                                controller: 'ResellerDetailCtrl',
                                resolve: {
                                    loadModule: ['$ocLazyLoad', function ($ocLazyLoad) {
                                            return $ocLazyLoad.load('resellerDetailModule');
                                        }]
                                }
                            })

                }])

            // Config request
            // Set up interceptor
            .config(['$httpProvider', function ($httpProvider) {

                    $httpProvider.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';

                    // $httpProvider.interceptors.push(function ($q, $cookies) {
                    //     return {
                    //         'request': function (config) {
                    //             config.headers['X-Access-Token'] = $cookies.get('AccessToken');
                    //             return config;
                    //         }
                    //     };
                    // });
                }])

            // Angular toastr config
            // see more https://github.com/Foxandxss/angular-toastr
            .config(['toastrConfig', function (toastrConfig) {
                    angular.extend(toastrConfig, {
                        autoDismiss: false,
                        containerId: 'toast-container',
                        maxOpened: 1,
                        newestOnTop: true,
                        positionClass: 'toast-top-center',
                        preventDuplicates: false,
                        preventOpenDuplicates: true,
                        target: 'body'
                    });
                }])

            // ui select config
            .config(['uiSelectConfig', function (uiSelectConfig) {
                    uiSelectConfig.theme = 'bootstrap';
                    uiSelectConfig.resetSearchInput = true;
                    uiSelectConfig.appendToBody = true;
                }])

            .run(['$rootScope', function ($rootScope) {

                    $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams, options) {
                        NProgress.start();
                    });

                    // Catch event load page
                    $rootScope.$on("$stateChangeSuccess", function (event, toState, toParams, fromState, fromParams) {

                    });

                    // Route change error
                    $rootScope.$on('$stateChangeError', function (event, toState, toParams, fromState, fromParams, error) {

                    });

                    $rootScope.$on('$viewContentLoaded', function (event) {

                        theme.setUpTheme();
                        theme.updateContentHeight();
                        NProgress.done();
                    });

                }]);

    // Set fallback lang will be loaded
    // Config i18next localize
    angular.module('jm.i18next').config(['$i18nextProvider', function ($i18nextProvider) {
            // Language code: http://4umi.com/web/html/languagecodes.php || http://msdn.microsoft.com/en-us/library/ie/ms533052.aspx
            var lang = window.navigator.browserLanguage || window.navigator.language;
            var language = lang.substr(0, 2);
            if (typeof language === 'undefined' || language !== "ja")
                language = 'en';// set as default

            $i18nextProvider.options = {
                lng: language,
                // selectorAttr: 'data-hg',
                fallbackLng: ["en"], // fallback
                resGetPath: 'i18n/app.' + language + '.json',
                debug: true,
                getAsync: true,
                // preload: ["en", "ja"], // ['en', 'ja'],
                defaultLoadingValue: 'Loading'
            };
        }]);

    return internapp;
});
