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
        })
                .state('merchantHome.new-booking', {

                    parent: 'merchantHome',
                    url: '/new/merchantBooking',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/merchantHome/merchantHomeBooking-dialog.html',
                            controller: 'MerchantBookingDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                entity: function () {
                                    return {
                                        text: "Please enter your booking here",
                                        resource: null,
                                        startDate: null,
                                        endDate: null,
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
                .state('merchantHome.new-resource', {
                            parent: 'merchantHome',
                            url: '/new/merchantResource',
                            data: {
                                authorities: ['ROLE_USER']
                            },
                            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                                $uibModal.open({
                                    templateUrl: 'app/merchantHome/merchantHomeResource-dialog.html',
                                    controller: 'MerchantResourceDialogController',
                                    controllerAs: 'vm',
                                    backdrop: 'static',
                                    size: 'lg',
                                    resolve: {
                                        entity: function () {
                                            return {
                                                text: null,
                                                color: null,
                                                capacity: null,
                                                multiplebooking: null,
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
                .state('merchantHome.edit-resource', {
                            parent: 'merchantHome',
                            url: '/{id}/edit/merchantResource',
                            data: {
                                authorities: ['ROLE_USER']
                            },
                            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                                $uibModal.open({
                                    templateUrl: 'app/merchantHome/merchantHomeResource-dialog.html',
                                    controller: 'MerchantResourceDialogController',
                                    controllerAs: 'vm',
                                    backdrop: 'static',
                                    size: 'lg',
                                    resolve: {
                                        entity: ['Resource', function(Resource) {
                                            return Resource.get({id : $stateParams.id}).$promise;
                                        }]
                                    }
                                }).result.then(function() {
                                    $state.go('merchantHome', null, { reload: 'merchantHome' });
                                }, function() {
                                    $state.go('^');
                                });
                            }]
                })
                .state('merchantHome.delete-resource', {
                    parent: 'merchantHome',
                    url: '/{id}/delete/merchantResource',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/merchantHome/merchantHomeResource-delete.html',
                            controller: 'MerchantResourceDeleteController',
                            controllerAs: 'vm',
                            size: 'md',
                            resolve: {
                                entity: ['Resource', function(Resource) {
                                    return Resource.get({id : $stateParams.id}).$promise;
                                }]
                            }
                        }).result.then(function() {
                            $state.go('merchantHome', null, { reload: 'merchantHome' });
                        }, function() {
                            $state.go('^');
                        });
                    }]
                })
                .state('merchantHome.booking-edit', {
                    parent: 'merchantHome',
                    url: '/{id}/edit/merchantBooking',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                        templateUrl: 'app/merchantHome/merchantHomeBooking-dialog.html',
                        controller: 'MerchantBookingDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {
                                entity: ['Booking', function(Booking) {
                                    return Booking.get({id : $stateParams.id}).$promise;
                                }]
                            }
                        }).result.then(function() {
                            $state.go('^', {}, { reload: false });
                        }, function() {
                            $state.go('^');
                        });
                    }]
                })
                .state('merchantHome.booking-delete', {
                            parent: 'merchantHome',
                            url: '/{id}/delete/merchantBooking',
                            data: {
                                authorities: ['ROLE_USER']
                            },
                            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                                $uibModal.open({
                                    templateUrl: 'app/merchantHome/merchantHomeBooking-delete.html',
                                    controller: 'MerchantBookingDeleteController',
                                    controllerAs: 'vm',
                                    size: 'md',
                                    resolve: {
                                        entity: ['Booking', function(Booking) {
                                            return Booking.get({id : $stateParams.id}).$promise;
                                        }]
                                    }
                                }).result.then(function() {
                                    $state.go('merchantHome', null, { reload: 'merchantHome' });
                                }, function() {
                                    $state.go('^');
                                });
                            }]
                        });
    }
})();
