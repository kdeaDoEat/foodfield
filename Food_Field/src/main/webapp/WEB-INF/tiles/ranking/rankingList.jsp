<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%--계산을 쓸려면 fmt!! --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원랭킹</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.2.2.min.js">
</script>
<script>

	$(function() {

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

</style>
</head>
<body>
	<div style="margin-top: 150px; width: 100%;">
		<table style="margin-left: auto; margin-right: auto;" class="table">
			<tr>
				<th style="text-align: center;">순위</th>
				<th style="text-align: center;">이메일</th>
				<th style="text-align: center;">닉네임</th>
				<th style="text-align: center;">이름</th>
				<th style="text-align: center;">포인트</th>
			</tr>
			<c:forEach var="user" items="${userlist}" varStatus="status">
				<tr>
					<td style="text-align: center;">${user.rank}</td>
					<td>${user.email}</td>
					<td>${user.nickname}</td>
					<td>${user.name}</td>
					<td>${user.point}</td>
				</tr>
			</c:forEach>
		</table>
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
			<select id="searchoption"  class="form-control">
			  <option value="이메일">이메일</option>
			  <option value="닉네임">닉네임</option>
			  <option value="이름">이름</option>
			</select>
			<input type="text" class="form-control" id="search" name="word"
				placeholder="검색할 내용을 입력하세요"/>
		    <button type="button" id="searchbtn" class="btn btn-info btn-warning" style="margin:0px"><span class="glyphicon glyphicon-search"></span> 검색</button>
		    </div>
		
		</form>
	</div>
</body>
</html>