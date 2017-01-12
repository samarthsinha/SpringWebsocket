<%--
  Created by IntelliJ IDEA.
  User: samarth
  Date: 04/03/15
  Time: 6:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en" ng-app="chatApp">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1">
    <link href="//fonts.googleapis.com/css?family=Open+Sans:400,300,600,700" rel="stylesheet"
          type="text/css"/>
    <link href="assets/style.css" rel="stylesheet" type="text/css"/>
    <title>Chat Room</title>
</head>
<body>
<header>
    ChatBooth
</header>
<div ng-controller="ChatCtrl" class="container" data-id="${pageContext.session.id}">

    <div class="message_box">
        <p ng-repeat="message1 in messages | orderBy:'time':false" class="message"
           ng-class="{m_self: message1.self}">
            <time class="timeBlock" ng-class="{m_self: message1.self}">{{message1.time |
                date:'M/d/yyyy HH:mm:ss'}}
            </time>
            <span class="m_block" ng-bind="message1.message"
                  ng-class="{m_self: message1.self}"></span>
        </p>
    </div>
    <div class="info">
        <span ng-bind="message.length"></span>/<span class="count"
                                                     ng-bind="{{max - message.length}}"
                                                     ng-class="{danger: message.length > max}"></span>
    </div>
    <form ng-submit="addMessage()" name="messageForm">
        <input type="submit" ng-disabled="message.length > max || message.length === 0"
               class="formSend" value="Send"></input>
        <div style="overflow: hidden;">
            <input type="text" placeholder="Compose a new message..." ng-model="message"
                   name="message" autocomplete="off"/>
        </div>
    </form>
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
