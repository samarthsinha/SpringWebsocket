<!DOCTYPE HTML>
<html>
<head>
    <title></title>
</head>
<body>
<form method="post" action="/test">
    <input type="text" name="text">
    <input type="submit" value="Submit">
</form>
<div id="testBox">

</div>

<script src="libs/sockjs-0.3.4.min.js" type="text/javascript"></script>
<script>
    var sock = new SockJS('http://localhost:8080/hello');
    sock.onopen=function(){
        console.log()
    };
    sock.onmessage = function(message) {
        console.log('message', e.data);
        document.querySelector("#testBox").innerHTML = document.querySelector("#id").innerHTML + "<BR>" + e.data;
    }
</script>
</body>
</html>
