'use strict';

angular.module('sparkCalc').controller('myDialogController', function($scope, $mdDialog) {
	  $scope.status = '  ';
	  $scope.customFullscreen = false;

	  $scope.showAlert = function(ev) {
		    $mdDialog.show(
		      $mdDialog.alert()
		        .parent(angular.element(document.querySelector('#popupContainer')))
		        .clickOutsideToClose(true)
		        .title('This is an alert title')
		        .textContent('You can specify some description text in here.')
		        .ariaLabel('Alert Dialog Demo')
		        .ok('Got it!')
		        .targetEvent(ev)
		    );
		  };
	  
	  $scope.showTabDialog = function(ev) {
	    $mdDialog.show({
	      controller: DialogController,
	      templateUrl: 'tabDialog.html',
	      parent: angular.element(document.body),
	      targetEvent: ev,
	      clickOutsideToClose:true
	    })
	        .then(function(answer) {
	          $scope.status = 'You said the information was "' + answer + '".';
	        }, function() {
	          $scope.status = 'You cancelled the dialog.';
	        });
	  };

	  function DialogController($scope, $mdDialog) {
	    $scope.hide = function() {
	      $mdDialog.hide();
	    };

	    $scope.cancel = function() {
	      $mdDialog.cancel();
	    };

	    $scope.answer = function(answer) {
	      $mdDialog.hide(answer);
	    };
	  }
	});