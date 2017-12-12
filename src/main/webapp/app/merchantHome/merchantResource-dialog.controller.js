(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('MerchantResourceDialogController', MerchantResourceDialogController);

    MerchantResourceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MerchantResource', 'Principal'];

    function MerchantResourceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MerchantResource, Principal) {
        var vm = this;

        vm.resource = entity;
        vm.account = vm.account;

        vm.clear = clear;
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
            if (vm.resource.id !== null) {
                MerchantResource.update({id: vm.account.merchant.id}, vm.resource, onSaveSuccess, onSaveError);
            } else {
                MerchantResource.save({id: vm.account.merchant.id}, vm.resource, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yaldayProtoApp:resourceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
