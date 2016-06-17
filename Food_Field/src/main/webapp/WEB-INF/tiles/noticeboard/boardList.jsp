<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%--계산을 쓸려면 fmt!! --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지게시판</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.2.2.min.js">
	
</script>
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
	text-decoration: none;
}
</style>
</head>
<body>
		<div id="noticeList" class="container" style="margin-top: 120px; margin-left: auto; margin-right: auto; width: 80%;">
			<div id="noticeTitle" style="width: 100%; text-align: center;">
				<h1>공지게시판</h1>
			</div>
			<div class="table-responsive"
				style="margin-left: auto; margin-right: auto; width: 100%;">
				<table class="table table-hover">
					<tr>
						<th style="text-align: center;">글 번호</th>
						<th style="text-align: center;">글 제목</th>
						<th style="text-align: center;">글쓴이</th>
						<th style="text-align: center;">글쓴 날짜</th>
					</tr>
					<c:forEach var="board" items="${boardlist}" varStatus="status">
						<tr>
							<td style="text-align: center;">${board.num}</td>
							<td><a href="view?num=${board.num}"
								style="text-decoration: none;">${board.title}</a></td>
							<td style="text-align: center;">${board.nickname}</td>
							<td style="text-align: center;">${board.w_date}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<button type="button" id="writebtn" class="btn btn-warning"
					style="float: right; margin-top: 10px;">
					<span class="glyphicon glyphicon-pencil"></span> 글쓰기
				</button>
			</sec:authorize>
		</div>

	<div id="belownavi" style="margin-top: 50px;">
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
	<center style="margin-top: 50px;">
		<form class="navbar-form navbar" role="search"
			style="display: table; margin-left: auto; margin-right: auto;">

			<div class="form-group">
				<select name="type" id="searchoption" class="form-control">
					<option value="제목">제목</option>
					<option value="내용">내용</option>
					<option value="작성자">작성자</option>
				</select> <input type="text" class="form-control" id="search" name="word"
					placeholder="검색할 내용을 입력하세요" />
				<button type="button" id="searchbtn"
					class="btn btn-info btn-warning" style="margin: 0px">
					<span class="glyphicon glyphicon-search"></span> 검색
				</button>
			</div>

		</form>
	</center>

</body>
</html>