<%@page import="org.kdea.vo.FreeBoardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String cp = request.getContextPath();
	String parentnum = request.getParameter("num");
	System.out.println("ref: " + parentnum);
	if (parentnum == null) {
		parentnum = "0";
		System.out.println("상귀 글번호가 없을때 ref: " + parentnum);
	}
	session.setAttribute("ref", parentnum);
%>
<%--ContextPath 선언 --%>

<div class="container">
	<div class="row">
		<div class="col-lg-12">
			<form class="form-horizontal" id="bform" name="input" method="post"
				action="winput">
				<input type="hidden" name="nickname" value="jungheeLee"> <input
					type="hidden" name="ref" value="${ref}">
				<p>
					<!--  제목 부분  -->
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">제목</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="title"
							placeholder="input title">
					</div>
				</div>
				<!-- 내용 부분 -->
				<div class="form-group">
					<label for="message" class="col-sm-2 control-label">내용</label>
					<div class="col-sm-10">
						<textarea rows="10" cols="30" id="ir1" name="contents"
						class="form-control" placeholder="Input text" 
							style="width: 100%; height: 412px; min-width: 260px; resize: none;"></textarea>
					</div>
				</div>

			 	<!-- <input type="file" name="photo"> -->
				<!--<div class="form-group">
					<div class="col-sm-10 col-sm-offset-2" style="text-align: center;">
						<button class="btn icon-btn btn-default" type="submit">
							<span
								class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted"></span>저장
						</button>

					</div>
				</div> -->
			</form>
		</div>
	</div>
</div>