'use strict';

angular.module('reseller', [])
    .controller('CreateResellerCtrl', [

        '$scope',
        'Util',
        'API',
        '$state',
        'Patterns',
        '$http',
        '$timeout',

        function ($scope, Util, API, $state, Patterns, $http, $timeout) {

            // $scope.currentUser = Session.getUser();
            //
            // $scope.lang =  $scope.currentUser.lang;

            $scope.emailPattern = Patterns.EMAIL_PATTERN;
            $scope.phoneNumber = Patterns.PHONE_PATTERN;
            $scope.websitePattern = Patterns.WEBSITE_PATTERN;
            $scope.submitting = false;

            $scope.isCallingAPI = false;

            $scope.reseller = {
                "resellerId": "",
                "name": "",
                "email": "",
                "phone": "",
                "fax": "",
                "contactName": "",
                "website": "",
            };

            $scope.createReseller = function () {

                if ($scope.isCallingAPI) {
                    return;
                }

                $scope.isCallingAPI = true;
                $scope.submitting = true;
                var paramReseller = {};

                paramReseller.email = $scope.reseller.email;
                paramReseller.name = $scope.reseller.name;
                paramReseller.website = $scope.reseller.website;
                paramReseller.phone = $scope.reseller.phone;
                paramReseller.fax = $scope.reseller.fax;
                paramReseller.contactName = $scope.reseller.contactName;


                Util.createRequest(API.CREATE_RESELLER, paramReseller, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        // show message delete successfully
                        Util.showSuccessToast(Util.translate("message.reseller.create_success"));
                        // goto car branch list
                        $state.go('reseller.list');
                    } else {
                        Util.showErrorAPI(status);
                    }
                    $scope.submitting = false;
                });

                $scope.isCallingAPI = false;
            }

        }
    ]);