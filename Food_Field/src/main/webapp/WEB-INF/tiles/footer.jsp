<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.2.2.min.js">
	
</script>
<script type="text/javascript">
	$(function() {
		var ws = new WebSocket(
				"ws://192.168.8.53:8080/FoodField/chat?${_csrf.parameterName}=${_csrf.token}");

		ws.onopen = function() {
			$('#chatStatus').text('Info: connection opened.');
			//getUsers();
			//채팅의 session과 thread를 이용해야 할듯
			ws.send("/userrequest");
			$('input[name=chatInput]').on('keydown', function(evt) {
				if (evt.keyCode == 13) {

					var msg = $('input[name=chatInput]').val();
					var sustr = "";

					$("input:checkbox:checked").each(function(index) {
						sustr += $(this).val() + "/";
					});
					//alert(sustr);
					ws.send(sustr + msg);
					$('input[name=chatInput]').val('');
				}
			});
		};
		ws.onmessage = function(event) {
			var resultarray = event.data.split("/");
			var usrstr = "";
			if (resultarray[1] == "userrequest") {

				for (var i = 2; i < resultarray.length - 1; i++) {

					usrstr += "<input type='checkbox' class='"+resultarray[i]+"' value='"+resultarray[i]+"'>"
							+ "<span class='"+resultarray[i]+"'>"
							+ resultarray[i] + "</span><br>";

				}
				$("#users").html(usrstr);

			} else {

				$('textarea').eq(0).prepend(event.data + '\n');

			}
		};
		ws.onclose = function(event) {
			$('#chatStatus').text('Info: connection closed.');
		};

		var canvas = document.getElementById("myCanvas");
		var ctx = canvas.getContext("2d");

	});

	function getMouseEventTarget(e) {

		var targ;
		if (!e) {
			var e = window.event;
		}
		if (e.target) {
			targ = e.target;
		} else if (e.srcElement) {
			targ = e.srcElement;
		}
		var tname;
		tname = targ.tagName;
/* 		alert("event발생한 target의 태그이름은 " + tname + "입니다. 좌표는 " + e.clientX
				+ ", " + e.clientY); */

	}

</script>
<footer onmousedown="getMouseEventTarget(event)" id="fh5co-footer">
	<div class="container">
		<div class="row fh5co-row-padded fh5co-copyright">
			<div class="col-md-5">
				<p>
					<small>&copy; Booster Free HTML5 Template. All Rights
						Reserved. <br>Designed by: <a href="http://freehtml5.co/"
						target="_blank">FREEHTML5.co</a> | Images by: <a
						href="http://deathtothestockphoto.com/" target="_blank">DeathToTheStockPhoto</a>
					</small>
				</p>
			</div>
		</div>
	</div>
	
	<div id='chatStatus'></div>
	<textarea name="chatMsg" rows="5" cols="40"></textarea>
	<p>
		메시지 입력 : <input type="text" name="chatInput">
	<div>
		유저리스트 :
		<div id="users"></div>
	</div>
</footer>