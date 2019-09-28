/**
 * Created by samarth on 05/03/15.
 */
var app = angular.module('chatApp', ['ngSanitize']);
var app = app || angular.module('chatApp');
app.controller("ChatCtrl",['$scope','ChatService',function($scope,ChatService){
   $scope.messages=[];
   $scope.message="";
    $scope.max=140;

    $scope.formsMethod = {
        addMessage : $scope.addMessage,
        setUser : $scope.setUser
    };
    $scope.addMessage = function(){
        if($scope.message.search(/^@(a-b)?/)>=0){
            var spaceInd = $scope.message.search(/\s/);
            if(spaceInd != -1){
                user = $scope.message.substring(1,spaceInd);
                $scope.sendToUser($scope.message,user);
            }
        }else{
            ChatService.send($scope.message);
        }
        $scope.message="";

    };

    $scope.sendToUser = function(message,user){
        ChatService.sendToUser(message,user);
    };

    $scope.userName = "";

    ChatService.receive().then(null,null,function(message){
        $scope.messages.push(message);
    });

    $scope.setUser = function(){
        $scope.setCookie("userName",$scope.userName);
    };

    $scope.init = function(){
        ChatService.init($scope.getCookie("userName"));
    };


    $scope.isEmpty = function(val){

            // test results
            //---------------
            // []        true, empty array
            // {}        true, empty object
            // null      true
            // undefined true
            // ""        true, empty string
            // ''        true, empty string
            // 0         false, number
            // true      false, boolean
            // false     false, boolean
            // Date      false
            // function  false

            if (val === undefined)
                return true;

            if (typeof (val) == 'function' || typeof (val) == 'number' || typeof (val) == 'boolean' || Object.prototype.toString.call(val) === '[object Date]')
                return false;

            if (val == null || val.length === 0)        // null or 0 length array
                return true;

            if (typeof (val) == "object") {
                // empty object

                var r = true;

                for (var f in val)
                    r = false;

                return r;
            }

            return false;
    };

    $scope.getCookie = function (cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i=0; i<ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1);
            if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
        }
        return "";
    };

    $scope.setCookie = function (cname, cvalue, exdays) {
            if(!exdays){
                exdays = 10;
            }
            var d = new Date();
            d.setTime(d.getTime() + (exdays*24*60*60*1000));
            var expires = "expires="+ d.toUTCString();
            document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
        }
}]);