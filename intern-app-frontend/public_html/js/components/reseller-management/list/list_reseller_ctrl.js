/*
 * Copyright (c) HOGO, Inc. All Rights Reserved.
 * This software is the confidential and proprietary information of HOGO,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with HOGO.
 */
'use strict';

angular.module("reseller", [])

    .controller('ListResellerCtrl', [
        '$scope',
        'Session',
        'Patterns',
        'APIStatus',
        'toastr',
        'Util',
        '$state',
        'API',
        '$location',
        '$uibModal',
        function ($scope, Session, Patterns, APIStatus, toastr, Util, $state, API, $location, $uibModal) {

            $scope.listReseller = [];
            $scope.listEmpty = false;
            $scope.selected = [];
            $scope.currentSelected = [];
            $scope.selectedAll = false;
            $scope.isSort = false;
            $scope.searchString = "";
            $scope.currentPage = 1;
            $scope.pageSize = 10;
            $scope.maxSize = 5;
            var param = {
                name: "",
                searchKey: "",
                sortCase: -1,
                ascSort: 1,
                pageNumber: 1,
                pageSize: 10
            };

            $scope.isCallingAPI = false;


            $scope.pageChanged = function () {
                $scope.loadListReseller();
            };

            $scope.doSort = function (sortCase) {
                $scope.loadListReseller(sortCase);
            };


            $scope.loadListReseller = function (sortCase) {

                if ($scope.isCallingAPI) {
                    return;
                }
                $scope.isCallingAPI = true;
                param.pageNumber = $scope.currentPage;
                param.pageSize = $scope.pageSize;
                if (sortCase) {
                    $scope.isSort = !$scope.isSort;
                    param.ascSort = $scope.isSort;
                    param.sortCase = sortCase;
                }
                // reset state
                $scope.selectedAll = false;
                param.searchKey = $scope.searchString;

                Util.createRequest(API.GET_LIST_RESELLER, param, function (response) {

                    var status = response.status;
                    if (status === 200) {
                        $scope.listReseller = response.data.content;
                        $scope.totalItems = response.data.totalElements;
                        $scope.totalPage = response.data.totalPages;

                        if ($scope.selected.length > 0) {
                            var numberCheckAll = 0;
                            $scope.listReseller.forEach(function (result) {
                                $scope.selected.forEach(function (selected) {
                                    if (result.resellerId === selected.resellerId) {
                                        result._selected = true;
                                        numberCheckAll++;
                                        if (numberCheckAll === $scope.listReseller.length) {
                                            $scope.selectedAll = true;
                                        }
                                    }
                                });
                            });
                        }
                    } else {
                        Util.showErrorAPI(status);

                    }
                    $scope.isCallingAPI = false;
                });
            };


            $scope.toggleSelectAll = function (isSelectAll) {
                if (isSelectAll) {
                    if ($scope.selected.length === 0) {
                        angular.copy($scope.listReseller, $scope.selected);
                    } else {
                        $scope.selected = $scope.selected.concat($scope.listReseller);
                        for (var i = 0; i < $scope.selected.length; ++i) { //distinct list selected
                            for (var j = i + 1; j < $scope.selected.length; ++j) {
                                if ($scope.selected[i].resellerId === $scope.selected[j].resellerId)
                                    $scope.selected.splice(j--, 1);
                            }
                        }
                    }
                } else {
                    $scope.listReseller.forEach(function (item) { //remove from selected list
                        $scope.selected.forEach(function (selected) {
                            if (item.resellerId === selected.resellerId)
                                $scope.currentSelected.push(selected);
                        });
                    });
                    $scope.removeSelected($scope.currentSelected);
                }

                //Set state for current reseller page
                _.forEach($scope.listReseller, function (reseller) {
                    reseller._selected = isSelectAll;
                });

            };

            $scope.toggleSelected = function (obj) {
                if (angular.isObject(obj) && obj.resellerId) {
                    var index = _.findIndex($scope.selected, {'resellerId': obj.resellerId});
                    if (index > -1) {
                        $scope.selected.splice(index, 1);
                        obj._selected = false;
                    } else {
                        $scope.selected.push(obj);
                        obj._selected = true;
                    }
                    $scope.selectedAll = false;//($scope.selected.length === $scope.listReseller.length);
                    var numberCheckAll = 0;
                    $scope.listReseller.forEach(function (result) {
                        $scope.selected.forEach(function (selected) {
                            if (result.resellerId === selected.resellerId) {
                                result._selected = true;
                                numberCheckAll++;
                                if (numberCheckAll === $scope.listReseller.length) {
                                    $scope.selectedAll = true;
                                } else {
                                    $scope.selectedAll = false;
                                }
                            }
                        });
                    });
                }
            };

            $scope.loadResellerDetail = function (resellerId) {
                $state.go('reseller.detail', {id: resellerId});
            };

            $scope.removeSelected = function (list) {
                list.forEach(function (item) {
                    var index = $scope.selected.indexOf(item);
                    $scope.selected.splice(index, 1);
                });
                $scope.currentSelected = [];
            };

            $scope.loadListReseller();

            $scope.confirmDeleteReseller = function (resellerId) {
                var listId = [];

                var message = "message.reseller.delete_mgs";
                if(resellerId){
                    message = "message.reseller.delete_single_mgs";
                }
                Util.showConfirmModal({
                    title: Util.translate('message.reseller.delete'),
                    message: Util.translate(message)
                }, function () {
                    var resellerIds = _.map($scope.selected, 'resellerId');
                    if (resellerId) {
                        listId.push(resellerId);
                    } else {
                        listId = resellerIds;
                    }

                    //Call API
                    var apiObj = {
                        method: API.DELETE_RESELLER.method,
                        path: API.DELETE_RESELLER.path + "?reseller_ids=" + listId.join(",")
                    };
                    Util.createRequest(apiObj, function (response) {

                        var status = response.status;
                        if (status === 200) {
                            $scope.selected = [];
                            // show message delete successfully
                            Util.showSuccessToast('message.reseller.delete_success');
                            // reload list
                            $scope.loadListReseller();
                        } else {
                            Util.showErrorToast('message.reseller.delete_error');
                        }
                    });
                });
            };

        }
    ]);
