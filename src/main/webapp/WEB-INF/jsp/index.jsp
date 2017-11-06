<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru">
<head>
<title>Java Spark Calculations</title>
<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.css">
<c:url value="/css/main.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet" />
</head>
<body ng-app="sparkCalc" class="ng-cloak">

	<jsp:include page="menu.jsp" />


	<div class="container">

		<div class="row starter-template">
			<div class="col-md-12">
				<h1>Вычисление функций с помощью фреймворка Apache Spark</h1>
			</div>
		</div>

		<div ng-controller="CalcFunctionController as ctrl">

			<div ng-if="ctrl.isLoading" class="loading">
				<md-progress-circular md-mode="indeterminate"></md-progress-circular>
			</div>
			
			<div ng-if="!ctrl.isLoading">
				<div class="row textDescription">
					<div class="col-md-12">
						<p>Пожалуйста, укажите параметры функции</p>
					</div>
				</div>
			
				<div ng-if="ctrl.response.errors.length" class="alert alert-danger">
					<p>При вычислении функции возникла ошибка:</p>
					<ul ng-repeat="e in ctrl.response.errors">
						<li ng-bind="e"></li>
					</ul>
				</div>
				<div ng-if="ctrl.response.data" class="alert alert-success">
					<span>Расчет матрицы выполнен успешно</span>
					<!-- <ul ng-repeat="(key, value) in ctrl.response.data">
						<li>{{key}}: {{value}}</li>
					</ul> -->
				</div>
				<form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
					<div class="row">
						<div class="form-group col-md-12">
							<label class="col-md-2 control-lable" for="funcNum">Тип функции</label>
							<div class="col-md-8">
								<select class="form-control" name="multipleSelect" id="funcNum" ng-model="ctrl.functionNum" required>
							      <option value="1">Аналитическая функция</option>
							      <option value="2">Интегральная функция</option>
							      <option value="3">Рекурентная функция</option>
							    </select>
							    <div class="has-error" ng-show="myForm.$dirty">
				                	<span ng-show="myForm.funcNum.$error.required">Это поле является обязательным</span>
									<span ng-show="myForm.funcNum.$invalid">Ошибка в заполнении поля</span>
								</div>
							</div>
						</div>
					</div>
		
					<div class="row">
						<div class="form-group col-md-12">
			            	<label class="col-md-2 control-lable" for="k">Размерность матрицы</label>
				            <div class="col-md-4">
					            <input type="number" ng-model="ctrl.n" id="n" placeholder="Строки" class="form-control input-sm" required ng-maxlength="6" required />
					            <div class="has-error" ng-show="myForm.$dirty">
					              	<span ng-show="myForm.n.$error.required">Это поле является обязательным</span>
					               	<span ng-show="myForm.n.$error.maxlength">Превышен максимальный допустимый порог</span>
									<span ng-show="myForm.n.$invalid">Ошибка в заполнении поля</span>
								</div>
					        </div>
				            <div class="col-md-4">
					            <input type="number" ng-model="ctrl.k" id="k" placeholder="Столбцы" class="form-control input-sm" required ng-maxlength="6" required />
					            <div class="has-error" ng-show="myForm.$dirty">
					               	<span ng-show="myForm.k.$error.required">Это поле является обязательным</span>
					               	<span ng-show="myForm.k.$error.maxlength">Превышен максимальный допустимый порог</span>
									<span ng-show="myForm.k.$invalid">Ошибка в заполнении поля</span>
								</div>
					        </div>
						 </div>
					</div>
		 
					<div class="row">
						<div class="form-group col-md-12">
		                	<label class="col-md-2 control-lable" for="alpha">&alpha;</label>
		                    <div class="col-md-8">
		                    	<input type="number"  step="0.00001" ng-model="ctrl.alpha" id="alpha" class="form-control input-sm" required/>
		                    	<div class="has-error" ng-show="myForm.$dirty">
				                	<span ng-show="myForm.alpha.$error.required">Это поле является обязательным</span>
									<span ng-show="myForm.alpha.$invalid">Ошибка в заполнении поля</span>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group col-md-12">
		                	<label class="col-md-2 control-lable" for="gamma">&gamma;</label>
		                    <div class="col-md-8">
		                    	<input type="number" step="0.00001" ng-model="ctrl.gamma" id="gamma" class="form-control input-sm"/>
							</div>
						</div>
					</div>
					<div class="row" style="margin-bottom:15px;">
						<div class="form-group col-md-12">
							<div class="col-md-2 checkbox">
							    <label>
							      <input id="isTestable" name="isTestable" ng-model="ctrl.isTestable" type="checkbox"> Провести тестирование
							    </label>
							</div> 
		                    <div class="col-md-8">
		                    	<input type="text" step="0.00001" placeholder="Список тестов" ng-disabled="!ctrl.isTestable" ng-model="ctrl.testList" id="testList" class="form-control input-sm"/>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-actions floatRight">
							<input type="submit"  value="Вычислить" class="btn btn-primary btn-sm" ng-disabled="myForm.$invalid">
							<button type="button" ng-click="ctrl.reset()" class="btn btn-warning btn-sm" ng-disabled="myForm.$pristine">Сбросить коэффициенты</button>
						</div>
					 </div>
				</form>
			</div>
		</div>

	</div>
	<!-- /.container -->
	
	

	<script type="text/javascript" src="/webjars/jquery/3.2.1/jquery.min.js"/>
	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-animate.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-aria.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.5/angular-messages.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.0/angular-material.min.js"></script>
	<script src="<c:url value='/scripts/app.js' />"></script>
    <script src="<c:url value='/scripts/service/function_service.js' />"></script>
    <script src="<c:url value='/scripts/controller/function_controller.js' />"></script>
    <script src="<c:url value='/scripts/controller/dialog_controller.js' />"></script>
	<script type="text/ng-template" id="tabDialog.html">
