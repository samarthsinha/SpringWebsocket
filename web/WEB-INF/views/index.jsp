<%--
  Created by IntelliJ IDEA.
  User: samarth
  Date: 04/03/15
  Time: 6:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
  <head>
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700" rel="stylesheet" type="text/css" />
    <title></title>
  </head>
  <body ng-app="chatApp">
  <div ng-controller="ChatCtrl" class="container" data-id="${pageContext.session.id}">
      <form ng-submit="addMessage()" name="messageForm">
          <input type="text" placeholder="Compose a new message..." ng-model="message" name="message"/>
          <div class="info">
              <span class="count" ng-bind="max - message.length" ng-class="{danger: message.length > max}">140</span>
              <button ng-disabled="message.length > max || message.length === 0" class="formSend">Send</button>
          </div>
      </form>
      <hr/>
      <p ng-repeat="message in messages | orderBy:'time':true" class="message">
          <time>{{message.time | date:'HH:mm'}}</time>
          <span ng-class="{self: message.self}">{{message.message}}</span>
      </p>
  </div>
  <script src="libs/sockjs-0.3.4.min.js" type="text/javascript"></script>
  <script src="libs/stomp.min.js" type="text/javascript"></script>
  <script src="libs/angular.min.js" type="text/javascript"></script>
  <script src="libs/lodash.min.js" type="text/javascript"></script>
  <script src="app/app.js" type="text/javascript"></script>
  <script src="app/controllers.js" type="text/javascript"></script>
  <script src="app/services.js" type="text/javascript"></script>
  </body>
</html>
