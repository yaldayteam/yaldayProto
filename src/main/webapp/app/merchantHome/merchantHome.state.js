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
          .state('merchantHome.new', {
                    parent: 'merchantHome',
                    url: '/new',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/entities/merchant/merchant-dialog.html',
                            controller: 'MerchantDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                entity: function () {
                                    return {
                                        name: null,
                                        description: null,
                                        address: null,
                                        city: null,
                                        postcode: null,
                                        country: null,
                                        category: null,
                                        bgColor: null,
                                        textColor: null,
                                        email: null,
                                        phoneNumber: null,
                                        id: null
                                    };
                                }
                            }
                        }).result.then(function() {
                            $state.go('merchantHome', null, { reload: 'merchantHome' });
                        }, function() {
                            $state.go('merchantHome');
                        });
                    }]
                })
         .state('merchantHome.edit', {
                    parent: 'merchantHome',
                    url: '/detail/edit/:id',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/entities/merchant/merchant-dialog.html',
                            controller: 'MerchantDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                entity: ['Merchant', function(Merchant) {
                                    return Merchant.get({id : $stateParams.id}).$promise;
                                }]
                            }
                        }).result.then(function() {
                             $state.go('merchantHome', null, { reload: 'merchantHome' });
                             }, function() {
                             $state.go('merchantHome', null, { reload: 'merchantHome' });
                        });
                    }]
                })
       .state('merchantHome.delete', {
                           parent: 'merchantHome',
                           url: '/delete/:id',
                           data: {
                               authorities: ['ROLE_USER']
                           },
                           onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                               $uibModal.open({
                                   templateUrl: 'app/entities/merchant/merchant-delete-dialog.html',
                                   controller: 'MerchantDeleteController',
                                   controllerAs: 'vm',
                                   size: 'md',
                                   resolve: {
                                       entity: ['Merchant', function(Merchant) {
                                           return Merchant.get({id : $stateParams.id}).$promise;
                                       }]
                                   }
                               }).result.then(function() {
                                    $state.go('merchantHome', null, { reload: 'merchantHome' });
                                    }, function() {
                                    $state.go('merchantHome', null, { reload: 'merchantHome' });
                               });
                           }]
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
