<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">

</script>
<!-- Login Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Login</h4>
			</div>
			<form action="loginProcess" method="post">
			<div class="modal-body">
				<input type="text" name="email" class="form-control" style="margin-bottom: 10px" placeholder="example@example.com">
				<input type="password" name="pwd" class="form-control" style="margin-bottom: 10px">
				<div style="width: 100%; text-align: right;">
				<button type="submit" class="btn btn-primary" style="margin-bottom: 0px"><span class="glyphicon glyphicon-ok"></span> 로그인</button>
				</div>
			</div>
			<div class="modal-footer">
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</div>
</div>
<!-- Join Modal -->
<div class="modal fade" id="joinModal" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span><span class="sr-only">Close</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">Sign Up</h4>
			</div>
				<form action="join" method="post">
			<div class="modal-body">
				아이디<input type="text" name="email" class="form-control" style="margin-bottom: 10px" placeholder="example@example.com">
				비밀번호<input type="password" name="pwd" class="form-control" style="margin-bottom: 10px">
				비밀번호 확인<input type="password" name="pwdCheck" class="form-control" style="margin-bottom: 10px">
				이름<input type="text" name="name" class="form-control" style="margin-bottom: 10px">
				닉네임<input type="text" name="nickname" class="form-control" style="margin-bottom: 10px">
				전화번호<input type="text" name="phone" class="form-control" style="margin-bottom: 10px">
				성별  남<input type="radio" name="gender" value="man"> 여<input type="radio" name="gender" value="woman">
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary" style="margin-bottom: 0px"><span class="glyphicon glyphicon-ok"></span> 회원가입</button>
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
		</div>
	</div>
</div>
<header id="fh5co-header" role="banner">
		<nav class="navbar navbar-default" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header"> 
				<!-- Mobile Toggle Menu Button -->
				<a href="#" class="js-fh5co-nav-toggle fh5co-nav-toggle" data-toggle="collapse" data-target="#fh5co-navbar" aria-expanded="false" aria-controls="navbar"><i></i></a>
				<a class="navbar-brand" href="/FoodField/main">FoodField</a>
				</div>
				<div id="fh5co-navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav navbar-left">
						<li class="dropdown">
		            			<a href="#" class="dropdown-toggle" data-toggle="dropdown">Board <b class="caret"></b></a>
		            			<ul class="dropdown-menu">
		              				<li><a href="/FoodField/notice">공지사항<span class="border"></span></a></li>
		              				<li><a href="/FoodField/free?page=1">자유게시판<span class="border"></span></a></li>
		              				<li><a href="/FoodField/qna">QnA<span class="border"></span></a></li>
		              				<!-- <li class="divider"></li>
		              				<li class="dropdown-header">Nav header</li> -->
		            			</ul>
         				</li>
         				<li><a href="right-sidebar.html"><span>미사용 <span class="border"></span></span></a></li>
						<li><a href="/FoodField/review"><span>맛집리뷰 <span class="border"></span></span></a></li>
						<li><a href="/FoodField/doit"><span>오늘의 메뉴 <span class="border"></span></span></a></li>
					
					</ul>
					
					<ul class="nav navbar-nav navbar-right">
						<sec:authorize access="isAnonymous()">
						<li><a href="#loginModal" data-toggle="modal"><span>login<span class="border"></span></span></a></li>
						<li><a href="#joinModal" data-toggle="modal"><span>sign up<span class="border"></span></span></a></li>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
						<li><a href="logout"><span>logout<span class="border"></span></span></a></li>
						</sec:authorize>
						<!-- <li><a href="left-sidebar.html"><span>Left Sidebar <span class="border"></span></span></a></li>
						<li><a href="elements.html"><span>Elements <span class="border"></span></span></a></li> -->
					</ul>
				</div>
			</div>
			
		</nav>
</header>