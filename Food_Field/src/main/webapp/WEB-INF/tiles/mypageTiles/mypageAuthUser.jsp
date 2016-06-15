<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container">
<h3>비밀번호를 입력하세요</h3>
<form action="authUser" method="post" class="form-inline">
	<input type="hidden" name="email" value="${sessionScope.userInfo.email }">
<!-- 	<input type="password" name="pwd"> -->
	<div style="margin-bottom: 20px">
	<input type="password" class="form-control" name="pwd">
	<button type="submit" class="btn btn-primary" id="authUserBtn" style="margin-bottom: 0px"><span class="glyphicon glyphicon-ok"></span> 확인</button>
	</div>
</form>
<c:if test="${error == 'true' }">
<strong style="color: red">비밀번호가 일치하지 않습니다.</strong>
</c:if>
</div>
