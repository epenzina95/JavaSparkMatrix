'use strict';
 
angular.module('sparkCalc').controller('CalcFunctionController', ['$scope', 'CalcFunctionService', function($scope, CalcFunctionService) {
    var self = this;
    
    self.functionNum = null;
    self.iterations = null;
    self.alpha = null;
    self.gamma = null;
    //self.response={errors:[], data: {}};
    self.response={};
 
    self.submit = submit;
    self.reset = reset;
    
    function getFunctionResult() {
    	CalcFunctionService.getFunctionResult(self.functionNum, self.iterations, self.alpha, self.gamma)
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
    
    function checkEmptyData() {
    	return Object.keys(self.response.data).length; 
    	//return self.response.data.idEmpty();
    }
    
    function submit() {
        console.log('Start getting function result');
        /*if (self.functionNum == 2 && !self.fi) {
    		self.response.errors.push("Не все поля заполнены");
    		return;
    	}*/
        getFunctionResult();
        reset();
    }
 
    function reset(){
    	self.functionNum = null;
        self.iterations = null;
        self.alpha = null;
        self.gamma = null;
        //self.response = {errors:[], data: {}};
        self.response = {};
        //$scope.myForm.$setPristine(); //reset Form
    }
 
}]);