<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$('#modifyInfoForm #nickBtn').on('click', function() {
		if($('#modifyInfoForm #nickname').val() == '') {
			$('#modifyInfoForm #checkNick').val('false');
			alert('닉네임을 입력하세요.');
			return;
		}
		$.ajax({
			url : 'checkNick',
			type : 'post',
			data : {nickname : $('#modifyInfoForm #nickname').val(),
					email : $('#modifyInfoForm #email').val()},
			success : function(res) {
				if(res == 'true') {
					alert('같은 닉네임이 존재합니다.');
					$('#modifyInfoForm #checkNick').val('false');
				} else {
					alert('사용할 수 있는 닉네임 입니다.');
					$('#modifyInfoForm #checkNick').val('true');
				}
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	});
	
	$('#modifyInfoForm #nickname').on('change', function() {
		$('#modifyInfoForm #checkNick').val('false');
	});
});
</script>
<!-- Password Change Modal -->
<div class="modal fade" id="changePwdModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">비밀번호 변경</h4>
			</div>
			<form method="post" id="changePwdForm">
			<div class="modal-body">
				<input type="hidden" name="email" value="${user.email }">
				기존 비밀번호<input type="password" name="oldPwd" class="form-control" style="margin-bottom: 10px">
				새 비밀번호<input type="password" name="newPwd" id="newPwd" class="form-control" style="margin-bottom: 10px">
				새 비밀번호 확인<input type="password" name="newPwdCheck" id="newPwdCheck" class="form-control" style="margin-bottom: 10px">
				<div style="width: 100%; text-align: right;">
				<span id="changePwdMsg" style="padding-right: 60px"></span>
				<button type="button" id="changePwdBtn" class="btn btn-primary" style="margin-bottom: 0px"><span class="glyphicon glyphicon-ok"></span> 변경</button>
				</div>
			</div>
			<div class="modal-footer" style="text-align: center;">
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</div>
</div>
<div class="container">
<form action="modifyUserInfo" method="post" id="modifyInfoForm">
	아이디 <input type="text" class="form-control" id="email" name="email" style="margin-bottom: 10px" value="${user.email }" readonly><br>
	이름 <input type="text" class="form-control" id="name" name="name" style="margin-bottom: 10px" value="${user.name }"><br>
	닉네임
	<div class="form-group input-group">
	<input type="text" class="form-control" id="nickname" name="nickname" value="${user.nickname }">
	<input type="hidden" id="checkNick" value="true">
		<span class="input-group-btn">
			<button class="btn btn-default" type="button" id="nickBtn" style="margin-right: 0px"><i class="glyphicon glyphicon-search"></i></button>
		</span>
	</div>
	전화번호 <input type="text" class="form-control" id="phone" name="phone" style="margin-bottom: 10px" value="${user.phone }"><br>
	성별 <input type="text" class="form-control" name="gender" style="margin-bottom: 10px" value="${user.gender }" readonly><br>
	포인트 <input type="text" class="form-control" name="point" style="margin-bottom: 10px" value="${user.point }" readonly><br>
	<div style="text-align: right;">
	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#changePwdModal"><span class="glyphicon glyphicon-edit"></span> 비밀번호 변경</button>
	<button type="button" class="btn btn-primary" id="modifyInfoBtn"><span class="glyphicon glyphicon-ok"></span> 확인</button>
	<button type="button" class="btn btn-default" onclick="goMain()"><span class="glyphicon glyphicon-remove"></span> 취소</button>
	</div>
</form>
</div>
