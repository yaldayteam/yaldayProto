(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('ResourceController', ResourceController);

    ResourceController.$inject = ['Resource'];

    function ResourceController(Resource) {

        var vm = this;

        vm.resources = [];

        loadAll();

        function loadAll() {
            Resource.query(function(result) {
                vm.resources = result;
                vm.searchQuery = null;
            });
        }
    }
})();
