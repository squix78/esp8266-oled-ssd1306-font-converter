angular.module('FontConverter', ['ngRoute', 'ngResource'])
.config(function($routeProvider) {
    $routeProvider
    .when('/home', {
    	controller : 'MainController',
    	templateUrl : 'partials/main.html'
    })
    .otherwise({
       redirectTo: '/home'
    });
})
.factory('Ping', ['$resource', function($resource) {
    return $resource('/rest/ping');
}]);
angular.module('FontConverter')
  .controller('MainController', function($scope, $rootScope, $resource, $location, $routeParams, Ping) {
	  $scope.ping = Ping.get();
	  console.log("Arrived in font converter: " + $scope.ping);
  });