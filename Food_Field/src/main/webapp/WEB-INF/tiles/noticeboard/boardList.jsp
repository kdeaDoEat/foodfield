<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%--계산을 쓸려면 fmt!! --%>
<% String cp = request.getContextPath(); %> <%--ContextPath 선언 --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지게시판</title>
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

		$("#writebtn").on("click", writego);
		$("#searchbtn").on("click", gosearch);
		$("#search").keypress(function(e) {
			if (e.keyCode == 13)
				gosearch();

		});

	});

	function writego() {

		location.href = "write";

	}

	function gosearch() {

		location.href = "list?currpage=1&type=" + $("#searchoption").val()
				+ "&word=" + $("#search").val();

	}
</script>
<style>
#belownavi {
	display: table;
	margin-left: auto;
	margin-right: auto;
}

#pageoutput {
	display: table;
	margin-left: auto;
	margin-right: auto;
}
a {

 text-decoration:none;

}
</style>
</head>
<body>


    <div style="margin-left: auto;
	margin-right: auto; width:70%; margin-top:150px;">
	<table class="table table-hover">
		<output id="pageoutput">${page.currpage}/${page.totalpage}</output>
		<tr>
			<th>글 번호</th>
			<th>글 제목</th>
			<th>글쓴이</th>
			<th>글쓴 날짜</th>
		</tr>
		<c:forEach var="board" items="${boardlist}" varStatus="status">
			<tr>
				<td>${board.num}</td>
				<td><a href="view?num=${board.num}" style="text-decoration:none;">${board.title}</a></td>
				<td>${board.nickname}</td>
				<td>${board.w_date}</td>
			</tr>
		</c:forEach>
	</table>
	<button type="button" id="writebtn" class="btn btn-warning"><span class="glyphicon glyphicon-pencil"></span> 글쓰기</button>
	</div>
	
	<div id="belownavi">
		<ul class="pagination">
			<fmt:parseNumber var="currdivppp" type="number"
				value="${page.currpage/page.ppp}" integerOnly="true" />

			<c:if test="${(page.currpage % page.ppp) != 0}">

				<fmt:parseNumber var="currstart" type="number"
					value="${(currdivppp*page.ppp)+1}" />

				<c:if test="${(currdivppp*page.ppp)+page.ppp > page.totalpage}">
					<fmt:parseNumber var="currend" type="number"
						value="${page.totalpage}" />
				</c:if>
				<c:if test="${(currdivppp*page.ppp)+page.ppp <= page.totalpage}">
					<fmt:parseNumber var="currend" type="number"
						value="${(currdivppp*page.ppp)+page.ppp}" />
				</c:if>

				<c:if test="${currdivppp>0}">
					<li><a
						href="list?currpage=${currstart-page.ppp}&type=${search.type}&word=${search.word}">이전</a></li>
				</c:if>

				<c:forEach var="i" begin="${currstart}" end="${currend}"
					varStatus="status">
					<li><a
						href="list?currpage=${status.current}&type=${search.type}&word=${search.word}">${status.current}</a></li>
				</c:forEach>

				<c:if test="${currend != page.totalpage}">
					<li><a
						href="list?currpage=${currend+1}&type=${search.type}&word=${search.word}">다음</a></li>
				</c:if>

			</c:if>

			<c:if test="${(page.currpage % page.ppp) == 0}">

				<fmt:parseNumber var="currstart" type="number"
					value="${((currdivppp-1)*page.ppp)+1}" />

				<c:if test="${currdivppp*page.ppp > page.totalpage}">
					<fmt:parseNumber var="currend" type="number"
						value="${page.totalpage}" />
				</c:if>
				<c:if test="${currdivppp*page.ppp <= page.totalpage}">
					<fmt:parseNumber var="currend" type="number"
						value="${currdivppp*page.ppp}" />
				</c:if>

				<c:if test="${currdivppp>1}">
					<li><a
						href="list?currpage=${currstart-page.ppp}&type=${search.type}&word=${search.word}">이전</a></li>
				</c:if>

				<c:forEach var="i" begin="${currstart}" end="${currend}"
					varStatus="status">
					<li><a
						href="list?currpage=${status.current}&type=${search.type}&word=${search.word}">${status.current}</a></li>
				</c:forEach>

				<c:if test="${currend != page.totalpage}">
					<li><a
						href="list?currpage=${currend+1}&type=${search.type}&word=${search.word}">다음</a></li>
				</c:if>

			</c:if>
		</ul>
		
	</div>

	<form class="navbar-form navbar" role="search" style="display:table; margin-left:auto; margin-right:auto;">

		<div class="form-group">
			<select name="type" id="searchoption" class="form-control">
				<option value="제목">제목</option>
				<option value="내용">내용</option>
				<option value="작성자">작성자</option>
			</select> 
			<input type="text" class="form-control" id="search" name="word"
				placeholder="검색할 내용을 입력하세요"/>
				<button type="button" id="searchbtn" class="btn btn-info btn-warning"><span class="glyphicon glyphicon-search"></span> 검색</button>
		</div>
		
	</form>	

    
</body>
</html>