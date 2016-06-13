<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<script type="text/javascript"	src="http://code.jquery.com/jquery-2.2.2.min.js"></script>
<script type="text/javascript" src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script><!--  CKEditor -->


<div style="width:80%; margin:auto; margin-top:100px">
	<form action="qnamodify" method="post"  id="modform">
		<input type="hidden" value="${board.num}" name="num"/>
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}"/>
		<table>
			<tr>
				<td colspan="2" style="text-align: center;">
					<h1>Q&A 글쓰기</h1><br>
				</td>
			</tr>
			<tr>
				<th style="width:20%; text-align:center;">제목</th>
				<td style="padding-bottom:10px; width:60%;"><input type="text" id="title" name="title" size="150" value="${board.title}"/></td>
			</tr>
			<tr>
				<th style="text-align:center;">내용</th>
				<td><textarea name="contents" id="contents"  class="ckeditor"  rows="10" cols="150">${board.contents}</textarea></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:center;">
					<br>
					<button type="button" id="modifybtn">작성</button>
					<button type="button" id="cancelbtn">취소</button>
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

