<!DOCTYPE HTML>
<html lang="en" ng-app="chatApp">
<head>
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0,width=device-width">
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
    <div class="modal-box" ng-if="isEmpty(getCookie('userName'))" ng-cloak>
        <form name="loginForm" novalidate ng-submit="loginForm.$valid && $parent.setUser()">
            <input type="submit" ng-disabled="!$parent.userName"
                   class="formSend" value="Enter"></input>
            <div style="overflow: hidden;">
                <input type="text" name="" ng-model="$parent.userName" placeholder="Enter your username (alphanumeric)" ng-pattern-restrict="^$|^[A-Za-z0-9]+"/>
            </div>
        </form>
    </div>

    <div ng-if="!isEmpty(getCookie('userName'))" ng-cloak>
        <div class="message_box" ng-cloak>
            <p ng-repeat="message1 in messages | orderBy:'time':false" class="message"
               ng-class="{m_self: message1.self,m_connect: message1.type=='CONNECTED'}">
                <time class="timeBlock" ng-class="{m_self: message1.self}" ng-hide="message1.type=='CONNECTED'">{{message1.time |
                    date:'M/d/yyyy HH:mm:ss'}}
                </time>
                <span class="user_detail" ng-hide="message1.self || message1.type=='CONNECTED'">{{message1.name}} says: </span>
                <span class="m_block" ng-bind="message1.message"
                      ng-bind-html='message1.message' ng-class="{m_self: message1.self}"></span>
            </p>
        </div>
        <div class="info">
            <span ng-bind="message.length"></span>/<span class="count"
                                                         ng-bind="{{max - message.length}}"
                                                         ng-class="{danger: message.length > max}"></span>
            <span class="hint">To send message to a specific user target message like '@&lt;username&gt; hey there' do not use @&lt;username&gt; before message to send to chat room</span>
        </div>
        <form ng-submit="$parent.addMessage()" name="messageForm" ng-init="init()">
            <input type="submit" ng-disabled="$parent.message.length >$parent.max || $parent.message.length === 0"
                   class="formSend" value="Send"></input>
            <div style="overflow: hidden;">
                <input type="text" placeholder="Compose a new message..." ng-model="$parent.message"
                       name="message" autocomplete="off"/>
            </div>
        </form>
    </div>
</div>

<script src="libs/sockjs-0.3.4.min.js" type="text/javascript"></script>
<script src="libs/stomp.min.js" type="text/javascript"></script>
<script src="libs/angular.min.js" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular-sanitize.min.js" type="text/javascript"></script>
<script src="libs/lodash.min.js" type="text/javascript"></script>
<script src="app/app.js" type="text/javascript"></script>
<script src="app/controllers.js" type="text/javascript"></script>
<script src="app/services.js" type="text/javascript"></script>
</body>
</html>
