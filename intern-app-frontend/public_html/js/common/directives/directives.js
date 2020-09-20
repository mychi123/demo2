/*
 * Copyright (c) HOGO, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of HOGO,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HOGO.
 */
'use strict';

angular.module('internapp')
// Compare to another value
// Such as confirm password
        .directive("compareTo", function () {

            return {
                require: "ngModel",
                scope: {
                    otherModelValue: "=compareTo"
                },
                link: function (scope, element, attributes, ngModel) {

                    ngModel.$validators.compareTo = function (modelValue) {

                        var cp = (modelValue === scope.otherModelValue.$modelValue);
                        ngModel.$setValidity('compare', cp);
                        return cp;
                    };

                    scope.$watch("otherModelValue.$modelValue", function () {

                        ngModel.$validate();
                    });
                }
            };
        })

// Another way check repeat
        .directive("repeatPassword", function () {
            return {
                require: "ngModel",
                link: function (scope, elem, attrs, ctrl) {

                    var otherInput = elem.inheritedData("$formController")[attrs.repeatPassword];

                    ctrl.$parsers.push(function (value) {
                        if (value === otherInput.$viewValue) {
                            ctrl.$setValidity("repeat", true);
                            return value;
                        }
                        ctrl.$setValidity("repeat", false);
                        // ctrl.$invalid = false;
                    });

                    otherInput.$parsers.push(function (value) {
                        ctrl.$setValidity("repeat", value === ctrl.$viewValue);
                        // ctrl.$invalid = ( value === ctrl.$viewValue );
                        return value;
                    });
                }
            };
        })
        .directive('enforceMaxTags', function () {
            return {
                require: 'ngModel',
                link: function (scope, element, attrs, ngModelController) {
                    var maxTags = attrs.maxTags ? parseInt(attrs.maxTags, '10') : null;
                    ngModelController.$validators.checkLength = function (value) {
                        if (value && maxTags && value.length > maxTags) {
                            value.splice(value.length - 1, 1);
                        }
                        return value;
                    };
                }
            };
        })

        .directive('slideToggle', function() {
            return {
                restrict: 'A',
                scope:{
                    isOpen: "=slideToggle" // 'data-slide-toggle' in our html
                },
                link: function(scope, element, attr) {
                    var slideDuration = parseInt(attr.slideToggleDuration, 10) || 200;
                    // watch change isOpen of Slide Toggle
                    scope.$watch('isOpen', function(newVal, oldVal){
                        // set default auto hide file data
                        if(oldVal === undefined) {
                            oldVal = false;
                        }

                        if(newVal !== oldVal){
                            element.stop().slideToggle(slideDuration);
                        }
                    });

                }
            };
        })

        // Datepicker ( jQuery UI )
        // #mydatepicker
        .directive('mydatepicker', function ($parse) {
            return {
                restrict: "A",
                replace: true,
                transclude: false,
                compile: function () {

                    return function (scope, element, attrs) {

                        var modelAccessor = $parse(attrs.ngModel);

                        var processChange = function (dateText) {

                            // var date = element.datepicker("getDate");
                            scope.$apply(function () {
                                // Change bound variable
                                modelAccessor.assign(scope, dateText);
                            });
                        };

                        var opts = {
                            autoSize: true,
                            // Datepicker is now using format ISO 8601
                            dateFormat: attrs.dateFormat ? attrs.dateFormat : 'mm/dd/yy',
                            // onClose: processChange,
                            onSelect: processChange
                        };

                        // Get min date if have
                        var min = attrs.minDate;

                        if (min) {
                            opts.minDate = new Date(min);
                        }

                        // apply
                        element.datepicker(opts);
                    };
                }
            };
        })

    .directive("limitToMin", function () {
        return {
            link: function (scope, element, attributes) {
                element.on("keyup", function (e) {
                    if (Number(element.val()) < Number(attributes.min) &&
                        e.keyCode != 46 // delete
                        &&
                        e.keyCode != 8 // backspace
                    ) {
                        e.preventDefault();
                        element.val(attributes.min);
                    }
                });
            }
        };
    })
    .directive("dateValidation", ["Util", function(Util) {
        return {
            restrict: "A",
            require: "ngModel",
            link: function (scope, el, attrs) {
                var model = attrs.ngModel;
                var name = attrs.name;
                var formName = el[0].form.name;
                scope.$watch(model, function (newVal) {
                    scope[formName][name].$invalid = (newVal && Util.checkDate(newVal) !== "");
                });
            }
        };
    }]);

