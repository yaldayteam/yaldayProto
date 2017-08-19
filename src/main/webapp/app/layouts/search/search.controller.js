(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('SearchController', SearchController);

    SearchController.$inject = ['$scope', '$state'];

    function SearchController ($scope, $state) {
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
            /*$state.go('search');*/
            vm.searchtext = '';
            console.log('search!' + $state.current.name);
        }

    }
})();