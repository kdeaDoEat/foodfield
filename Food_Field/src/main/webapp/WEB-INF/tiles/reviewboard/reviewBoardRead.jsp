<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$('#ir1').html('${vo.contents}');
})
</script>
<div class="container">
        <div class="row">
            <div class="col-lg-12">
            	<!-- 입력 박스  -->
				<form class="form-horizontal" role="form" method="post" name="inputForm" id="inputForm"
				style=" border: 1px solid black;">
					<!--  제목 부분  -->
					<div class="form-group">
						<label for="name" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<h3><label for="name" class="col-sm-2 control-label">${vo.title}</label></h3>
						</div>
					</div>
					<!-- 내용 부분 -->
					<div class="form-group">
						<div class="col-sm-10" id="ir1" style="width: 80%; border: 1px solid black; margin-left: 10%">
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-10 col-sm-offset-2" style="text-align: center;">
							<a class="btn icon-btn btn-default" href="review/write">
            				<span class="glyphicon btn-glyphicon glyphicon-thumbs-up img-circle text-muted">
            				</span>　추천</a>
            				<a class="btn icon-btn btn-default" href="review/write">
            				<span class="glyphicon btn-glyphicon glyphicon-list img-circle text-muted">
            				</span>　리스트보기</a>
						</div>
					</div>
				</form>
            </div>
        </div>
 </div>