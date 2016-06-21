<%@page import="org.kdea.vo.FreeBoardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String cp = request.getContextPath();
%>
<%--ContextPath 선언 --%>
<%
	List<FreeBoardVO> list = (List<FreeBoardVO>) request.getAttribute("fbList");
	String strPages = (String) request.getParameter("page");
	int pages = Integer.parseInt(strPages);
	int lastPages = list.get(0).getPagevo().getLastPage();

%>

<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$('#prev').on('click',prevClick);
		$('#next').on('click',nextClick);
	<%if (pages == 1) {%>
		$('#prev').hide();
		
	<%} else if (pages == lastPages);%>
		/* $('#next').hide(); 	*/

		}); 
	<%	//System.out.println("글 삭제성공: "+(String)session.getAttribute("deletesuc"));
	String deletesuc=(String)session.getAttribute("deletesuc");
	if(deletesuc!=null){
	if(deletesuc.equals("true")){%>
	alert('글이 성공적으로 삭제되었습니다.');
	<%
	session.setAttribute("deletesuc", null);}
	}%>
	function prevClick(){
			console.log("전버튼 눌림");
			<%if (list.get(0).getPagevo().isLeftMore()) {%>
			location.href="../list?page=<%=list.get(0).getPagevo().getFirstPage() - 1%>";
			<%} else {%>
			$('#prev').hide();
			$('#next').show();
			<%}%>
			}
	function nextClick(){
			console.log("후버튼 눌림");
			<%if (list.get(0).getPagevo().isRightMore()) {%>
				location.href="../list?page=<%=list.get(0).getPagevo().getLastPage() + 1%>";
<%} else {%>
	$('#next').hide();
		$('#prev').show();
<%}%>
	}
</script>
<div class="container">
	<div class="row">
		<h1 class="text-center" style="margin-left: 100px; padding-bottom: 20px">자유게시판</h1>

		<div class="col-lg-12">
			<div class="table-responsive">
				<table class="table">
					<thead>
						<tr>
							<th style="text-align: center;">번호</th>
							<th style="text-align: center;">제목</th>
							<th style="text-align: center;">작성자</th>
							<th style="text-align: center;">작성일</th>
							<th style="text-align: center;">조회수</th>
							<th style="text-align: center;">
							<a id="recommendfree" href="recommendfree?page=1">추천수</a></th>
						</tr>
					</thead>
					<tbody style="text-align: center;">
						<c:forEach var="list" items="${fbList}">
							<tr>
								<td class="num" style="width: 10%">${list.num}</td>
								<td class="title" style="width: 45%; text-align: left;"
									onmouseover="this.style.color='gray'" style="cursor: pointer"
									onmouseout="this.style.color='black'"><a
									href="detail?num=${list.num}">${list.title}</a></td>
								<td class="author" style="width: 15%">${list.nickname}</td>
								<td class="date" style="width: 10%">${list.w_date}</td>
								<td class="views" style="width: 10%">${list.views}</td>
								<td class="views" style="width: 10%">${list.recommend}</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="6">
								<ul class="pagination" role="menubar" aria-label="Pagination">

									<li class="arrow unavailable" aria-disabled="true"><a
										type="button" id="prev"> ◀ </a></li>
									<c:choose>
										<c:when test="${isSearch.bsearch }">
											<c:forEach var="i" begin="${fbList[0].pagevo.firstPage}"
												end="${fbList[0].pagevo.lastPage}">
												<li><a
													href="search?page=${i}&searchCategory=${isSearch.searchCategory}&searchContent=${isSearch.searchContent}">${i}</a></li>
											</c:forEach>
										</c:when>
										<c:when test="${recommendsearch }">
											<c:forEach var="i" begin="${fbList[0].pagevo.firstPage}"
												end="${fbList[0].pagevo.lastPage}">
												<li><a
													href="recommendfree?page=${i}">${i}</a></li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<c:forEach var="i" begin="${fbList[0].pagevo.firstPage}"
												end="${fbList[0].pagevo.lastPage}">
												<li><a href="free?page=${i}">${i}</a></li>
											</c:forEach>
										</c:otherwise>
									</c:choose>
									<li class="arrow"><a type="button" id="next">▶</a></li>
								</ul>
							</td>
						</tr>
						<c:if test="${sessionScope.userInfo.nickname != null }">
						<tr>
						<td colspan="6" style="text-align: right;">
						<a href="write">글쓰기</a>
						</td>
						</tr>
						</c:if>
						<tr>
							<td colspan="6" style="text-align: center;">
								
								<form id="search" method="get" action="search">
									<input type="hidden" name="page" value="1"> <select
										name="searchCategory">
										<option value="title">제목</option>
										<option value="id">작성자</option>
										<option value="content">내용</option>
									</select> <input type="text" name="searchContent">
									<button type="submit">찾기</button>
								</form>
							</td>
						</tr>
						</tbody>
				</table>
			</div>
		</div>
	</div>
</div>