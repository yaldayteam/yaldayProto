(function() {
    'use strict';
    angular
        .module('yaldayProtoApp')
        .factory('Booking', Booking);

    Booking.$inject = ['$resource', 'DateUtils'];

    function Booking ($resource, DateUtils) {
        var resourceUrl =  'api/bookings/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
