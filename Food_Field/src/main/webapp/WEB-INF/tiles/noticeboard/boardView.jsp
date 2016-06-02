<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String cp = request.getContextPath();
%>
<%--ContextPath 선언 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=1024">
<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<link rel="stylesheet" type="text/css" media="screen"
	href="//d85wutc1n854v.cloudfront.net/live/css/screen_preview.css">
<!--<script src="//cdn.optimizely.com/js/233905874.js"></script>-->
<script type="text/javascript" async=""
	src="http://www.google-analytics.com/ga.js"></script>
<script src="//d85wutc1n854v.cloudfront.net/live/js/behavior.js"></script>
<script type="text/javascript">
	var _gaq = _gaq || [];
	_gaq.push([ '_setAccount', 'UA-28871117-1' ]);
	_gaq.push([ '_setDomainName', 'wrapbootstrap.com' ]);
	_gaq.push([ '_trackPageview' ]);
	(function() {
		var ga = document.createElement('script');
		ga.type = 'text/javascript';
		ga.async = true;
		ga.src = ('https:' == document.location.protocol ? 'https://ssl'
				: 'http://www')
				+ '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(ga, s);
	})();
</script>
<link rel="shortcut icon"
	href="//d85wutc1n854v.cloudfront.net/live/imgs/favicon.ico">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 보기</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.2.2.min.js">
	
</script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script>
	$(function() {

		$("#listbtn").on("click", goList);
		$("#replbtn").on("click", showForm);
		$("#delete").on("click", delAjax);

	});

	function goList() {

		location.href = "list?num=" + ${board.num};

	}

	function showForm() {

		$("#replyform").css("visibility", "visible");
		$("#replbtn").text("답글 취소");
		$("#replbtn").on("click", hideForm);

	}

	function hideForm() {

		$("#replyform").css("visibility", "hidden");
		$("#replbtn").text("답글");
		$("#replbtn").on("click", showForm);

	}

	function delAjax() {

		var data = $("#infoform").serialize();
		$.ajax({

			url : "delConfirm",
			data : data,
			dataType : "json",
			type : "post",
			success : function(obj) {
				if (obj.parent) {

					var c = confirm("자식글이어서 삭제가 가능합니다. 삭제하시겠습니까?");
					if (c) {
						location.href = "del?num="
								+ $("input[name='num']").val();
					}
				} else {

					alert("부모글이어서 삭제가 불가능합니다. 자식을 먼저 삭제하세요~");

				}

			},
			error : function() {

			}

		})

	}
</script>
<style>
th {
	background-color: #eeeeee;
	opacity: 0.7;
}

#contenttitle {
	height: 300px;
}

#view {
	margin-left: auto;
	margin-right: auto;
	width: 50%;
	height: 400px;
}
</style>
</head>
<body>

	<%-- 	<table id="view" class="table" style="margin-top:150px;">
		<tr>
			<th>글 번호</th>
			<th>글 제목</th>
			<th>글 쓴 날짜</th>
		</tr>
		<tr>
			<td>${board.num}</td>
			<td>${board.title}</td>
			<td>${board.w_date}</td>
		</tr>
		<tr>
			<th id="contenttitle">글 내용</th>
			<td colspan=2>${board.contents}</td>
		</tr>
	</table> --%>
	<div class="blog-entry col-md-8 col-sm-8 col-xs-12"
		style="margin-left: 20% !important; margin-right: auto !important; margin-top: 50px !important;">
		<article class="post">
		<h2 class="title">${board.title}</h2>
		<div class="meta">
			<ul class="meta-list list-inline">
				<li class="post-time post_date date updated"><i
					class="fa fa-calendar"></i>${board.w_date}</li>
				<li class="post-author"><i class="fa fa-user"></i> <a href="#">${board.nickname}</a></li>
			</ul>
			<!--//meta-list-->
		</div>
		<!--meta-->
		<div class="content">
			<p class="post-figure">${board.contents}</p>
			<!--                         <p class="video-container">
                        <div class="fluid-width-video-wrapper" style="padding-top: 56.25%;"><iframe src="//player.vimeo.com/video/37254322?color=ffffff&amp;wmode=transparent" frameborder="0" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen="" id="fitvid144791"></iframe></div>
                        </p>  -->

		</div>

		<br>
		<br>
		<br>
		<!--//Soical media buttons: https://github.com/kni-labs/rrssb (More examples) -->
		<br>
		<form action="modform" style="display:inline;"
			id="infoform">
			<input type="hidden" name="num" value="${board.num}" /> 
			<input type="hidden" name="title" value="${board.title}" /> 
			<input type="hidden" name="wdate" value="${board.w_date}" /> 
			<input type="hidden" name="contents" value='${board.contents}' />
			<button type="submit" class="btn btn-default">수정</button>
		</form>
		<button type="button" id="replbtn" class="btn btn-default">답글</button>
		<button type="button" id="listbtn" class="btn btn-default">리스트보기</button>
		<button type="button" id="delete" class="btn btn-default">삭제</button>
		<form action="reply" method="post" id="replyform" class="form-group" style="margin-top:25px; visibility:hidden">			
				<input type="hidden" name="ref" value="${board.num}"> 
				<br> 
				<input type="text" id="title" name="title" class="form-control" placeholder="제목"> 
				<br>
				<textarea rows="4" cols="50" id="contents" name="contents" class="form-control" placeholder="내용"></textarea>
				<br>
				<button type="submit" class="btn btn-default">답글</button>
		</form>
	</div>
</body>
</html>