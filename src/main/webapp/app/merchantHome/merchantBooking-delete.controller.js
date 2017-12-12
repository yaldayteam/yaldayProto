(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('MerchantBookingDeleteController',MerchantBookingDeleteController);

    MerchantBookingDeleteController.$inject = ['$uibModalInstance', 'entity', 'MerchantBooking'];

    function MerchantBookingDeleteController($uibModalInstance, entity, MerchantBooking) {
        var vm = this;

        vm.booking = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {

             MerchantBooking.delete({id},
                function () {
                    $uibModalInstance.close(true);
                });
        }

    }
})();
