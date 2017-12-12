(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('MerchantBookingDialogController', MerchantBookingDialogController);

    MerchantBookingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MerchantBooking', 'Principal'];

    function MerchantBookingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MerchantBooking, Principal) {

        var vm = this;

        vm.booking = entity;
        vm.account = vm.account;

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;


        getAccount();

        function getAccount() {
         Principal.identity().then(function(account) {
             vm.account = account;
             vm.isAuthenticated = Principal.isAuthenticated;
         });
        }


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {

            vm.isSaving = true;
            if (vm.booking.id !== null) {
                MerchantBooking.update({id: vm.account.merchant.id},vm.booking, onSaveSuccess, onSaveError);
            } else {
                MerchantBooking.save({id: vm.account.merchant.id},vm.booking, onSaveSuccess, onSaveError);
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
