<%@page import="org.kdea.vo.FreeBoardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String cp = request.getContextPath();
	String pref= (String)request.getParameter("num");
	System.out.println("넘어온 num값: "+pref);
	if(pref==null){
		pref="0";
		System.out.println("넘어온 pref값이 널일때: "+pref);
		int ref= Integer.parseInt(pref);
		System.out.print("ref: "+ref);
		session.setAttribute("ref", ref);
	}else{
		int ref= Integer.parseInt(pref);
		System.out.print("ref: "+ref);
		session.setAttribute("ref", ref);
	}
%>
<%--ContextPath 선언 --%>
<script type="text/javascript"
	src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		CKEDITOR
				.replace(
						'editor',
						{//해당 이름으로 된 textarea에 에디터를 적용
							filebrowserImageUploadUrl : 'uploadPhoto?${_csrf.parameterName}=${_csrf.token}'//여기 경로로 파일을 전달하여 업로드 시킨다.
						});
		var data = CKEDITOR.instances.editor.getData();
		console.log(data);
		CKEDITOR.on('dialogDefinition', function(ev) {
			var dialogName = ev.data.name;
			var dialogDefinition = ev.data.definition;

			switch (dialogName) {
			case 'image': //Image Properties dialog
				//dialogDefinition.removeContents('info');
				dialogDefinition.removeContents('Link');
				dialogDefinition.removeContents('advanced');
				break;
			}
		});
	});
</script>
<div class="container">
	<div class="row">
		<div class="col-lg-12">
			<form class="form-horizontal" method="post" action="winput">
				<input type="hidden" name="nickname" value="${sessionScope.userInfo.nickname }">
 				<input type="hidden" name="ref" value="${ref}">
				<p>
					<!--  제목 부분  -->
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">제목</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="title">
					</div>
				</div>

				<!-- 내용 부분 -->

				<div class="form-group">
					<label for="message" class="col-sm-2 control-label">내용</label>
					<div class="col-sm-10">
						<textarea name="contents" id="editor"
							style="width: 100%; height: 400px;">
						</textarea>
					</div>
				</div>
				<p>
					<!-- <input type="file" name="photo"> -->
				<div class="form-group">
					<div class="col-sm-10 col-sm-offset-2" style="text-align: center;">
						<button class="btn icon-btn btn-default" type="submit">
							<span
								class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted"></span>
							저장
						</button>

					</div>
				</div>
				<!-- <button type="submit" id="btn">저장</button> -->
			</form>

		</div>
	</div>
</div>