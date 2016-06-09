$(function() {
	$('#loginBtn').on('click', function() {
		$.ajax({
			url : 'loginProcess',
			type : 'post',
			data : $('#loginForm').serialize(),
			success : function(res) {
				if(res == 'true') {
					location.href = 'main';
				} else {
					$('#loginMsg').text('아이디 또는 비밀번호가 틀렸습니다.');
					$('#loginMsg').css('color', 'red');
				}
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	});
	
	$('.pwd').on('change', function() {
		if($('#pwd').val() == $('#pwdCheck').val() && $('#pwd').val() != "") {
			$('#joinPwdMsg').css('color', 'blue');
			$('#joinPwdMsg').text('비밀번호 일치');
		} else if($('#pwd').val() == "") {
			$('#joinPwdMsg').text('');
		} else {
			$('#joinPwdMsg').css('color', 'red');
			$('#joinPwdMsg').text('비밀번호 불일치');
		}
	});
	
	$('#joinBtn').on('click', function() {
		$('#joinErrMsg').css('color', 'red');
		if($('#joinPwdMsg').text() == '' || $('#joinPwdMsg').text() == '비밀번호 불일치') {
			$('#joinErrMsg').text('비밀번호를 확인하세요');
		}
	});
});