<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript">
		$(function() {
		})
		function view(num) {
			location.href="reviews/read?num="+num;
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
							<c:forEach var="l" items="${list}">
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
							<tr>
							<td colspan="6">
							<ul class="pagination" role="menubar" aria-label="Pagination">
					<li class="arrow unavailable" aria-disabled="true">
								<c:choose>
									<c:when test="${vo.type eq 'list'}">
										<a href="review?page=${vo.nowpage-1 }">
										&laquo; Previous</a>
									</c:when>
									<c:when test="${vo.type eq 'search'}">
										<a href="search?option=${vo.searchType }&con=${vo.searchOption }&page=${vo.nowpage-1 }">
							  			&laquo; Previous</a>
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
										<a href="review?page=${vo.nowpage+1 }">Next &raquo;</a>
									</c:when>
									<c:when test="${vo.type eq 'search'}">
										<a href="search?option=${vo.searchType }&con=${vo.searchOption }&page=${vo.nowpage+1 }">Next &raquo;</a>
									</c:when>
								</c:choose>
								</li>
							</ul>
							</td>
							</tr>
            				<tr><td colspan="6" style="text-align: center;">

            				<a class="btn icon-btn btn-primary" href="review/write">

            				<span class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted">
            				</span>　글쓰기</a></td></tr>
						</tbody>

					</table>
				</div>
            </div>
        </div>
 </div>