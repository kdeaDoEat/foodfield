<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
@media(max-width:400px){
#chat{width:350px;}
}
@media(min-width:401px){
#chat{width:400px;}
}
#chat {
	display: none;
}
.btn-circle {
  width: 30px;
  height: 30px;
  text-align: center;
  padding: 6px 0;
  font-size: 12px;
  line-height: 1.428571429;
  border-radius: 15px;
}

</style>
<link href="https://gitcdn.github.io/bootstrap-toggle/2.2.2/css/bootstrap-toggle.min.css" rel="stylesheet">
<script src="https://gitcdn.github.io/bootstrap-toggle/2.2.2/js/bootstrap-toggle.min.js"></script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.2.2.min.js">
	
</script>
<script type="text/javascript">
	$(function() {
		if('${sessionScope.userInfo}' != ''){
			$("#msg").attr("placeholder","메시지를 입력하세요");
		}

		var ws = new WebSocket("ws://192.168.8.28:8088/FoodField/chat");/*?${_csrf.parameterName}=${_csrf.token} */

		ws.onopen = function() {
			//$('#chatStatus').text('');
			//getUsers();
			//채팅의 session과 thread를 이용해야 할듯
			var jsonmsg = {};
			jsonmsg.status = "userrequest";
			var jsonstr = JSON.stringify(jsonmsg);
			ws.send(jsonstr);

			$('input[name=chatInput]').on('keydown', function(evt) {
				if (evt.keyCode == 13) {

					var entire = $("#entireMsgOption").val();

					var msg = $('input[name=chatInput]').val();
					var userlist = [];

					if (!$("#togglebtn").is(":checked")) {

						$("input:checkbox:checked").each(function(index) {
							userlist[index] = $(this).val();
						});
						jsonmsg.msg = msg;
						
					} else {

						$("input:checkbox").each(function(index) {
							userlist[index] = $(this).val();
						});
						jsonmsg.msg = '[전체] ' + msg;
					}
					jsonmsg.status = "sendrequest";
					jsonmsg.recievers = userlist;

					var jsonstr = JSON.stringify(jsonmsg);
					ws.send(jsonstr);
					$('input[name=chatInput]').val('');
				}
			});
		};
		ws.onmessage = function(event) {

			var object = eval('(' + event.data + ')');
			var usrstr = '';
			if (object.status == "userrequest") {

				var userlist = object.recievers;
				for (var i = 0; i < userlist.length; i++) {

					if ("${sessionScope.userInfo.nickname}" == userlist[i]) {
						usrstr += "<span class='"+userlist[i]+"' style=\"margin-left:5%\"><b>"
								+ userlist[i] + "</b></span><br>";
					} else {
						usrstr += "<input type='checkbox' class='"+userlist[i]+"' value='"+userlist[i]+"' style=\"margin-left:5%\">"
								+ "<span class='"+userlist[i]+"' style=\"margin-left:10px;\">"
								+ userlist[i] + "</span><br>";
					}

				}
				$("#users").html(usrstr);

			} else if (object.status == "sendrequest") {

				$('textarea[name=chatMsg]').eq(0).append(object.msg + '\n');
				$('textarea[name=chatMsg]').focus();
				$('input[name=chatInput]').focus();
			}
		};
		ws.onclose = function(event) {
			//$('#chatStatus').text('로그인 해주세요');
			$("#msg").attr("placeholder","로그인 해주세요");
		};

		/* chat */
		$("#chatbtn").click(function() {

			$("#chat").toggle("slow");

		});

		$("#userlistbtn").click(function() {
			$("#userlist").toggle("slow");
		});

	});
</script>

<div>

<div id="chatArea"
	style="position: fixed; right: 5px; bottom: 5px; z-index: 1000;">
 <div id="cbnt" style="margin: 0px;">
      <button type="button" id="chatbtn" class="btn btn-warning btn-circle" style="margin: 5px;">
         <span class="glyphicon glyphicon-comment"></span>
      </button>
   </div>

	<div id="chat"
		style="background-color: #f1f2f2; display: none; border: 3px solid #F0AD4E; -moz-border-radius: 15px; -webkit-border-radius: 15px; -o-border-radius: 15px;">
		<!-- 로그인 여부 출력  -->
		<!-- <div id='chatStatus' style="text-align:center;"></div> -->
		<div id="userlistArea">
			<div id="userlistBtn">
				<button type="button" id="userlistbtn"
					style="outline:0px; margin: 0px; width: 100%; background-color: #F0AD4E; border: 0px; border-radius:10px 10px 0 0;">
					<a style="color:white;"><span class="glyphicon glyphicon-user"></span></a>
				</button>
			</div>
			<div id="userlist" style="dispay: none; background-color: #f1f2f2;">
				<label style="margin-left:2.5%;"><h4 style="color:#555555;">UserList</h4></label>
				<span style="float:right; margin-top:7px;">
                <input type="checkbox" id="togglebtn" data-toggle="toggle" data-on="전체" data-off="개인"  data-size="mini" data-width="105" data-height="20">
                </span>
                <!-- 경계선 -->
                <div style="margin-left:10px; margin-right:10px; height:2px; background-color:#d1d2d4;"></div>
				<div id="users" style="overflow-y:scroll; height:90px; width:100%"></div>
				<!-- 리스트로 사람들 뜨기 -->
				<!-- <div style="background-color:#f1f2f2;"></div> -->
			</div>
		</div>

		<textarea name="chatMsg" rows="10" cols="40"
			style="width: 100%; border: 0px; padding: 4px; resize: none;"
			readonly></textarea>
		<p>
		<div class="input-group" style="width: 90%; margin: auto; margin-bottom:10px; margin-top:10px;">
			<span class="input-group-addon input-sm"
				style="background-color: #d1d2d4;">message</span> 
				<input type="text" id="msg" name="chatInput" class="form-control input-sm" placeholder="메시지를 입력하세요">
		</div>
	</div>
</div>


