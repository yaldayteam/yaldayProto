(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('BookingDialogController', BookingDialogController);

    BookingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Booking'];

    function BookingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Booking) {
        var vm = this;

        vm.booking = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.booking.id !== null) {
                Booking.update(vm.booking, onSaveSuccess, onSaveError);
            } else {
                Booking.save(vm.booking, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yaldayProtoApp:bookingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }


    }
})();
