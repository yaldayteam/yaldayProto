(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('MerchantResourceDeleteController',MerchantResourceDeleteController);

    MerchantResourceDeleteController.$inject = ['$uibModalInstance', 'entity', 'MerchantResource'];

    function MerchantResourceDeleteController($uibModalInstance, entity, MerchantResource) {
        var vm = this;

        vm.resource = entity;
        vm.account = vm.account;
        vm.clear = clear;

        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MerchantResource.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
