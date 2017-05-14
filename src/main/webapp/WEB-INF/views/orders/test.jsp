<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>Test</title>
    <script src="http://www.xmtroika.com/ttmall/webjars/sockjs-client/1.0.0/sockjs.min.js"></script>
    <script src="http://www.xmtroika.com/ttmall/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
    <script src="http://www.xmtroika.com/ttmall/webjars/jquery/2.0.3/jquery.min.js"></script>
  </head>
  <body>
  	<p>
  	Try opening this app in two separate browsers.
  	Then post messages in each to see them sent to the other browser.
  	Try mentioning each user (@fred and @chuck) to see the user-targeted "You just got mentioned" message appear in the appropriate user's browser.
  	</p>
  
  	<form id="spittleForm">
  		<textarea rows="4" cols="60" name="text"></textarea>
  		<input type="submit"/>
  	</form>
  
    <script>
    
	$('#spittleForm').submit(function(e){
		e.preventDefault();
		var text = $('#spittleForm').find('textarea[name="text"]').val();
		sendSpittle(text);
	});
    
      var sock = new SockJS('/ttmall/spittr');
      var stomp = Stomp.over(sock);
      alert("2");
      stomp.connect('guest', 'guest', function(frame) {
        alert("*****  Connected  *****!!!!!!");
        stomp.subscribe("/topic/spittlefeed", handleSpittle);
        stomp.subscribe("/user/queue/notifications", handleNotification);
      });
      
      function handleSpittle(message) {
    	  console.log('Spittle:', message);
    	  $('#output').append("<b>Received spittle: " + JSON.parse(message.body).message + "</b><br/>");
      }
      
      function handleNotification(message) {
        console.log('Notification: ', message);
        $('#output').append("<b>Received: " + 
            JSON.parse(message.body).message + "</b><br/>")
      }
      
      function sendSpittle(text) {
        alert('Sending Spittle');
        stomp.send("/app/spittle", {}, 
            JSON.stringify({ 'text': text }));
      }

      $('#stop').click(function() {sock.close()});
      </script>
    
    <div id="output"></div>
  </body>
</html>
