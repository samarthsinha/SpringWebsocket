/**
 * Created by samarth on 05/03/15.
 */
/*angular.module("chatApp",["chatApp.controllers","chatApp.services"]);
angular.module("chatApp.controllers",[]);
angular.module("chatApp.services",[]);*/

var socket = new SockJS('/notification');
var stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {
    console.info("connected")
//    stompClient.send("/app/notification", {}, {});
    stompClient.subscribe('/topic/message', function(response){
        console.info("Response: "+response.body);
        var resp = JSON.parse(response.body);
        var messageDom = document.querySelector(".message");
        var dateString = new Date(resp.time);
        var time = dateString.toDateString()+" | "+ dateString.toLocaleTimeString();
        messageDom.innerHTML = "<BR><span style='font-size:small; color:dodgerblue; padding: 5px; '>" +time + "</span><span style='color: darkgreen; font-weight: bold; padding:5px;'>"+  resp.message + "</span><BR>"+ messageDom.innerHTML ;
    });

});

document.querySelector(".formSend").addEventListener("click",function(event){
    event.preventDefault();
    var id = Math.floor(Math.random() * 1000000);
    stompClient.send("/testApp/notification",{
        priority:9
    }, JSON.stringify({
        message : document.querySelector("input[name='message']").value,
        id:id
    }));
    document.querySelector("input[name='message']").value='';
});
