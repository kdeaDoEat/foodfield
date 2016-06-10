<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Booster &mdash; A free HTML5 Template by FREEHTML5.CO</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Free HTML5 Template by FREEHTML5.CO" />
<meta name="keywords"
	content="free html5, free template, free bootstrap, html5, css3, mobile first, responsive" />
<meta name="author" content="FREEHTML5.CO" />

<!-- Facebook and Twitter integration -->
<meta property="og:title" content="" />
<meta property="og:image" content="" />
<meta property="og:url" content="" />
<meta property="og:site_name" content="" />
<meta property="og:description" content="" />
<meta name="twitter:title" content="" />
<meta name="twitter:image" content="" />
<meta name="twitter:url" content="" />
<meta name="twitter:card" content="" />

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
<link rel="shortcut icon" href="favicon.ico">

<!-- Google Webfonts -->
<link
	href='http://fonts.googleapis.com/css?family=Roboto:400,300,100,500'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Montserrat:400,700'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/earlyaccess/jejugothic.css'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/earlyaccess/notosanskr.css'
	rel='stylesheet' type='text/css'>

<!-- Animate.css -->
<link rel="stylesheet"
	href="/FoodField/resources/bootstrap/css/animate.css">
<!-- Icomoon Icon Fonts-->
<link rel="stylesheet"
	href="/FoodField/resources/bootstrap/css/icomoon.css">
<!-- Owl Carousel -->
<link rel="stylesheet"
	href="/FoodField/resources/bootstrap/css/owl.carousel.min.css">
<link rel="stylesheet"
	href="/FoodField/resources/bootstrap/css/owl.theme.default.min.css">
<!-- Magnific Popup -->
<link rel="stylesheet"
	href="/FoodField/resources/bootstrap/css/magnific-popup.css">
<!-- Theme Style -->
<link rel="stylesheet"
	href="/FoodField/resources/bootstrap/css/style.css">
<!-- Modernizr JS -->
<script src="/FoodField/resources/bootstrap/js/modernizr-2.6.2.min.js"></script>
<!-- FOR IE9 below -->

<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.2.2.min.js">
	
</script>
<script type="text/javascript">
	$(function() {
		
		var ws = new WebSocket(
				"ws://192.168.8.53:8080/FoodField/chat?${_csrf.parameterName}=${_csrf.token}");

		ws.onopen = function() {
			$('#chatStatus').text('');
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
			$('#chatStatus').text('로그인 해주세요');
		};
		
		/* chat */
		$("#chatbtn").click(function() {
			//$("#chat").toggleClass("newClass", 1000);
			
			$("#chat").toggle("slow");
		});

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

<style>
 
  #chat{
  
     display:none;
  
  }
 
</style>

</head>
<body>

	<!-- Chat Button -->
	<script type="text/javascript">
		
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

	<tiles:insertAttribute name="header" />
	<tiles:insertAttribute name="main_slider" />
	<tiles:insertAttribute name="main_body" />
	<tiles:insertAttribute name="footer" />

	<!-- jQuery -->
	<script src="/FoodField/resources/bootstrap/js/jquery.min.js"></script>
	<!-- jQuery Easing -->
	<script src="/FoodField/resources/bootstrap/js/jquery.easing.1.3.js"></script>
	<!-- Bootstrap -->
	<script src="/FoodField/resources/bootstrap/js/bootstrap.min.js"></script>
	<!-- Owl carousel -->
	<script src="/FoodField/resources/bootstrap/js/owl.carousel.min.js"></script>
	<!-- Waypoints -->
	<script src="/FoodField/resources/bootstrap/js/jquery.waypoints.min.js"></script>
	<!-- Magnific Popup -->
	<script
		src="/FoodField/resources/bootstrap/js/jquery.magnific-popup.min.js"></script>
	<!-- Main JS -->
	<script src="/FoodField/resources/bootstrap/js/main.js"></script>


</body>
</html>
