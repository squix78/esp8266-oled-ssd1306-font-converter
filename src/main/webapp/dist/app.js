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
;
angular.module('FontConverter').controller('MainController', 
	function($scope, $rootScope, $resource, $location, $routeParams, FontFamilies, Fonts, FontArray) {
	  $scope.styles = {
	                   "0": {name:"Plain"},
	                   "1": {name:"Bold"},
	                   "2": {name:"Italic"},
	                   "3": {name:"Bold & Italic"}
	  };
	  $scope.fontArray = new FontArray();
	  $scope.fontArray.name = "Dialog";
	  $scope.fontArray.style = "0";
	  $scope.fontArray.size = 16;
	  
	  $scope.fontFamilies = FontFamilies.query();
	  $scope.fonts = Fonts.query({fontFamily: $scope.fontArray.fontFamily});
	  console.log("Arrived in font converter: " + $scope.fontArray);
	  $scope.getFontArray = function() {
		 $scope.fontArray.$save(function() {
			 console.log($scope.fontArray.fontArray);
		 });
	  };
  });