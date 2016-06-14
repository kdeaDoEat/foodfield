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
		<c:forEach var="cList" items="${list}">
			<tr id="tr${cList.cnum }">
				<th scope="row">${cList.nickname}</th>
				<td id="content${cList.cnum }">${cList.contents}</td>
				<td>${cList.w_date}</td>
				<td id="tdbtn${cList.cnum }"><div id="modifyIcon${cList.cnum }"
						class="divoIcon">
						<span class="glyphicon glyphicon-edit"
							style="font-size: 15px; cursor: pointer;"
							onclick="commentmodi(${cList.cnum });"></span>
					</div>
					<div id="deleteIcon${cList.cnum }" class="divoIcon">
						<span class="glyphicon glyphicon-remove"
							style="font-size: 15px; cursor: pointer;"
							onclick="commentdel(${cList.cnum });"></span>
					</div></td>
			</tr>
		</c:forEach>
		<tr id="first"></tr>
	</tbody>
</table>