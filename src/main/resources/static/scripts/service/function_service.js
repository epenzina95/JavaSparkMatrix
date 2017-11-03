'use strict';

angular.module('sparkCalc').factory('CalcFunctionService', ['$http', '$q', function($http, $q){
	 
    var REST_SERVICE_URI = 'http://localhost:8080/JavaSparkMatrixMult/calc';
 
    var factory = {
    		getFunctionResult: getFunctionResult
    };
 
    return factory;
 
    function getFunctionResult(functionNum, k, n, alpha, gamma) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI + "?functionNum=" + functionNum + "&k=" + k + "&n=" + n + 
        		(alpha ? ("&alpha=" + alpha) : "") + (gamma ? "&gamma="+ gamma : ""))
            .then(
            function (response) {
                deferred.resolve(response.data);
            }
        );
        return deferred.promise;
    }
  
}]);