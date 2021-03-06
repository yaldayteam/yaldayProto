(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('MerchantDialogController', MerchantDialogController);

    MerchantDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Merchant', 'Principal', 'User', 'Auth'];

    function MerchantDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Merchant, Principal, User, Auth) {
        var vm = this;

        vm.merchant = entity;
        vm.clear = clear;
        vm.save = save;
        vm.user = User;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.merchant.id !== null) {


                Merchant.update(vm.merchant, onSaveSuccess, onSaveError);

                     Auth.updateAccount(vm.user).then(function() {
                                    vm.error = null;
                                    vm.success = 'OK';

                                }).catch(function() {
                                    vm.success = null;
                                    vm.error = 'ERROR';
                                });

            } else {
                Merchant.save(vm.merchant, onSaveSuccess, onSaveError);


                Auth.updateAccount(vm.user).then(function() {
                    vm.error = null;
                    vm.success = 'OK';

                }).catch(function() {
                    vm.success = null;
                    vm.error = 'ERROR';
                });
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('yaldayProtoApp:merchantUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        var copyUser = function (user) {
                return {
                    id: user.id,
                    activated: user.activated,
                    email: user.email,
                    firstName: user.firstName,
                    langKey: user.langKey,
                    lastName: user.lastName,
                    login: user.login

                };

            };

        Principal.identity().then(function(user) {
            vm.user = copyUser(user);
        });






















    }
})();
