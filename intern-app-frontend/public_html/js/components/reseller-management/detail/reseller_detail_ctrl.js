'use strict';

angular.module('reseller', [])
    .controller('ResellerDetailCtrl', [

        '$scope',
        'Util',
        'API',
        '$state',
        'Patterns',
        '$stateParams',
        '$uibModal',
        'Session',
        'AppConfig',
        function ($scope, Util, API, $state, Patterns, $stateParams, $uibModal, Session, AppConfig) {

            $scope.emailPattern = Patterns.EMAIL_PATTERN;
            $scope.phoneNumber = Patterns.PHONE_PATTERN;
            $scope.websitePattern = Patterns.WEBSITE_PATTERN;

            $scope.showBntSave = true;
            $scope.submitting = false;
            $scope.isCallingAPI = false;

            var originReseller;

            $scope.getResellerDetail = function (cb) {

                if ($scope.isCallingAPI) {
                    return;
                }

                $scope.isCallingAPI = true;

                var apiObj = {
                    method: API.GET_RESELLER_DETAIL.method,
                    path: API.GET_RESELLER_DETAIL.path + $stateParams.id
                };

                Util.createRequest(apiObj, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        $scope.reseller = response.data;

                        originReseller = angular.copy($scope.reseller);
                        setWatch();
                    } else {
                        Util.showErrorAPI(status);
                    }
                });

                $scope.isCallingAPI = false;
            };

            function setWatch() {
                $scope.$watch('reseller', function (newValue) {
                    $scope.origin = angular.equals(newValue, originReseller);
                }, true);
            }

            $scope.getResellerDetail();

            $scope.updateReseller = function () {

                if ($scope.isCallingAPI) {
                    return;
                }

                $scope.isCallingAPI = true;

                $scope.showBntSave = false;
                $scope.submitting = true;
                var paramReseller = {};

                paramReseller.resellerId = $stateParams.id;
                paramReseller.email = $scope.reseller.email;
                paramReseller.name = $scope.reseller.name;
                paramReseller.fax = $scope.reseller.fax;
                paramReseller.phone = $scope.reseller.phone;
                paramReseller.website = $scope.reseller.website;
                paramReseller.contactName = $scope.reseller.contactName;

                var apiCAll = {
                    path: API.UPDATE_RESELLER.path,
                    method: API.UPDATE_RESELLER.method
                };
                Util.createRequest(apiCAll, paramReseller, function (response) {
                    var status = response.status;
                    if (status === 200) {
                        Util.showSuccessToast('message.reseller.update_success');
                        $scope.getResellerDetail();
                        $state.go('reseller.list');
                        $scope.submitting = false;
                    } else {
                        Util.showErrorAPI(status);
                        $scope.submitting = false;
                    }
                });

                $scope.isCallingAPI = false;
            }

            $scope.confirmDeleteReseller = function () {

                Util.showConfirmModal({
                    title: Util.translate('message.reseller.delete'),
                    message: Util.translate('message.reseller.delete_single_mgs')
                }, function () {
                    //Call API
                    var apiObj = {
                        method: API.DELETE_RESELLER.method,
                        path: API.DELETE_RESELLER.path + "?reseller_ids=" + $stateParams.id
                    };
                    Util.createRequest(apiObj, function (response) {

                        var status = response.status;
                        if (status === 200) {
                            $scope.selected = [];
                            // show message delete successfully
                            Util.showSuccessToast('message.reseller.delete_success');
                            // reload list
                            $state.go('reseller.list');
                        } else {
                            Util.showErrorToast('message.reseller.delete_error');
                        }
                    });
                });
            };

        }
    ])
;