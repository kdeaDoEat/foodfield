<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<!-- <link rel="stylesheet" type="text/css" media="screen"
	href="//d85wutc1n854v.cloudfront.net/live/css/screen_preview.css"> -->
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
        $("#commentscnt").text($(".replycontents").length);
		$("#listbtn").on("click", goList);
		$("#replbtn").on("click", showForm);
		$("#delete").on("click", delAjax);

	});

	function goList() {

		location.href = "list?num=" + ${board.num};

	}
	
	function replyajax() {

		var data = $("#replyform").serialize();
		
		$.ajax({

			url : "reply",
			type : "post",
			data : data,
			dataType : "json",
			success : function(obj) {

				if (obj.success) {
                    
					var cnts = $("#commentscnt").text()*1;
					cnts = cnts+1;
					$("#commentscnt").text(cnts);
 					$("#replylist table").append(
							"<tr><td style='padding-left:25%;'>" + obj.nickname + "</td><td class='replycontents'>"
									+ obj.contents + "</td><td>" + obj.w_date
									+ "</td><td><a href='' class='glyphicon glyphicon-wrench' style='text-decoration:none; width:50px; color:#bbb'></a><a href='' class='glyphicon glyphicon-remove' style='text-decoration:none; width:10px; color:#bbb'></a></td></tr>");
		     			
					
				}
				
			},
			error : function() {
				
				alert("에러");
				
			}
		})
	};

	var replyform = "<form action=\"reply\" id=\"replyform\" class=\"form-group\" style=\"width:100%; margin-top:25px;\"><input type=\"hidden\" name=\"num\" value=\"${board.num}\"><br><textarea rows=\"4\" cols=\"45\" id=\"contents\" name=\"contents\" class=\"form-control\" style=\"margin-bottom:20px; width: 100%; padding: 4px;\" placeholder=\"내용\"></textarea><br><button type='button' onclick=\"replyajax()\" class=\"btn btn-warning\"><span class=\"glyphicon glyphicon-pushpin\"></span> 답글</button></form>";
	
	function showForm() {

		//$("#replyform").css("visibility", "visible");
		$("#replyformdiv").html(replyform);		
		$("#replbtn").text("답글 취소");
		$("#replbtn").prepend("<span class=\"glyphicon glyphicon-remove\"></span> ");
		$("#replbtn").on("click", hideForm);

	}

	function hideForm() {

		//$("#replyform").css("visibility", "hidden");
		$("#replyformdiv").html("");		
		$("#replbtn").text("답글");
		$("#replbtn").prepend("<span class=\"glyphicon glyphicon-pushpin\"></span> ");
		$("#replbtn").on("click", showForm);

	}

	function recommendAjax() {
        if(confirm("정말로 이 글을 추천하시겠습니까?")){
		$.ajax({

			url : "recommend?num=" + $("input[name='num']").val(),
			dataType : "json",
			type : "get",
			success : function(obj) {

				if (obj.success) {
					
					$("#recommend").html(obj.recommend);
					
				} else {
					
				}
			},
			error : function() {

			}

		})
        }

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
	
	function replyModify(index){
		
		var data = $("#reply"+index).serialize();
		if(confirm("이대로 댓글을 수정하시겠습니까?")){
		$.ajax({
			
			url:"replyModify",
			data:data,
			type:"post",
			dataType:"json",
			success:function(obj){
				
				if(obj.success){
										
					$("#reply"+index).css("display","none");
		            $("#reply-noform"+index).css("display","");
					$("#reply-noform"+index).find("#replycontents").text(obj.contents);
										
				}
				
			},
			error:function(){
				
			}
			
		})
		}
	}
	
	function replyDel(index){
		
		var data = $("#reply"+index).serialize();
		if(confirm("이대로 댓글을 삭제하시겠습니까?")){
		$.ajax({
			
			url:"replyDel",
			data:data,
			type:"post",
			dataType:"json",
			success:function(obj){
				
				if(obj.success){
										
					hideForm(index);
					$("#reply-noform"+index).parent().remove();
										
				}
				
			},
			error:function(){
				
			}
			
		})
		}
	}
	
	function changeForm(index){
		
		$("#reply"+index).css("display","");
		$("#reply-noform"+index).css("display","none");
		
	}
	
	function hideForm(index){
		
		$("#reply"+index).css("display","none");
		$("#reply-noform"+index).css("display","");
		
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
	<div style="margin-left: 20% !important; margin-right: auto !important; width: 60%; margin-top: 50px !important; margin-bottom: 50px;">
		<article class="post" style="margin-top:100px;">
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
			<p class="post-figure">
			<div style="padding-bottom:20px;">
			<span class="glyphicon glyphicon-eye-open" style="width:30px;"></span><span id="hit">${board.view}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		    <span class="glyphicon glyphicon-thumbs-up" style="width:30px;"></span><span id="recommend">${board.recommend}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="glyphicon glyphicon-comment" style="width:30px;"></span><span id="commentscnt"></span>
			</div>
			<hr>
			<br>
			<br>
			${board.contents}			
			<!--        <p class="video-container">
                        <div class="fluid-width-video-wrapper" style="padding-top: 56.25%;"><iframe src="//player.vimeo.com/video/37254322?color=ffffff&amp;wmode=transparent" frameborder="0" webkitallowfullscreen="" mozallowfullscreen="" allowfullscreen="" id="fitvid144791"></iframe></div>
                        </p>  -->		
        <br>
        <br>
        <div style="text-align:center;"><button onclick="recommendAjax()" class="btn btn-warning" style="margin-left:auto; margin-right:auto;"><span class="glyphicon glyphicon-thumbs-up"></span> 추천</button></div>
		<br>
		<br>
		<br>
		<!--//Soical media buttons: https://github.com/kni-labs/rrssb (More examples) -->
		<br>
		<div class="navbar-form navbar">
			<form action="modform" style="display: inline;" id="infoform">
				<input type="hidden" name="num" value="${board.num}" /> <input
					type="hidden" name="title" value="${board.title}" /> <input
					type="hidden" name="wdate" value="${board.w_date}" /> <input
					type="hidden" name="contents" value='${board.contents}' />
				<button type="submit" class="btn btn-warning"><span class="glyphicon glyphicon-wrench"></span> 수정</button>
			</form>
			<button type="button" id="replbtn" class="btn btn-warning"><span class="glyphicon glyphicon-pushpin"></span> 답글</button>
			<button type="button" id="listbtn" class="btn btn-warning"><span class="glyphicon glyphicon-th-list"></span> 리스트보기</button>
			<button type="button" id="delete" class="btn btn-warning"><span class="glyphicon glyphicon-remove"></span> 삭제</button>
			<div id="replyformdiv" style="width:100%;"></div>
			<div id="replylist" style="width:100%;">
		    <table style="margin-top:60px; width:100%; float:right;" class="table">
			<c:forEach var="reply" items="${comments}" varStatus="status">
                  <tr>                 
                    <td colspan="4" style="padding-left:25%;">  
                    <span id="reply-noform${status.index}">
                    <span id="replynick">${reply.nickname}</span>
                    <span id="replycontents" style="margin-left:20px;">${reply.contents}</span>
                    <span id="replywdate" style="margin-left:20px;">${reply.w_date}</span>
                    <span style="float:right">

                    <a onclick="changeForm(${status.index})" class="glyphicon glyphicon-wrench" style="text-decoration:none; width:10px; color:#bbb; cursor:pointer"></a>
                    <a onclick="replyDel(${status.index})" class="glyphicon glyphicon-remove" style="margin-left:20px; text-decoration:none; width:10px; color:#bbb; cursor:pointer"></a>
                    
                    </span>   
                    </span>  
                    <form method="post" id="reply${status.index}" style="display:none;" >
                    <input type="hidden" value="${reply.cnum}" name="cnum"/>
                    <span id="replynick">${reply.nickname}</span> <span id="freplycontents" style="margin-left:20px;"><input type="text" name="contents" value="${reply.contents}"></span> <span id="replywdate" style="margin-left:20px;">${reply.w_date}</span>
                    <span style="float:right">
                    <a onclick="replyModify(${status.index})" class="glyphicon glyphicon-ok" style="text-decoration:none; width:10px; color:#bbb; cursor:pointer"></a>
                    <a onclick="hideForm(${status.index})" class="glyphicon glyphicon-remove" style="margin-left:20px; text-decoration:none; width:10px; color:#bbb; cursor:pointer"></a>
                    <!--  
                    <a href="#" onclick="" class="glyphicon glyphicon-wrench" style="text-decoration:none; width:10px; color:#bbb"></a>
                    <a href="#" onclick="" class="glyphicon glyphicon-remove" style="margin-left:20px; text-decoration:none; width:10px; color:#bbb"></a>
                    -->
                    </span>   
                    </form>                    
                    </td>                 
                  </tr>
			</c:forEach>
			</table>
		</div>
		</div>
	</div>
	</div>
</body>
</html>