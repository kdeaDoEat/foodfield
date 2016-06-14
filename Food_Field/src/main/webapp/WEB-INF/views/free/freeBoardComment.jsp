<%@page import="org.kdea.vo.UserVO"%>
<%@page import="org.kdea.vo.CommentVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<table class="table" id="tbodyTable">
	<thead class="thead-inverse" id="Th">
		<tr>
			<th>comment</th>
		</tr>
	</thead>
	<tbody id="body">
		<c:forEach var="comment" items="${list}">
			<tr id="tr${comment.cnum }">
				<th scope="row">${comment.nickname}</th>
				<td id="content${comment.cnum }">${comment.contents}</td>
				<td>${comment.w_date}</td>
				<td id="tdbtn${comment.cnum }">
				  <c:if test="${comment.nickname ==sessionScope.userInfo.nickname}">
					<div id="modifyIcon${comment.cnum }" class="divIcon" style="visibility: visible;">
						<span class="glyphicon glyphicon-edit"
							style="font-size: 15px; cursor: pointer;"
							onclick="commentmodi(${comment.cnum });"></span>
					</div>
					</c:if>
					<c:if test="${comment.nickname==sessionScope.userInfo.nickname}">
					<div id="deleteIcon${comment.cnum }" class="divIcon" style="visibility: visible;">
						<span class="glyphicon glyphicon-remove"
							style="font-size: 15px; cursor: pointer;"
							onclick="commentdel(${comment.cnum });"></span>
					</div>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>