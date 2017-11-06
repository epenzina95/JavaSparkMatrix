'use strict';

angular.module('sparkCalc').factory('CalcFunctionService', ['$http', '$q', function($http, $q){
	 
    var REST_SERVICE_URI = 'http://localhost:8080/JavaSparkMatrixMult';
 
    var factory = {
    		getFunctionResult: getFunctionResult
    };
 
    return factory;
 
    function getFunctionResult(functionNum, k, n, alpha, gamma, isTestable, selectedTests) {
    	var request = [];
    	request.push($http.get(REST_SERVICE_URI + "/calc" + "?functionNum=" + functionNum + "&k=" + k + "&n=" + n + 
        		(alpha ? ("&alpha=" + alpha) : "") + (gamma ? "&gamma="+ gamma : "")))
        
        var deferred = $q.defer();
        if (isTestable)
        	for (var i=0; i < selectedTests.length; i++) {
        		request.push(
        				$http.post(REST_SERVICE_URI + '/test' + "?selectedTest=" + selectedTests[i])
        		);
        	}
        $q.all(request).then(function(response) {
        	deferred.resolve(response.data);
        })
        return deferred.promise;
    }
  
}]);