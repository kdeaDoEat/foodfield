<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원랭킹</title>
</head>
<body>
<div style="margin-top:150px; width:100%;">
<table style=" margin-left:auto; margin-right:auto;" class="table">
    <tr>
      <th style="text-align:center;">순위</th>
      <th style="text-align:center;">이메일</th>
      <th style="text-align:center;">닉네임</th>
      <th style="text-align:center;">이름</th>
      <th style="text-align:center;">포인트</th>
    </tr>
	<c:forEach var="user" items="${userlist}" varStatus="status">
		<tr>
		    <td style="text-align:center;">${user.rank}</td>
			<td>${user.email}</td>
			<td>${user.nickname}</td>
			<td>${user.name}</td>
			<td>${user.point}</td>
		</tr>
	</c:forEach>
</table>
</div>
</body>
</html>