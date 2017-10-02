'use strict';
 
angular.module('sparkCalc').controller('CalcFunctionController', ['$scope', 'CalcFunctionService', function($scope, CalcFunctionService) {
    var self = this;
    
    self.functionNum = null;
    self.iterations = null;
    self.alpha = null;
    self.fi = null;
    
    self.response={errors:[]};
 
    self.submit = submit;
    self.reset = reset;
    
    function getFunctionResult() {
    	CalcFunctionService.getFunctionResult(self.functionNum, self.iterations, self.alpha, self.fi)
    	.then(
    	function(d) {
            console.log('Function execution complete');
    		self.response = d;
    		if (self.response.errors != null) {
    			for (var i = 0; i < self.response.errors.length; i++)
    				console.log('Ошибка вычисления функции: ' + self.response.errors[i]);
    		}
    	}
    	);
    }
    
    function submit() {
        console.log('Start getting function result');
        if (self.functionNum == 2 && !self.fi) {
    		self.response.errors.push("Не все поля заполнены");
    		return;
    	}
        getFunctionResult();
        reset();
    }
 
    function reset(){
    	self.functionNum = null;
        self.iterations = null;
        self.alpha = null;
        self.fi = null;
        self.response = {errors:[]};
        //$scope.myForm.$setPristine(); //reset Form
    }
 
}]);