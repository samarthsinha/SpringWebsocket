/**
 * Created by samarth on 05/03/15.
 */
var app = app || angular.module('chatApp');
app.service("ChatService",function($q,$sce,$timeout){
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

    service.stringContainingUrl = function(message){
        var regexpUrl =  /\b(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?\b/i;
        return regexpUrl.test(message);
    };

    service.transformUrlToLink = function(message){
        // var regex = /(\b(https?|ftp):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gim;
        // message = message.replace(regex,'<a href="$1" target="_blank">$1</a>');
        return $sce.trustAsHtml(message);
    };

    service._send = function(message,topic){
        var id = Math.floor(Math.random() * 1000000);
        socket.stomp.send(topic,{
            priority:9
        }, JSON.stringify({
                              message : message,
                              id:id,
                              name:service.user
                          }));
        messageIds.push(""+id);
    };
    service.send = function(message){
        service._send(message,service.CHAT_BROKER);
    };

    service.sendHandShake = function(user){
        var id = Math.floor(Math.random() * 1000000);
        socket.stomp.send(service.CHAT_BROKER,{
            priority:9
        }, JSON.stringify({
                              id:id,
                              name:service.user,
                              type:"CONNECTED"
                          }));
        messageIds.push(""+id);
    };

    service.sendToUser = function (message, toUser) {
        service._send(message,"/testApp/sendto/"+toUser);
        console.log("Sending to user on topic: " + "/testApp/sendto/"+toUser);
    };

    var reconnect = function(){
        $timeout(function(){
            initialize();
        },this.RECONNECT_TIMEOUT);
    };

    var getMessage = function(data){
        var message = JSON.parse(data), out = {};
        message.message =service.transformUrlToLink(message.message)
        // if(message.message =service.transformUrlToLink(message.message)){
        //     console.log("message contains url",message.message);
        // }
        out.message = message.message;
        out.time = new Date(message.time);
        out.name = message.name;
        out.type = message.type;
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
        // console.log("subscribed to : "+"/topic/reply/"+service.user);
        socket.stomp.subscribe("/topic/reply/"+service.user,function(data){
            console.log("User Message",data.body);
            listener.notify(getMessage(data.body));
        });
        service.sendHandShake(service.user);
    };

    var initialize = function(){
        socket.client = new SockJS(service.SOCKET_URL);
        socket.stomp = Stomp.over(socket.client);
        socket.stomp.connect({login:service.user},startListener);
        // socket.stomp.onclose = reconnect();
    };

    service.user = "";
    service.init = function(userName){
        service.user = userName;
        initialize();
    };

    return service;
});
