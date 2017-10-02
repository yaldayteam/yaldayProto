(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', '$state'];

    function HomeController ($scope, $state) {
        var vm = this;

        vm.goSearch = goSearch;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.date = new Date();  
        vm.datePickerOpenStatus.bookingDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
                        console.log('calendar!');

        }

        function goSearch() {
            $state.go('results');
            vm.searchtext = '';
            console.log('search!' + $state.current.name);
        }

    }
})();   