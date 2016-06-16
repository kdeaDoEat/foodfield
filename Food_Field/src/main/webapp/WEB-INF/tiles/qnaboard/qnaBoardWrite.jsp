<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script><!--  CKEditor -->
<!-- <div style="height:100px;">겹치는부분</div> -->

<div class="container" style="margin-top:100px; margin-bottom:100px;">
<form action="qnainsert" method="post">
	<input type="hidden"name="${_csrf.parameterName}"value="${_csrf.token}"/>
	<input type="hidden" name="ref" value="${num}"/>
	<!-- title -->
	<div class="col-md-11 center-block" style="text-align:center;">
		<h2>Q&A WRITE</h2>
		<hr>	
	</div>
	<table class="col-md-11">
		<tr>
			<th style="text-align:center;" class="col-md-2 col-xs-2">제목</th>
			<td style="padding-bottom:10px;" class="col-xs-16"><input type="text" id="title" name="title" style="width:100%;"/></td>
		</tr>
		<tr>
			<th style="text-align:center;">내용</th>
			<td><textarea name="contents" id="contents" class="ckeditor" rows="10" style="width:100%;"></textarea></td>
		</tr>
		<tr>
			<td colspan="2" style="text-align:center;">
				<br>
				<button type="submit" class="btn btn-warning btn-sm">Save</button>
				<button type="button" onclick="cancel()" class="btn btn-danger btn-sm">Cancel</button>
				<br>
			</td>
		</tr>
	</table>
</form>
</div>
<br>





<script type="text/javascript">
function cancel(){
	location.href="qna";
}
</script>