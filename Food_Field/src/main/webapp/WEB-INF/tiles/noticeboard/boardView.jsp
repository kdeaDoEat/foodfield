<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String cp = request.getContextPath(); %> <%--ContextPath 선언 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

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

		location.href = "list?num="+ ${board.num};

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

	<table id="view" class="table" style="margin-top:150px;">
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
	</table>
	<br>


	<form action="modform" style="margin-left: 25%; display: inline;"
		id="infoform" class="form-group">
		<input type="hidden" name="num" value="${board.num}" /> <input
			type="hidden" name="title" value="${board.title}" /> <input
			type="hidden" name="wdate" value="${board.w_date}" /> <input
			type="hidden" name="contents" value='${board.contents}' />
		<button type="submit" class="btn btn-default">수정</button>
	</form>
	<button type="button" id="replbtn" class="btn btn-default">답글</button>
	<button type="button" id="listbtn" class="btn btn-default">리스트보기</button>
	<button type="button" id="delete" class="btn btn-default">삭제</button>
	<form action="reply" method="post"
		style="margin-left: auto; margin-right: auto; visibility: hidden; width: 380px;"
		id="replyform">
		<fieldset>
			<legend>답글쓰기</legend>
			<input type="hidden" name="ref" value="${board.num}" /> <label
				for="title">제목</label> <br> <input type="text" id="title"
				name="title" style="width: 370px;" /> <br> <label
				for="contents">내용</label> <br>
			<textarea rows="4" cols="50" id="contents" name="contents"></textarea>
			<br>
			<button type="submit" class="btn btn-default">답글</button>
		</fieldset>
	</form>

</body>
</html>