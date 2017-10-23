<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-inverse"  ng-controller="myDialogController">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Spark Calc</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <li><a ng-click="showTabDialog($event)" href="#">Справка</a></li>
            </ul>
        </div>
    </div>
</nav>