<md-dialog>
  <form>
    <md-toolbar aria-label="Справка">
      <div class="md-toolbar-tools">
        <h2>Справка</h2>
        <span flex></span>
        <md-button class="md-icon-button" ng-click="cancel()">
          <!-- <md-icon md-svg-src="img/icons/ic_close_24px.svg" aria-label="Закрыть диалог"></md-icon> -->
          <span class="glyphicon glyphicon-remove-sign"></span>
        </md-button>
      </div>
    </md-toolbar>
    <md-dialog-content style="max-width:800px;max-height:810px; ">
      <md-tabs md-dynamic-height md-border-bottom>
        <md-tab label="О программе">
          <md-content class="md-padding">
            <h1 class="md-display-2">О программе</h1>
            <p>Дизайн поправить надо, конечно.</p>
            <p>Кто-нибудь может выпилить этот темплейт из index.jsp? У меня чет ничего не выходит. Я днище.</p>
            <p>Еще по какой-то причине непонятной получается так, что при загрузке диалогового окна на нем отображается только две вкладки, а если потом открыть/закрыть консольку, появляется и третья. Кто-нить может посмотреть, почему так происходит?</p>
            <p>А вообще здесь можно было бы вывести задание, например, наверное.</p>
            <p>Типа, в приложении производится вычисление функций Якоби с использованием фреймворка Apache Spark для распараллеливания процесса обработки данных.</p>
            <p>Ну и типа работу выполнили:</p>
          </md-content>
        </md-tab>
        <md-tab label="Данные">
          <md-content class="md-padding">
            <h1 class="md-display-2">Данные</h1>
            <p>В приложении считается то-то, то-то. Вот у нас тут три типа функций. Еще всякие параметры. Их ввести надо, и все будет хорошо.</p>
            <p>Вот тут итерации - сомнительная переменная, но в формуле вроде как присутствует; альфа, гамма - это вообще просто параметры; Еще есть c и тау, но c всегда равно двум, а тау вроде как считать тоже надо.</p>
          </md-content>
        </md-tab>
        <md-tab label="Графики">
          <md-content class="md-padding">
            <h1 class="md-display-2">Графики</h1>
            <p>В приложении выводятся графики нагрузки. Они должны быть красивые, а может и не очень красивые. Кто их знает?</p>
          </md-content>
        </md-tab>
        <md-tab label="Тестирование">
          <md-content class="md-padding">
            <h1 class="md-display-2">Тестирование</h1>
            <p>В приложении проводится нагрузочное тестирование. тарам парарам.</p>
          </md-content>
        </md-tab>
        <md-tab></md-tab>
      </md-tabs>
    </md-dialog-content>

    <md-dialog-actions layout="row">
      <span flex></span>
      <md-button ng-click="answer('OK')" style="margin-right:20px;" >
        OK
      </md-button>
    </md-dialog-actions>
  </form>
</md-dialog>
	</script>
</body>

</html>
