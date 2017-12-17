'use strict';
 
angular.module('sparkCalc').controller('CalcFunctionController', ['$scope', 'CalcFunctionService', function($scope, CalcFunctionService) {
    var self = this;
    
    self.functionNum = null;
    self.k = null;
    self.n = null;
    self.alpha = null;
    self.gamma = null;
    self.isLoading = false;
    self.isTestable = false;
    self.response={};
 
    self.selectedTests = ['Транспонирование матрицы','Вычисление ранга матрицы'];
    self.allTests = ['Транспонирование матрицы','Вычисление ранга матрицы','Еще тест'];
    self.dropdownSettings = {
    	scrollable: true,
    	scrollableHeight: '200px'
    };
    
    self.submit = submit;
    self.reset = reset;
    
    function getFunctionResult() {
    	CalcFunctionService.getFunctionResult(self.functionNum, self.k, self.n, self.alpha, self.gamma, self.isTestable, self.selectedTests)
    	.then(
    	function(d) {
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
        
        if (self.gamma > 1 || self.gamma < 0) {
        	self.response.errors = [];
        	self.response.errors.push('Неверное значение параметра: gamma <= 1');
        	return;
        }
        if (self.n <= 0 || self.k <= 0) {
        	if (self.response.errors) self.response.errors.push('Неверное значение параметра: количество строк и столбцов должно быть положительным');
        	else {
            	self.response.errors = [];
            	self.response.errors.push('Неверное значение параметра: количество строк и столбцов должно быть положительным');
        	}
        	return;
        }
        getFunctionResult();
        reset();
        self.isLoading = true;
    }
 
    function reset(){
    	self.functionNum = null;
        self.k = null;
        self.n = null;
        self.alpha = null;
        self.gamma = null;
        self.isLoading = false;
        self.isTestable = false;
        self.response = {};
    }
 
}]);