(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('home', {
            parent: 'app',
            url: '/',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/home.html',
                    controller: 'HomeController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('results', {
            url: '/results',
            parent: 'home',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/home/results.html',
                    controller: 'ResultsController',
                    controllerAs: 'vm'
                },
            }
        })

        ;
    }
})();
