<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table class = "table">
	<!--  닉네임 , 내용 , 날짜 -->
   <tbody>
   		<c:forEach var="list" items="${list }">
   		<input type="hidden" style="display: none" value="${list.num }" class="boardNum">
				<tr>
					<td style="width: 20%">${list.nickname }</td>
					<td style="width: 60%;text-align: left">
						<span id="${list.cnum }originalContents" style="visibility: visible; display: block;">${list.contents }</span>
						<input type="text" value="${list.contents }" id="${list.cnum }newContents" style="visibility: hidden; width: 100%; display: none;">
						</td>
					<td style="width: 10%">${list.w_date }</td>
					<td style="width: 10%;">
						<span id="${list.cnum }editIcon" class="glyphicon glyphicon-edit" onclick="edit(${list.cnum});" style="cursor: pointer; display: inline-block; visibility: visible;"></span>
						<span id="${list.cnum }okIcon" class="glyphicon glyphicon-ok" onclick="editok(${list.cnum});" style="cursor: pointer; display: none; visibility: hidden;"></span>
						　<span id="${list.cnum }delIcon" class="glyphicon glyphicon-remove" onclick="del(${list.cnum});" style="cursor: pointer; display: inline-block; visibility: visible;"></span>
					</td>
				</tr>
		</c:forEach>
   </tbody>
</table>
