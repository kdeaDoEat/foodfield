<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    <link href="/FoodField/resources/assets/css/hover_pack.css" rel="stylesheet">
    <link href="/FoodField/resources/assets/css/animations.css" rel="stylesheet">
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$(".mainImg").children().css("width","100%").css("height","200px");
})
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
	location.href='http://192.168.8.43:8088/FoodField/review?range=recommend';
}
function numm() {
	var url=location.href;
	location.href='http://192.168.8.43:8088/FoodField/review';
}
function hitt() {
	var url=location.href;
	location.href='http://192.168.8.43:8088/FoodField/review?range=hit';
}
</script>
<div class="container">
	<div class="row mt centered ">
		<div class="col-lg-4 col-lg-offset-4">
      			<h1 class="text-center" style="margin-bottom: 5%">맛집을 찾자</h1>
		</div>
	</div><!-- /row -->
	<div class="row mt centered ">
		<div class="col-lg-4 col-lg-offset-11">
			<div class="input-group-btn search-panel">
	                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
	                    	<span id="search_concept">Option</span> <span class="caret"></span>
	                    </button>
	                    <ul class="dropdown-menu" role="menu">
	                      <li><span style="cursor: pointer;" onclick="numm()">최신글 순</span></li>
	                      <li><span style="cursor: pointer;" onclick="recommend()">추천수 순</span></li>
	                      <li><span style="cursor: pointer;" onclick="hitt()">조회수 순</span></li>
	                    </ul>
	                </div>
		</div>
	</div><!-- /row -->
	<div class="row mt centered">
		<c:forEach var="l" items="${vo.list}">
			<div class="col-lg-4 desc">
				<a class="b-link-fade b-animate-go" onclick="view(${l.num})">
				<div style="margin: 10px;" class="mainImg">${l.photo }</div>
					<div class="b-wrapper">
						<br><br><br>
					  	<h4 class="b-from-left b-animate b-delay03">${l.title}</h4>
					  	<p class="b-from-right b-animate b-delay03">View Details</p>
					</div>
				</a>
				<p style="text-align: center;">${l.nickname }<i class="fa fa-heart-o"></i>
				<span>　　　</span>
				<span class="glyphicon glyphicon-eye-open">　${l.hit }　</span>
				<span class="glyphicon glyphicon-thumbs-up">　${l.recommend}　</span></p>
			</div>
		</c:forEach>
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
