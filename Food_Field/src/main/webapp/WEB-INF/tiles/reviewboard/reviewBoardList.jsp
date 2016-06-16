<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
function view(num) {
	location.href="review/read?num="+num;
}
function search_Option(evt) {
	$('#search_concept').text(evt);
}
function search() {
	var text = '';
	if($('#search_concept').text() == 'Option'){
		alert('검색 옵션을 설정해주세요!');
	}else if($('#searchContent').val() == ''){
		alert('검색 내용을 입력해주세요!');
	}else{
		if($('#search_concept').text() == '제목')		text='title';
		else if($('#search_concept').text() == '내용')	text='contents';
		else if($('#search_concept').text() == '작성자')	text='nickname';
		
		location.href="review/search?type="+text+"&word="+$('#searchContent').val()+"&page=1";
	}
}
function recommend() {
	var url=location.href;
	if(url == 'http://192.168.8.43:8088/FoodField/review'){
		location.href='http://192.168.8.43:8088/FoodField/review?recommend=high';
	}else{
		location.href='http://192.168.8.43:8088/FoodField/review';
	}
}
</script>
<div class="container">
	<div class="row">
        <h1 class="text-center" style="margin-bottom: 5%">맛집을 찾자</h1>
        <div class="col-xs-12">
        	<div class="table-responsive">
				<table class="table" >
					<thead>
						<tr>
							<th style="text-align: center;">번호</th>
							<th style="text-align: center;">제목</th>
							<th style="text-align: center;">작성자</th>
							<th style="text-align: center;">작성일</th>
							<th style="text-align: center;">조회수</th>
							<th style="text-align: center; cursor: pointer;" onclick="recommend()">추천수</th>
						</tr>
					</thead>
					<tbody style="text-align: center;">
						<c:forEach var="l" items="${vo.list}">
							<tr>
								<td class="num" style="width: 10%">${l.num }</td>
								<td class="title" style="width: 45%; text-align: left; cursor: pointer;" onclick="view(${l.num})"
								onmouseout="this.style.color='gray'" onmouseover="this.style.color='black'">
								${l.title}
								</td>
								<td class="author" style="width: 15%">${l.nickname }</td>
								<td class="date" style="width: 10%"><fmt:formatDate value="${l.w_date }"
										pattern="yyyy-MM-dd" /></td>
								<td class="hit" style="width: 10%">${l.hit }</td>
								<td class="recommend" style="width: 10%">${l.recommend }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-4"></div>
			<div class="col-xs-4" style="text-align: center;">
				<ul class="pagination" role="menubar" aria-label="Pagination">
						<li class="arrow unavailable" aria-disabled="true">
						<c:choose>
							<c:when test="${vo.type eq 'list'}">
								<a href="review?page=${vo.nowpage-1 }">
								&laquo;</a>
							</c:when>
							<c:when test="${vo.type eq 'search'}">
								<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${vo.nowpage-1 }">
					  			&laquo;</a>
							</c:when>
						</c:choose>
						</li>
						<c:choose>
							<c:when test="${vo.type eq 'list'}">
								<c:forEach var="p" items="${vo.page }">
									<c:choose>
										<c:when test="${p eq vo.nowpage }">
											<li class="current">
											<a href="review?page=${p }" style="font-weight: bold;">${p }</a>
											</li>
										</c:when>
										<c:otherwise>
											<li class="current">
											<a href="review?page=${p }">${p }</a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
							<c:when test="${vo.type eq 'search'}">
								<c:forEach var="p" items="${vo.page }">
									<c:choose>
										<c:when test="${p eq vo.nowpage }">
											<li class="current">
											<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${p }" style="font-weight: bold;">${p }</a>
											</li>
										</c:when>
										<c:otherwise>
											<li class="current">
											<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${p }">${p }</a>
											</li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</c:when>
						</c:choose>
						
						<li class="arrow">
						<c:choose>
							<c:when test="${vo.type eq 'list'}">
								<a href="review?page=${vo.nowpage+1 }">&raquo;</a>
							</c:when>
							<c:when test="${vo.type eq 'search'}">
								<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${vo.nowpage+1 }">&raquo;</a>
							</c:when>
						</c:choose>
						</li>
					</ul>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-md-4 col-md-offset-4" style="padding-top: 2%">
				<div class="input-group">
	                <div class="input-group-btn search-panel">
	                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
	                    	<span id="search_concept">Option</span> <span class="caret"></span>
	                    </button>
	                    <ul class="dropdown-menu" role="menu">
	                      <li><span style="cursor: pointer;" onclick="search_Option('제목');">제목</span></li>
	                      <li><span style="cursor: pointer;" onclick="search_Option('내용');">내용</span></li>
	                      <li><span style="cursor: pointer;" onclick="search_Option('작성자');">작성자</span></li>
	                    </ul>
	                </div>
	                <input type="hidden" name="search_param" value="all" id="search_param">         
	                <input id="searchContent" type="text" class="form-control" placeholder="Search">
	                <span class="input-group-btn">
	                    <button class="btn btn-primary" type="button" onclick="search();"><span class="glyphicon glyphicon-search"></span></button>
	                </span>
	            </div>
			</div>
		</div>
		<div class="row">
			<div style="text-align: center;">
				<a class="btn icon-btn btn-primary" href="review/write">
           		<span class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted">
           		</span>　글쓰기</a>
		</div>
	</div>
</div>
