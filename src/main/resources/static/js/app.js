angular.module('FontConverter', ['ngRoute', 'ngResource'])
.config(function($routeProvider) {
    $routeProvider
    .when('/home', {
    	controller : 'MainController',
    	templateUrl : 'partials/main.html'
    })
    .when('/xbm', {
    	controller : 'XbmController',
    	templateUrl : 'partials/xbm.html'
    })
    .otherwise({
       redirectTo: '/home'
    });
})
.factory('Ping', ['$resource', function($resource) {
    return $resource('/rest/ping');
}])
.factory('FontFamilies', ['$resource', function($resource) {
    return $resource('/rest/fontFamilies');
}])
.factory('Fonts', ['$resource', function($resource) {
    return $resource('/rest/fonts/:fontFamily');
}])
.factory('FontArray', ['$resource', function($resource) {
	return $resource('/rest/fontArray');
}])
.factory('XbmPreview', ['$resource', function($resource) {
	return $resource('/rest/xbmPreview');
}])
.filter('htmlEncode', function() {
  return window.encodeURIComponent;
});
;