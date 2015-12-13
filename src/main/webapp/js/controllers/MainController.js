angular.module('FontConverter')
  .controller('MainController', function($scope, $rootScope, $resource, $location, $routeParams, Ping) {
	  $scope.ping = Ping.get();
	  console.log("Arrived in font converter: " + $scope.ping);
  });