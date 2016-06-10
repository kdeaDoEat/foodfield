<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<style>
 
  #chat{
  
     display:none;
  
  }
 
</style>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.2.2.min.js">
	
</script>
<script type="text/javascript">
	$(function() {
		
		var ws = new WebSocket(
				"ws://192.168.8.53:8080/FoodField/chat");/*?${_csrf.parameterName}=${_csrf.token} */

		ws.onopen = function() {
			$('#chatStatus').text('');
			//getUsers();
			//채팅의 session과 thread를 이용해야 할듯
			var jsonmsg = {};
			jsonmsg.status="userrequest";
			var jsonstr = JSON.stringify(jsonmsg);
			ws.send(jsonstr);
			
			$('input[name=chatInput]').on('keydown', function(evt) {
				if (evt.keyCode == 13) {

					var msg = $('input[name=chatInput]').val();
					var userlist = [];

					$("input:checkbox:checked").each(function(index) {
						userlist[index] = $(this).val();
					});
					jsonmsg.status="sendrequest";
					jsonmsg.recievers=userlist;
					jsonmsg.msg=msg;
					
					var jsonstr = JSON.stringify(jsonmsg);					
					ws.send(jsonstr);
					$('input[name=chatInput]').val('');
				}
			});
		};
		ws.onmessage = function(event) {

            var object = eval('('+event.data+')');
			var usrstr = '';
			if (object.status == "userrequest") {
                var userlist = object.recievers;
				for (var i = 0; i < userlist.length; i++) {

					usrstr += "<input type='checkbox' class='"+userlist[i]+"' value='"+userlist[i]+"'>"
							+ "<span class='"+userlist[i]+"'>"
							+ userlist[i] + "</span><br>";

				}
				$("#users").html(usrstr);

			} else if(object.status == "sendrequest"){

				$('textarea').eq(0).prepend(object.msg + '\n');

			}
		};
		ws.onclose = function(event) {
			$('#chatStatus').text('로그인 해주세요');
		};
		
		/* chat */
		$("#chatbtn").click(function() {
			//$("#chat").toggleClass("newClass", 1000);
			
			$("#chat").toggle("slow");
		});

	});

	
</script>

	<div id="chatArea"
		style="position: fixed; right: 20px; bottom: 20px; z-index: 1000;">
		<div id="cbnt">
			<button type="button" id="chatbtn" style="width: 100px;">채팅하기</button>
		</div>
		<div id="chat"
			style="width: 400px; height: 400px; background-color: skyblue; display:none;">

			<div id='chatStatus'></div>
			<textarea name="chatMsg" rows="5" cols="40" style="width: 100%; border: 1px solid #333; padding: 4px;"></textarea>
			<p>
				메시지 입력 : <input type="text" name="chatInput">
			<div>
				유저리스트 :
				<div id="users"></div>
			</div>

		</div>
	</div>
