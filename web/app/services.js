/**
 * Created by samarth on 05/03/15.
 */
var app = app || angular.module('chatApp');
app.service("ChatService",function($q,$timeout){
    var service = {}, listener = $q.defer(), socket = {
        client: null,
        stomp:null
        },messageIds = [];

    service.RECONNECT_TIMEOUT = 30000;
    service.SOCKET_URL = "/notification";
    service.CHAT_TOPIC = "/topic/message";
    service.CHAT_BROKER = "/testApp/notification";

    service.receive = function(){
        return listener.promise;
    };

    service.send = function(message){
        var id = Math.floor(Math.random() * 1000000);
        socket.stomp.send(service.CHAT_BROKER,{
            priority:9
        }, JSON.stringify({
            message : message,
            id:id
        }));
        messageIds.push(""+id);
    };
    var reconnect = function(){
        $timeout(function(){
            initialize();
        },this.RECONNECT_TIMEOUT);
    };

    var getMessage = function(data){
        var message = JSON.parse(data), out = {};
        out.message = message.message;
        out.time = new Date(message.time);
        console.log("here",messageIds,message.id);
        var indexFound = messageIds.indexOf(message.id);
        console.log("INDEX",indexFound);
        if(indexFound>-1){
            out.self = true;
            console.log(indexFound);
            messageIds.splice(indexFound,1);
        }
        return out;
    };

    var startListener = function(){
        socket.stomp.subscribe(service.CHAT_TOPIC,function(data){
            console.log("herhe",data.body);
            listener.notify(getMessage(data.body));
        });
    };

    var initialize = function(){
        socket.client = new SockJS(service.SOCKET_URL);
        socket.stomp = Stomp.over(socket.client);
        socket.stomp.connect({},startListener);
        // socket.stomp.onclose = reconnect();
    };

    initialize();
    return service;
});
