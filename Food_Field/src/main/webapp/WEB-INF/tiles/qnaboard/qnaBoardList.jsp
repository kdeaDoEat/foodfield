<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>

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


<!-- body -->
<div style="margin-top:100px; margin-bottom:100px;" class="container">
	<!-- title -->
	<div class="col-md-11 center-block" style="text-align:center;">
		<h2>Q&A BOARD</h2>
	</div>
	
	<!-- table -->

	<div style="margin:auto;" id="qnatable" class="col-md-11 center-block table-responsive">
		<table id="tb" class="table table-hover" style="text-align:center;">
			<thead>
				<tr>
					<th style="width:20%; text-align:center;">번호</th>
					<th style="width:40%; text-align:center;">제목</th>
					<th style="width:20%; text-align:center;">작성자</th>
					<th class="w_date" style="width:20%; text-align:center;">작성일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="board" items="${boardlist}" varStatus="status">
					<tr>
						<td>${board.num}</td>
						<td style="text-align:left;"><a href="qnaview?num=${board.num}">${board.title}</a></td>
						<td>${board.nickname}</td>
						<td class="w_date">${board.w_date}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<!-- bottom Menu -->
	<div style="height:50px;" class="col-md-11 center-block">
		<!-- Pagination -->
		<div style="height:50px;" class="col-md-3 col-xs-12 center-block">
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
		
		<!-- Search & Button -->
		<div class="col-md-9 col-xs-12" style="height:50px; text-align:right; float:right; margin-top:20px;">
			<!-- Write -->
			<span class="col-md-2 col-md-offset-5 col-xs-12"><!-- style="float:right;width:10%;" -->
				<sec:authorize access="isAuthenticated()">
				<button type="button" id="writebtn" class="btn btn-warning btn-sm">글쓰기</button>
				</sec:authorize>
			</span>
			
			<!-- Search Group -->
			<span class="input-group col-md-5 col-xs-12">
				<span class="input-group-btn">
					<select name="type" id="type" class="btn btn-warning btn-sm" style="height:30px;">
						<option value="제목">제목</option>
						<option value="내용">내용</option>
						<option value="작성자">작성자</option>
					</select>					
				</span>
				<input type="text" id="word" style="height:30px;" class="form-control input-sm">
				<span class="input-group-btn">
					<button id="searchbtn" class="btn btn-warning btn-sm" type="button"><span class="glyphicon glyphicon-search"></span>검색</button>
				</span>
			</span>
		</div>
	</div>	
</div>