<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="/FoodField/resources/bootstrap/js/user.js"></script>
<div class="container">
<h1 class="text-center">비밀번호 찾기</h1>
<form action="searchPwd" method="post">
	<div style="margin-bottom: 20px">
	아이디
	<input type="text" class="form-control" name="email" style="margin-bottom: 20px;">
	이름
	<input type="text" class="form-control" name="name" style="margin-bottom: 20px;">
	전화번호
	<input type="text" class="form-control" name="phone">
	<div style="text-align: right; margin-top: 20px;">
	<button type="submit" class="btn btn-primary" id="searchPwdBtn" style="margin-bottom: 0px"><span class="glyphicon glyphicon-ok"></span> 확인</button>
	</div>
	</div>
</form>
</div>
