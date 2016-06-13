<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
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
.toggle-button {
	background-color: white;
	margin: 5px 0;
	border-radius: 20px;
	border: 2px solid #D0D0D0;
	height: 24px;
	cursor: pointer;
	width: 50px;
	position: relative;
	display: inline-block;
	user-select: none;
	-webkit-user-select: none;
	-ms-user-select: none;
	-moz-user-select: none;
}

.toggle-button button {
	cursor: pointer;
	outline: 0;
	display: block;
	position: absolute;
	left: 0;
	top: 0;
	border-radius: 100%;
	width: 30px;
	height: 30px;
	background-color: white;
	float: left;
	margin: -5px 0 0 -5px;
	border: 2px solid #D0D0D0;
	transition: left 0.3s;
}

.toggle-button-selected {
	background-color: #83B152;
	border: 2px solid #7DA652;
}

.toggle-button-selected button {
	left: 26px;
	top: 0;
	margin: -2px 0 0 -2px;
	border: none;
	width: 24px;
	height: 24px;
	box-shadow: 0 0 8px rgba(0, 0, 0, 0.1);
}
</style>

<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.2.2.min.js">
	
</script>
<script type="text/javascript">
	$(function() {

		var ws = new WebSocket("ws://192.168.8.53:8080/FoodField/chat");/*?${_csrf.parameterName}=${_csrf.token} */

		ws.onopen = function() {
			$('#chatStatus').text('');
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

					if (entire == 'off') {

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
						usrstr += "<span class='"+userlist[i]+"'><b>"
								+ userlist[i] + "</b></span><br>";
					} else {
						usrstr += "<input type='checkbox' class='"+userlist[i]+"' value='"+userlist[i]+"'>"
								+ "<span class='"+userlist[i]+"'>"
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
			$('#chatStatus').text('로그인 해주세요');
		};

		/* chat */
		$("#chatbtn").click(function() {

			$("#chat").toggle("slow");

		});

		$(document).on('click', '.toggle-button', function() {
			$(this).toggleClass('toggle-button-selected');
			$(".toggle-button #entireMsgOption").val('off');
			$(".toggle-button-selected #entireMsgOption").val('on');
		});

		$("#userlistbtn").click(function() {
			$("#userlist").toggle("slow");
		});

	});
</script>

<div id="chatArea"
	style="position: fixed; right: 5px; bottom: 5px; z-index: 1000;">
 <div id="cbnt" style="margin: 0px;">
      <button type="button" id="chatbtn" class="btn btn-warning btn-circle" style="margin: 5px;">
         <span class="glyphicon glyphicon-comment"></span>
      </button>
   </div>

	<div id="chat"
		style="width: 400px; height: 400px; background-color: #f1f2f2; display: none; border: 3px solid white; -moz-border-radius: 15px; -webkit-border-radius: 15px; -o-border-radius: 15px;">
		<br>
		<div id='chatStatus'></div>
		<!-- 로그인 여부 출력  -->
		<div id="userlistArea">
			<div>
				<button type="button" id="userlistbtn"
					style="margin: 0px; width: 100%; background-color: white; border: 0px;">
					<span class="glyphicon glyphicon-menu-down"></span>
				</button>
			</div>
			<div id="userlist" style="dispay: none; background-color: white;">
				<label><h4>User List</h4></label> 전체메세지
				<div class="toggle-button" style="top: 10px;">
					<input type="hidden" id="entireMsgOption" type="text" value="off"/>
					<button></button>
				</div>
				<div id="users" style="overflow-y:scroll; height:60px;"></div>
				<!-- 리스트로 사람들 뜨기 -->
				<!-- <div style="background-color:#f1f2f2;"></div> -->
			</div>
		</div>

		<textarea name="chatMsg" rows="5" cols="40"
			style="width: 100%; border: 0px; padding: 4px; resize: none;"
			readonly></textarea>
		<p>
		<div class="input-group" style="width: 90%; margin: auto;">
			<span class="input-group-addon input-sm"
				style="background-color: #d1d2d4;">message</span> <input type="text"
				name="chatInput" class="form-control input-sm">
		</div>
	</div>
</div>


