(function() {
    "use strict";

    angular
        .module('yaldayProtoApp')
        .controller("ResultsController", ResultsController);

    ResultsController.$inject = ["$scope", "$state"];

    function ResultsController ($scope, $state) {
      
      var vm = this;
    
       /*vm.users = ["No1", "No2", "No3", "No4", "No5", "No6", "No7", "No8", "No9"];*/

       vm.users = [
       					{
						 "name" : "No1",
						 "city" : "City1",
						 "postcode" : "PC1",
						 "address" : "ADD1",
						 "rating" : "1",
						 "url" : "" },
						{
						 "name" : "No2",
						 "city" : "City2",
						 "postcode" : "PC2",
						 "address" : "ADD2",
						 "rating" : "2",
						 "url" : "" },
						{
						 "name" : "No3",
						 "city" : "City3",
						 "postcode" : "PC3",
						 "address" : "ADD3",
						 "rating" : "3",
						 "url" : "" },
						{
						 "name" : "No4",
						 "city" : "City4",
						 "postcode" : "PC4",
						 "address" : "ADD4",
						 "rating" : "4",
						 "url" : "" },
						{
						 "name" : "No5",
						 "city" : "City5",
						 "postcode" : "PC5",
						 "address" : "ADD5",
						 "rating" : "5",
						 "url" : "" },
						{
						 "name" : "No6", 
						 "city" : "City6",
						 "postcode" : "PC6",
						 "address" : "ADD6",
						 "rating" : "1",
						 "url" : "" },
						{
						 "name" : "No7",
						 "city" : "City7",
						 "postcode" : "PC7",
						 "address" : "ADD7",
						 "rating" : "2",
						 "url" : "" } ];

    }
})();