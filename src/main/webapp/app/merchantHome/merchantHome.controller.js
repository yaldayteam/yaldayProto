(function() {
         'use strict';

         angular
             .module('yaldayProtoApp')
             .controller('merchantHomeController', merchantHomeController);

         merchantHomeController.$inject = ['$scope', '$state', 'Principal', '$stateParams' ];

         function merchantHomeController ($scope, $state, Principal, $stateParams) {
             var vm = this;

             vm.account = null;







             $stateParams.id = null;
             getAccount();

               function getAccount() {
                         Principal.identity().then(function(account) {
                             vm.account = account;
                             vm.isAuthenticated = Principal.isAuthenticated;


                         });
                     }



         }
     })();
