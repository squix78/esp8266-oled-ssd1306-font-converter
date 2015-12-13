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