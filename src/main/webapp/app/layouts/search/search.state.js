(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('search', {
            parent: 'app',
            url: '/search',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/layouts/search/results.html',
                    controller: 'ResultsController',
                    controllerAs: 'vm'
                },
            }/*removed as it is now causing an error whereas in previous iterations it worked fine. Worth investigating.
            ,
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                }]
            }*/
        });
    }
})();
