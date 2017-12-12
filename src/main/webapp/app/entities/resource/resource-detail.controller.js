(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .controller('ResourceDetailController', ResourceDetailController);

    ResourceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Resource'];

    function ResourceDetailController($scope, $rootScope, $stateParams, previousState, entity, Resource) {
        var vm = this;

        vm.resource = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('yaldayProtoApp:resourceUpdate', function(event, result) {
            vm.resource = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
