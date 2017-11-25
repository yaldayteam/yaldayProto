(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('BookingController', BookingController);

    BookingController.$inject = ['Booking', '$scope'];

    function BookingController(Booking, $scope) {

        var url = "api/bookings/";

                $scope.options = {
                dataSource: url,
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
