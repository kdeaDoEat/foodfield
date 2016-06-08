<%@page import="org.kdea.vo.FreeBoardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String cp = request.getContextPath();
	String parentnum = request.getParameter("num");
	if (parentnum == null) {
		parentnum = "0";

	}
	session.setAttribute("ref", parentnum);
%>
<%--ContextPath 선언 --%>
<script type="text/javascript" src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	CKEDITOR.replace('editor',{//해당 이름으로 된 textarea에 에디터를 적용
		filebrowserImageUploadUrl:'/FoodField/uploadPhoto'//여기 경로로 파일을 전달하여 업로드 시킨다.
	});
	
    
    CKEDITOR.on('dialogDefinition', function( ev ){
        var dialogName = ev.data.name;
        var dialogDefinition = ev.data.definition;
      
        switch (dialogName) {
            case 'image': //Image Properties dialog
                dialogDefinition.removeContents('info');
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
						<textarea id="editor" style="width:100%; height:400px;"></textarea>
					</div>
				</div>
				<!-- <input type="file" name="photo"> -->
				<div class="form-group">
					<div class="col-sm-10 col-sm-offset-2" style="text-align: center;">
						<button class="btn icon-btn btn-default" type="submit">
							<span
								class="glyphicon btn-glyphicon glyphicon-pencil img-circle text-muted"></span>저장
						</button>

					</div>
				</div> 
			</form>
		</div>
	</div>
</div>