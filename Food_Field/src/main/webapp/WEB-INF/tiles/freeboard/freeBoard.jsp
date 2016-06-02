<%@page import="org.kdea.vo.FreeboardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String cp = request.getContextPath(); %> <%--ContextPath 선언 --%>
<%
  List<FreeboardVO> list=(List<FreeboardVO>)request.getAttribute("fbList");
	String strPages= (String)request.getParameter("page");
	int pages=Integer.parseInt(strPages);
	int lastPages=list.get(0).getPagevo().getLastPage();
	System.out.println("현재페이지: "+pages);
	System.out.println("마지막페이지: "+lastPages);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">  
<title>Food Field</title>

    <script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$('#prev').on('click',prevClick);
		$('#next').on('click',nextClick);
	<%if(pages==1){%>
		$('#prev').hide();
		
	<%}else if(pages==lastPages)%>
		/* $('#next').hide(); 	*/

		}); 
	function prevClick(){
			console.log("전버튼 눌림");
			<%
			
			if(list.get(0).getPagevo().isLeftMore()){%>
			location.href="../list?page=<%=list.get(0).getPagevo().getFirstPage()-1%>";
			<%}else{%>
			$('#prev').hide();
			$('#next').show();
			<%}%>
			}
	function nextClick(){
			console.log("후버튼 눌림");
			<%if(list.get(0).getPagevo().isRightMore()){%>
				location.href="../list?page=<%=list.get(0).getPagevo().getLastPage()+1%>";
			<%}else{%>
				$('#next').hide();
				$('#prev').show();
				<%}%>
		}  
	</script>
</head>
<body>
    <div class="container">
    <table id="freetable">
        <tr>
       	 <td>글번호</td><td>제목</td><td>날짜</td><td>작성자</td>
        </tr>
        <c:forEach var="list" items="${fbList}">
        <tr>
         <td>${list.num}</td><td><a href="detail?num=${list.num}">${list.title}</a></td>
         <td>${list.wdate}</td><td>${list.id}</td>
       	</tr>
        </c:forEach>
    </table>
        </div>
        <div>
        <a  type="button" id="prev"> ◀ </a>
         <c:choose>
        <c:when test="${isSearch.bsearch }">
        <c:forEach var="i" begin="${fbList[0].pagevo.firstPage}" end="${fbList[0].pagevo.lastPage}">
		<a href="search?page=${i}&searchCategory=${isSearch.searchCategory}&searchContent=${isSearch.searchContent}">${i}</a>
		</c:forEach>
        </c:when >
        <c:otherwise>
        <c:forEach var="i" begin="${fbList[0].pagevo.firstPage}" end="${fbList[0].pagevo.lastPage}">
		<a href="main?page=${i}">${i}</a>
		</c:forEach>
		</c:otherwise>
      	 </c:choose>
        <a type="button" id="next">▶</a>
        </div>
        <a href="write">글쓰기</a>
        <form id="search" method="get" action="search">
        <input type="hidden" name="page" value="1">
        	<select name="searchCategory">
        		<option value="title">제목</option>
        		<option value="id">작성자</option>
        		<option value="content">내용</option>
        	</select>
        	<input type="text" name="searchContent">
        	<button type="submit">찾기</button>
        </form>
        
       
</body>
</html>