(function() {
    'use strict';

    angular
        .module('yaldayProtoApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('merchantHome', {
            parent: 'app',
            url: '/merchantHome',
            data: {
                authorities: [],
                pageTitle: 'Home'
            },
            views: {
                'content@': {
                    templateUrl: 'app/merchantHome/merchantHome.html',
                    controller: 'merchantHomeController',
                    controllerAs: 'vm'
                }
            }
        })
        .state('merchantHome.resource', {
            parent: 'merchantHome',
            url: '/myResources',
            data: {
                authorities: []
            },
            views: {
                'manageType@merchantHome': {
                    templateUrl: 'app/merchantHome/manageResource.html'
                }
            }
        })
        .state('merchantHome.booking', {
            parent: 'merchantHome',
            url: '/myBookings',
            data: {
                authorities: []
            },
            views: {
                'manageType@merchantHome': {
                    templateUrl: 'app/merchantHome/manageBooking.html'
                }
            }
        })
        .state('merchantHome.signup', {
             parent: 'merchantHome',
             url: '/signUp',
             data: {
                  authorities: []
              },
                    views: {
                        'manageType@merchantHome': {
                            templateUrl: 'app/merchantHome/merchantSignUp.html'
                        }
                    }
                })
        .state('merchantHome.service', {
            parent: 'merchantHome',
            url: '/myServices',
            data: {
                authorities: []
            },
            views: {
                'manageType@merchantHome': {
                    templateUrl: 'app/merchantHome/manageService.html'
                }
            }
        });
    }
})();
