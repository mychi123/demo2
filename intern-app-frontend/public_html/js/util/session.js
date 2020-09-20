/*
 * Copyright (c) HoGo, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of HoGo,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HoGo.
 */
'use strict';

// Session module. Provide session factory $sessionFactory
angular.module('internapp.session', ['ngCookies', 'internapp.utils', 'internapp.constant'])

// Session
        .factory('Session', [

            '$cookies',
            'AppConfig',
            'API',
            'APIStatus',
            'toastr',
            'Util',
            '$state',
            '$q',
            '$rootScope',
            '$i18next',

            function ($cookies, AppConfig, API, APIStatus, toastr, Util, $state, $q, $rootScope, $i18next) {
                var service = {
                    detectBrowserLang: function () {
                        // Language code: http://4umi.com/web/html/languagecodes.php || http://msdn.microsoft.com/en-us/library/ie/ms533052.aspx
                        var lang = window.navigator.browserLanguage || window.navigator.language;
                        var language = lang.substr(0, 2);
                        switch (language) {
                            case 'en':
                            case 'ja':
                                return language;
                                break;
                            default:
                                return 'en';
                                break;
                        }
                    },
                    // Do change language
                    changeLanguage: function (lang) {
                        if (!angular.isDefined(lang))
                            return;
                        if (lang === 'auto')
                            lang = this.detectBrowserLang();
                        // Listen event changing process is done
                        var dfd = $q.defer();

                        // Check change
                        if (lang === $i18next.options.lng) {

                            dfd.resolve(lang);
                        } else {
                            // Apply change
                            $i18next.options = {
                                lng: lang,
                                resGetPath: 'i18n/app.' + lang + '.json'
                            };

                            var call = $rootScope.$on('i18nextLanguageChange', function (event, lang) {

                                dfd.resolve(lang, event);

                                // Unregister
                                call();
                            });
                        }
                        return dfd.promise;
                    }
                };

                return service;
            }
        ]);
