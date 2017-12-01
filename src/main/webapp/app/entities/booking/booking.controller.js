(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('BookingController', BookingController);

    BookingController.$inject = ['Booking', '$scope', '$http'];

    function BookingController(Booking, $scope, http) {

        var url = 'api/bookings/';

           $scope.options = {
              dataSource: new DevExpress.data.DataSource({
                store: new DevExpress.data.CustomStore({


                load: function(){
                    var result = $.Deferred();
                       $.ajax({
                            url: url,
                       }).done(function(response){
                            result.resolve(response);

                       });
                       return result.promise();
                    },



                insert: function(values){
                              var result = $.Deferred();
                                  $.ajax({
                                           url: url,
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
                                           url: url,
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
                            views: ["day", "workWeek", "month"],
                            currentDate: new Date(),
                            firstDayOfWeek: 0,
                            startDayHour: 8,
                            endDayHour: 21,
                            height: 600
                };

        var vm = this;

        vm.bookings = [];

        loadAll();


        function loadAll() {
            Booking.query(function(result) {
                vm.bookings = result;
                vm.searchQuery = null;
            });
        }

        }

})();
