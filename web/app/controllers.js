/**
 * Created by samarth on 05/03/15.
 */
var app = angular.module('chatApp', []);
var app = app || angular.module('chatApp');
app.controller("ChatCtrl",function($scope,ChatService){
   $scope.messages=[];
   $scope.message="";
    $scope.max=140;
    $scope.addMessage = function(){
        ChatService.send($scope.message);
        $scope.message="";
    };

    ChatService.receive().then(null,null,function(message){
        $scope.messages.push(message);
    });
});