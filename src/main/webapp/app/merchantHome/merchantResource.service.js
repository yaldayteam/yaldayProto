(function() {
    'use strict';
    angular
        .module('yaldayProtoApp')
        .factory('MerchantResource', MerchantResource);

    MerchantResource.$inject = ['$resource'];

    function MerchantResource ($resource) {
        var resourceUrl =  'api/merchants/resource/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        console.log(data);
                        data = angular.fromJson(data);
                    }
                    console.log("no data");
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
