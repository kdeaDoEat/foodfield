<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<script type="text/javascript"	src="http://code.jquery.com/jquery-2.2.2.min.js"></script>
<script type="text/javascript" src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script><!--  CKEditor -->


<div style="margin-top:100px;margin-bottom:100px;" class="container">
	<form action="qnamodify" method="post"  id="modform">
		<input type="hidden" value="${board.num}" name="num"/>
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}"/>
		<!-- title -->
		<div class="col-md-11 center-block" style="text-align: center;">
			<h2>Q&A MODIFY</h2>
			<hr>
		</div>
		<table  class="col-md-11">
			<tr>
				<th style="text-align:center;" class="col-md-2 col-xs-2">제목</th>
				<td style="padding-bottom:10px;" class="col-xs-16"><input type="text" id="title" name="title" style="width:100%;" value="${board.title}"/></td>
			</tr>
			<tr>
				<th style="text-align:center;">내용</th>
				<td><textarea name="contents" id="contents"  class="ckeditor"  rows="10" style="width:100%;">${board.contents}</textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;">
					<br>
					<button type="button" id="modifybtn" class="btn btn-warning btn-sm">Save</button>
					<button type="button" id="cancelbtn" class="btn btn-danger btn-sm">Cancel</button>
				</td>
			</tr>
		</table>
	</form>
</div>
<br>

<script>
$(function() {
	$("#modifybtn").on("click",modsubmitgo);
	$("#cancelbtn").on("click",cancel);
});

function modsubmitgo(){
	//alert($("#title").val()+"/"+$("#contents").val());
	var result = confirm("이대로 수정하시겠습니까?");
	if(result){
		$("#modform").submit();
	}
}

function cancel(){
	location.href="qnaview?num="+${board.num};
}
</script>

