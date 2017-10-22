'use strict';
 
angular.module('sparkCalc').controller('CalcFunctionController', ['$scope', 'CalcFunctionService', function($scope, CalcFunctionService) {
    var self = this;
    
    self.functionNum = null;
    self.iterations = null;
    self.alpha = null;
    self.gamma = null;
    self.isLoading = false;
    self.response={};
 
    self.submit = submit;
    self.reset = reset;
    
    function getFunctionResult() {
    	CalcFunctionService.getFunctionResult(self.functionNum, self.iterations, self.alpha, self.gamma)
    	.then(
    	function(d) {
            console.log('Function execution complete');
    		self.response = d;
    		self.isLoading = false;
    		if (self.response.errors != null) {
    			for (var i = 0; i < self.response.errors.length; i++)
    				console.log('Ошибка вычисления функции: ' + self.response.errors[i]);
    		}
    	}
    	);
    }
    
    function submit() {
        console.log('Start getting function result');
        getFunctionResult();
        reset();
        self.isLoading = true;
    }
 
    function reset(){
    	self.functionNum = null;
        self.iterations = null;
        self.alpha = null;
        self.gamma = null;
        self.isLoading = false;
        self.response = {};
    }
 
}]);