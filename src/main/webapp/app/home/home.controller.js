(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
            console.log('Thinking about whether to go to merchant state or not');
            if (vm.account.userType === 'merchant') {
                console.log('Going to merchant state');
                $state.go('merchantHome');
            } else {
                console.log('Not going to merchant state');
            }
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
