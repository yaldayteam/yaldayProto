(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = [ '$timeout', 'Auth', 'LoginService', '$uibModalInstance'];

    function RegisterController ($timeout, Auth, LoginService, $uibModalInstance) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;
        vm.cancel = cancel;

        $timeout(function (){angular.element('#login').focus();});

        function cancel () {
            vm.authenticationError = false;
            $uibModalInstance.dismiss('cancel');
        }

        function register () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {


           if(vm.userType == true )
                    {
                        vm.registerAccount.userType = 'merchant';
                    }
                    else
                    {
                        vm.registerAccount.userType = 'customer';
                    }



                vm.registerAccount.langKey =  'en' ;
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'email address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
    }
})();
