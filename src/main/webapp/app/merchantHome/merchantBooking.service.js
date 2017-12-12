(function() {
    'use strict';
    angular
        .module('yaldayProtoApp')
        .factory('MerchantBooking', MerchantBooking);

    MerchantBooking.$inject = ['$resource', 'DateUtils'];

    function MerchantBooking ($resource, DateUtils) {

        var resourceUrl =  'api/merchants/booking/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT'}
        });
    }
})();
