<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>

<style>
/* 매우 작은 기기들 (모바일폰, 768px 보다 작은) */
/* 부트스트랩에서 이것은 기본이므로 미디어쿼리가 없습니다. */

/* 작은 기기들 (태블릿, 768px 이상) */
@media (min-width: @screen-sm-min) { ... }
#tb{width:100%; margin:auto;}
#tb .w_date{font-size:12px;}

@media (min-width: @screen-md-min) { ... }

/* 큰 기기들 (큰 데스크탑, 1200px 이상) */
@media (min-width: @screen-lg-min) {
#tb{width:80%; margin:auto;}


}
</style>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.2.2.min.js"></script>
<script>
 	$(function() {
		$("#writebtn").on("click", writego);
		$("#searchbtn").on("click",gosearch);
		$("#word").keypress(function(e) {
			if (e.keyCode == 13) gosearch();   	  
	    });
	});

	function writego() {
		location.href = "qnawriteForm";
	}
	
	function gosearch() {
		location.href="qna?currpage=1&type="+$("#type").val()+"&word="+$("#word").val();
	}
	
	
	$(document).ready(function(e){
	    $('.search-panel .dropdown-menu').find('a').click(function(e) {
			e.preventDefault();
			var param = $(this).attr("href").replace("#","");
			var concept = $(this).text();
			$('.search-panel span#search_concept').text(concept);
			$('.input-group #search_param').val(param);
		});
	});
	
	
</script>







<div style="height:100px;">겹치는부분</div>
<!-- body -->
<div>
	<!-- title -->
	<div style="width:80%; margin:auto;">
		<h2>[ Q&A BOARD ]</h2>
		<hr>	
	</div>
	<!-- table -->
	<div style="width:80%; margin:auto; id="qnatable" class="table-responsive">
		<table id="tb" class="table table-striped table-hover">
			<thead>
				<tr>
					<th style="width:20%;">글 번호</th>
					<th style="width:40%;">글 제목</th>
					<th style="width:20%;">글쓴이</th>
					<th class="w_date" style="width:20%;">글쓴 날짜</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="board" items="${boardlist}" varStatus="status">
					<tr>
						<td>${board.num}</td>
						<td><a href="qnaview?num=${board.num}">${board.title}</a></td>
						<td>${board.nickname}</td>
						<td class="w_date">${board.w_date}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- navi -->
	<div class="col-md-12 col-xs-12 col-sm-12" style="width:70%; height:50px; margin:auto;">
		<!-- Pagination -->
		<div class="col-md-4 col-xs-12 col-sm-12" style="width:50%; height:50px; float:left; ">
			<ul class = "pagination pagination-sm">
			<fmt:parseNumber var="currdivppp" type="number"	value="${page.currpage/page.ppp}" integerOnly="true" />
		
			<c:if test="${(page.currpage % page.ppp) != 0}">
				<fmt:parseNumber var="currstart" type="number"	value="${(currdivppp*page.ppp)+1}" />
		
				<c:if test="${(currdivppp*page.ppp)+page.ppp > page.totalpage}">
					<fmt:parseNumber var="currend" type="number"	value="${page.totalpage}" />
				</c:if>
				<c:if test="${(currdivppp*page.ppp)+page.ppp <= page.totalpage}">
					<fmt:parseNumber var="currend" type="number"	value="${(currdivppp*page.ppp)+page.ppp}" />
				</c:if>
		
				<c:if test="${currdivppp>0}">
					<li><a href="qna?currpage=${currstart-page.ppp}&type=${search.type}&word=${search.word}">◀</a></li>
				</c:if>
		
				<c:forEach var="i" begin="${currstart}" end="${currend}"	varStatus="status">
					<li><a href="qna?currpage=${status.current}&type=${search.type}&word=${search.word}">${status.current}</a></li>
				</c:forEach>
		
				<c:if test="${currend != page.totalpage}">
					<li><a href="qna?currpage=${currend+1}&type=${search.type}&word=${search.word}">▶</a></li>
				</c:if>
			</c:if>
		
			<c:if test="${(page.currpage % page.ppp) == 0}">
				<fmt:parseNumber var="currstart" type="number"	value="${((currdivppp-1)*page.ppp)+1}" />
		
				<c:if test="${currdivppp*page.ppp > page.totalpage}">
					<fmt:parseNumber var="currend" type="number"		value="${page.totalpage}" />
				</c:if>
				<c:if test="${currdivppp*page.ppp <= page.totalpage}">
					<fmt:parseNumber var="currend" type="number"		value="${currdivppp*page.ppp}" />
				</c:if>
		
				<c:if test="${currdivppp>1}">
					<li><a href="qna?currpage=${currstart-page.ppp}&type=${search.type}&word=${search.word}">◀</a></li>
				</c:if>
		
				<c:forEach var="i" begin="${currstart}" end="${currend}"	varStatus="status">
					<li><a href="qna?currpage=${status.current}&type=${search.type}&word=${search.word}">${status.current}</a></li>
				</c:forEach>
		
				<c:if test="${currend != page.totalpage}">
					<li><a href="qna?currpage=${currend+1}&type=${search.type}&word=${search.word}">▶</a></li>
				</c:if>
			</c:if>
			</ul>
		</div>
		
	
		<div class="col-md-8 col-xs-12 col-sm-12">
		<!-- style="width:50%; height:50px; float:right; text-align: right;" -->
			<!-- Search -->
			<div class="col-md-4 col-xs-12 col-sm-12"><!-- style="float:left;width:90%;" -->
				<div class="col-md-2 col-xs-12 col-sm-12"><!-- style="float:left;width:30%;" -->
					<select name="type" id="type" class="form-control input-sm">
						<option value="제목">제목</option>
						<option value="내용">내용</option>
						<option value="작성자">작성자</option>
					</select>
				</div>
				<div class="col-md-2 col-xs-12 col-sm-12"> <!-- style="float:left;width:50%;" -->
					<input type="text" id="word" class="form-control input-sm"/>
				</div>
				<div class="col-md-2 col-xs-12 col-sm-12"><!--  style="float:right;width:20%;" -->
					<button type="button" id="searchbtn" class="btn btn-default btn-sm">
						<span class="glyphicon glyphicon-search"></span>
					</button>
				</div>
			</div>
			<!-- Write -->
			<div class="col-md-2 col-xs-12 col-sm-12"><!-- style="float:right;width:10%;" -->
				<sec:authorize access="isAuthenticated()">
				<button type="button" id="writebtn" class="btn btn-warning btn-sm">글쓰기</button>
				</sec:authorize>
			</div>
		</div>
	</div>	
</div>