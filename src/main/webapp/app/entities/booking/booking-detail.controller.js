(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('BookingDetailController', BookingDetailController);

    BookingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Booking'];

    function BookingDetailController($scope, $rootScope, $stateParams, previousState, entity, Booking) {
        var vm = this;

        vm.booking = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('yaldayProtoApp:bookingUpdate', function(event, result) {
            vm.booking = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
