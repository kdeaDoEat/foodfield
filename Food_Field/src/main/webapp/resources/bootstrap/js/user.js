$(function() {
	// 로그인 화면에서 확인 버튼 클릭 시 작동
	$('#loginBtn').on('click', function() {
		$.ajax({
			url : 'loginProcess',
			type : 'post',
			data : $('#loginForm').serialize(),
			success : function(res) {
				if(res == 'true') {
					location.href = '/FoodField/main';
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
	
	// 회원가입 화면에서 비밀번호 입력값 변경 시 작동
	$('.pwd').on('change', function() {
		if($('#pwd').val() == $('#pwdCheck').val() && $('#pwd').val() != "") {
			$('#checkPwd').val('true');
			$('#joinPwdMsg').css('color', 'green');
			$('#joinPwdMsg').text('비밀번호 일치');
		} else if($('#pwd').val() == "") {
			$('#joinPwdMsg').text('');
		} else {
			$('#checkPwd').val('false');
			$('#joinPwdMsg').css('color', 'red');
			$('#joinPwdMsg').text('비밀번호 불일치');
		}
	});
	
	// 회원가입 화면에서 회원가입 버튼 클릭 시 작동
	$('#joinBtn').on('click', function() {
		$('#joinErrMsg').css('color', 'red');
		$('#joinErrMsg').text('');
		if($('#checkEmail').val() == 'false') {
			alert('아이디를 확인하세요');
//			$('#joinErrMsg').text('아이디를 확인하세요');
			return;
		} else if($('#checkPwd').val() == 'false') {
			alert('비밀번호를 확인하세요');
//			$('#joinErrMsg').text('비밀번호를 확인하세요');
			return;
		} else if($('#checkNick').val() == 'false') {
			alert('닉네임을 확인하세요');
//			$('#joinErrMsg').text('닉네임을 확인하세요');
			return;
		} else if($('#joinForm input[name=name]').val() == '') {
			alert('이름을 확인하세요');
//			$('#joinErrMsg').text('정보를 모두 입력하세요');
			return;
		} else if($('#joinForm input[name=phone]').val() == '') {
			alert('전화번호를 확인하세요');
//			$('#joinErrMsg').text('정보를 모두 입력하세요');
			return;
		}
		
		if(!confirm('회원가입 하시겠습니까 ?'))
			return;
		
		$.ajax({
			url : 'join',
			type : 'post',
			data : $('#joinForm').serialize(),
			success : function(res) {
				if(res == 'true') {
					alert('회원가입에 성공했습니다 !');
					location.href = 'main';
				} else {
					alert('회원가입에 실패했습니다.');
				}
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	});
	
	// 회원가입, 마이페이지에서 닉네임 입력값 변경 시 작동
	$('#nickname').on('change', function() {
		$('#checkNick').val('false');
	});
	
	// 회원가입, 마이페이지에서 닉네임 중복검사 버튼 클릭 시 작동
	$('#nickBtn').on('click', function() {
		if($('#nickname').val() == '') {
			$('#checkNick').val('false');
			alert('닉네임을 입력하세요.');
			return;
		}
		$.ajax({
			url : 'checkNick',
			type : 'post',
			data : {nickname : $('#nickname').val(),
					email : $('#email').val()},
			success : function(res) {
				if(res == 'true') {
					alert('같은 닉네임이 존재합니다.');
					$('#checkNick').val('false');
				} else {
					alert('사용할 수 있는 닉네임 입니다.');
					$('#checkNick').val('true');
				}
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	});
	
	// 마이페이지에서 회원정보 변경 확인 버튼 클릭 시 작동
	$('#modifyInfoBtn').on('click', function() {
		if($('#modifyInfoForm  #checkNick').val() != 'true') {
			alert('닉네임 중복을 확인하세요');
			return;
		} else if($('#modifyInfoForm  #name').val() == '') {
			alert('이름을 입력하세요');
			return;
		} else if($('#modifyInfoForm  #nickname').val() == '') {
			alert('닉네임을 입력하세요');
			return;
		} else if($('#modifyInfoForm  #phone').val() == '') {
			alert('전화번호를 입력하세요');
			return;
		} else if(!confirm('정말 수정하시겠습니까 ?')) {
			return;
		}
		$.ajax({
			url : 'modifyUserInfo',
			type : 'post',
			data : $('#modifyInfoForm').serialize(),
			success : function(res) {
				if(res == 'true') {
					alert('수정 성공!');
					location.href = 'modifySuccess'
				} else {
					alert('수정 실패!');
				}
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	});
	
	// 회원가입 화면에서 이메일 중복검사 버튼 클릭 시 작동
	$('#emailBtn').on('click', function() {
		if($('#joinId').val() == '') {
			$('#checkEmail').val('false');
			$('#joinIdMsg').css('color', 'red');
			$('#joinIdMsg').text('아이디를 입력하세요.');
			return;
		}
		$.ajax({
			url : 'checkEmail',
			type : 'post',
			data : {email : $('#joinId').val()},
			success : function(res) {
				if(res == 'true') {
					$('#checkEmail').val('true');
					$('#joinIdMsg').css('color', 'green');
					$('#joinIdMsg').text('아이디를 사용할 수 있습니다.');
				} else {
					$('#checkEmail').val('false');
					$('#joinIdMsg').css('color', 'red');
					$('#joinIdMsg').text('같은 아이디가 이미 존재합니다.');
				}
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
	});
	
	// 회원가입 화면에서 이메일 입력값 변경 시 작동
	$('#joinId').on('change', function() {
		$('#joinIdMsg').text('');
		$('#checkEmail').val('false');
	})
});

function goMain() {
	location.href = "main";
}