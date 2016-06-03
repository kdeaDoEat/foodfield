<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
/* $(function() {
	$('.input-group').on( 'click', '.dropdown-menu li', function(event) {
		alert(event);
	});
}) */

function view(num) {
	location.href="review/read?num="+num;
}
function search_Option(evt) {
	$('#search_concept').text(evt);
}
		
</script>
<div class="container">
        <div class="row">
        <h1 style="margin-left: 100px;padding-bottom: 20px">맛집을 찾자</h1>
            <div class="col-lg-12">
            	<div class="table-responsive">
					<table class="table" >
						<thead>
							<tr>
								<th style="text-align: center;">번호</th>
								<th style="text-align: center;">제목</th>
								<th style="text-align: center;">닉네임</th>
								<th style="text-align: center;">올린날짜</th>
								<th style="text-align: center;">조회수</th>
								<th style="text-align: center;">추천수</th>
							</tr>
						</thead>
						<tbody style="text-align: center;">
							<c:forEach var="l" items="${vo.list}">
								<tr>
									<td class="num" style="width: 10%">${l.num }</td>
									<td class="title" style="width: 45%; text-align: left; cursor: pointer;" onclick="view(${l.num})"
										onmouseout="this.style.color='gray'" onmouseover="this.style.color='black'">
										${l.title}</td>
									<td class="author" style="width: 15%">${l.nickname }</td>
									<td class="date" style="width: 10%"><fmt:formatDate value="${l.w_date }"
											pattern="MM-dd" /></td>
									<td class="hit" style="width: 10%">${l.hit }</td>
									<td class="recommend" style="width: 10%">${l.recommend }</td>
								</tr>
							</c:forEach>
						</tbody>

					</table>
				</div>
				<div class="row">
					<div class="col-md-4"></div>
					<div class="col-md-4" style="text-align: center;">
						<ul class="pagination" role="menubar" aria-label="Pagination">
								<li class="arrow unavailable" aria-disabled="true">
								<c:choose>
									<c:when test="${vo.type eq 'list'}">
										<a href="review?page=${vo.nowpage-1 }">
										&laquo;</a>
									</c:when>
									<c:when test="${vo.type eq 'search'}">
										<a href="search?option=${vo.searchType }&con=${vo.searchOption }&page=${vo.nowpage-1 }">
							  			&laquo;</a>
									</c:when>
								</c:choose>
								</li>
								<li class="current">
								<c:choose>
									<c:when test="${vo.type eq 'list'}">
										<c:forEach var="p" items="${vo.page }">
										<a href="review?page=${p }">${p }</a>
										</c:forEach>
									</c:when>
									<c:when test="${vo.type eq 'search'}">
										<c:forEach var="p" items="${vo.page }">
										&nbsp;<a href="search?option=${vo.searchType }&con=${vo.searchOption }&page=${p }">${p }</a>&nbsp;
										</c:forEach>
									</c:when>
								</c:choose>
								</li>
								<li class="arrow">
								<c:choose>
									<c:when test="${vo.type eq 'list'}">
										<a href="review?page=${vo.nowpage+1 }">&raquo;</a>
									</c:when>
									<c:when test="${vo.type eq 'search'}">
										<a href="search?option=${vo.searchType }&con=${vo.searchOption }&page=${vo.nowpage+1 }">&raquo;</a>
									</c:when>
								</c:choose>
								</li>
							</ul>
					</div>
					<div class="col-md-4" style="padding-top: 2%">
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
			                <input type="text" class="form-control" name="x" placeholder="Search">
			                <span class="input-group-btn">
			                    <button class="btn btn-primary" type="button"><span class="glyphicon glyphicon-search"></span></button>
			                </span>
			            </div>
					</div>
				</div>
				
				<div class="row">
					<div></div>
					
				</div>
				<div style="text-align: center;">
					<a class="btn icon-btn btn-primary" href="review/write">
            		<span class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted">
            		</span>　글쓰기</a>
				</div>
            </div>
        </div>
 </div>
