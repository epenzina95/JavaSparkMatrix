<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="ru">
<head>

<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
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

		<div class="row">
			<div class="col-md-12">
				<p>Пожалуйста, укажите параметры функции</p>
			</div>
		</div>

		<div ng-controller="CalcFunctionController as ctrl">
			<div ng-if="ctrl.response.errors.length" class="alert alert-danger">
				<p>При вычислении функции возникла ошибка:</p>
				<ul ng-repeat="e in ctrl.response.errors">
					<li ng-bind="e"></li>
				</ul>
			</div>
			<form ng-submit="ctrl.submit()" name="myForm" class="form-horizontal">
				<div class="row">
					<div class="form-group col-md-12">
						<label class="col-md-2 control-lable" for="funcNum">Тип функции</label>
						<div class="col-md-7">
							<select class="form-control" name="multipleSelect" id="funcNum" ng-model="ctrl.functionNum" required>
						      <option value="1">Func 1</option>
						      <option value="2">Func 2</option>
						      <option value="3">Func 3</option>
						    </select>
						    <div class="has-error" ng-show="myForm.$dirty">
			                	<span ng-show="myForm.funcNum.$error.required">Это поле является обязательным</span>
								<span ng-show="myForm.funcNum.$invalid">Ошибка в заполнении поля</span>
							</div>
			               <!--  <input type="number"  class="form-control input-sm" id="funcNum" ng-model="ctrl.functionNum" required />
			                <div class="has-error" ng-show="myForm.$dirty">
			                	<span ng-show="myForm.funcNum.$error.required">Это поле является обязательным</span>
								<span ng-show="myForm.funcNum.$invalid">Ошибка в заполнении поля</span>
							</div> -->
						</div>
					</div>
				</div>
	
				<div class="row">
					<div class="form-group col-md-12">
		            	<label class="col-md-2 control-lable" for="iterations">Количество итераций</label>
		                <div class="col-md-7">
			                <input type="number" ng-model="ctrl.iterations" id="iterations" class="form-control input-sm" required ng-maxlength="6" required />
			                <div class="has-error" ng-show="myForm.$dirty">
			                	<span ng-show="myForm.iterations.$error.required">Это поле является обязательным</span>
			                	<span ng-show="myForm.iterations.$error.maxlength">Превышен максимальный допустимый порог</span>
								<span ng-show="myForm.iterations.$invalid">Ошибка в заполнении поля</span>
							</div>
			            </div>
					 </div>
				</div>
	 
				<div class="row">
					<div class="form-group col-md-12">
	                	<label class="col-md-2 control-lable" for="alpha">Alpha</label>
	                    <div class="col-md-7">
	                    	<input type="number" ng-model="ctrl.alpha" id="alpha" class="form-control input-sm" required/>
	                    	<div class="has-error" ng-show="myForm.$dirty">
			                	<span ng-show="myForm.alpha.$error.required">Это поле является обязательным</span>
								<span ng-show="myForm.alpha.$invalid">Ошибка в заполнении поля</span>
							</div>
						</div>
					</div>
				</div>
	 
				<div class="row"> <!-- ng-show="(funcNum.value == 2)"> -->
					<div class="form-group col-md-12">
	                	<label class="col-md-2 control-lable" for="fi">Fi</label>
	                    <div class="col-md-7">
	                    	<input type="number" ng-model="ctrl.fi" id="fi" class="form-control input-sm"/>
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
	<!-- /.container -->

	<script type="text/javascript" src="/webjars/jquery/3.2.1/jquery.min.js"/>
	<script type="text/javascript" src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
	<script src="<c:url value='/scripts/app.js' />"></script>
    <script src="<c:url value='/scripts/service/function_service.js' />"></script>
    <script src="<c:url value='/scripts/controller/function_controller.js' />"></script>
</body>

</html>
