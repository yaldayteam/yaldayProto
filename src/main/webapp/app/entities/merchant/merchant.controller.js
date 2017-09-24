(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('MerchantController', MerchantController);

    MerchantController.$inject = ['$scope', '$state', 'Merchant'];

    function MerchantController ($scope, $state, Merchant) {
        var vm = this;

        vm.merchants = [];

        loadAll();

        function loadAll() {
            Merchant.query(function(result) {
                vm.merchants = result;
            });
        }
    }
})();
