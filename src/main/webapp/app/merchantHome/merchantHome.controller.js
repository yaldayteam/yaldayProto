 (function() {
         'use strict';

         angular
             .module('yaldayProtoApp')
             .controller('merchantHomeController', merchantHomeController);

         merchantHomeController.$inject = ['$scope', '$state', 'Principal', '$stateParams', 'MerchantBooking', 'MerchantResource', '$http'];

         function merchantHomeController ($scope, $state, Principal, $stateParams, MerchantBooking, MerchantResource, http) {

             var vm = this;
             vm.account = null;
             $stateParams.id = null;

             getAccount();


            function getAccount() {
             Principal.identity().then(function(account) {
                 vm.account = account;
                 vm.isAuthenticated = Principal.isAuthenticated;
                 loadAll();
             });
            }

            var url = 'api/merchants/booking/';

            //added by mrae on 11/12

     var priorityData = new DevExpress.data.DataSource({
                            store: new DevExpress.data.CustomStore({

                                load: function(){
                                    var result = $.Deferred();
                                        $.ajax({
                                            url: 'api/merchants/resource/' + encodeURIComponent(vm.account.merchant.id),
                                        }).done(function(response)
                                        {
                                            result.resolve(response);
                                        });
                                    return result.promise();
                                }
                            })
     });


            $scope.options = {
                          dataSource: new DevExpress.data.DataSource({
                            store: new DevExpress.data.CustomStore({


                            load: function(){
                                var result = $.Deferred();
                                   $.ajax({
                                        url: url + encodeURIComponent(vm.account.merchant.id),
                                   }).done(function(response){
                                        result.resolve(response);

                                   });
                                   return result.promise();
                                },

                            insert: function(values){
                                          var result = $.Deferred();
                                              $.ajax({
                                                       url: url + encodeURIComponent(vm.account.merchant.id),
                                                       method: "POST",
                                                       data: angular.toJson(values),
                                                       headers: {
                                                           'Content-Type': 'application/json; charset=UTF-8'
                                                       }
                                                      }).done(function(response){
                                                           result.resolve(response);
                                                      });
                                                         return result.promise();
                                                },

                              remove: function(key){
                                                var result = $.Deferred();
                                                console.log(key);
                                                   $.ajax({
                                                        url: url + encodeURIComponent(key.id),
                                                        method: "DELETE",
                                                   }).done(function(response){
                                                        result.resolve(response);
                                                   });
                                                   return result.promise();
                                                },


                             update: function(key, values){
                                               var result = $.Deferred();
                                                  $.ajax({
                                                       url: url + encodeURIComponent(vm.account.merchant.id),
                                                       data: angular.toJson(values),
                                                       method: "PUT",
                                                         headers: {
                                                                   'Content-Type': 'application/json; charset=UTF-8'
                                                         }

                                                  }).done(function(response){
                                                       result.resolve(response);

                                                  });
                                                  return result.promise();
                                               },
                            onBeforeSend: function(method, ajaxOptions) {
                                            ajaxOptions.xhrFields = { withCredentials: true };
                                        }

                            })
                            }),
                                       groups: ["priorityId"],
                                       resources: [
                                                   {
                                                        fieldExpr: "priorityId",
                                                        allowMultiple: false,
                                                        dataSource: priorityData,
                                                        label: "Priority"
                                                   }],

                                        views: ["day", "workWeek", "month"],
                                        currentDate: new Date(),
                                        firstDayOfWeek: 0,
                                        startDayHour: 8,
                                        endDayHour: 21,
                                        height: 600
                            };

                    vm.bookings = [];

                    function loadAll() {
                        MerchantBooking.query({id: vm.account.merchant.id}, function(result) {
                            vm.bookings = result;
                            vm.searchQuery = null;
                        });

                        MerchantResource.query({id: vm.account.merchant.id}, function(resourceResult) {
                            vm.resources = resourceResult;
                        });

                    }
         }



     })();
