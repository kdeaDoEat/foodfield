<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<script type="text/javascript"	src="http://code.jquery.com/jquery-2.2.2.min.js"></script>
<script type="text/javascript" src="/FoodField/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script><!-- Smart Editor 2.0 -->
<script>
$(function() {
	$("#modifybtn").on("click",modsubmitgo);
	$("#cancelbtn").on("click",cancel);
});

function modsubmitgo(){
	alert($("#contents").val());
	var result = confirm("이대로 수정하시겠습니까?");
	if(result){
		$("#modform").submit();
	}
}

function cancel(){
	location.href="qnaview?num="+${board.num};
}
</script>

<div style="width:70%; margin:auto; margin-top:100px">
	<form action="modify" method="post"  id="modform">
		<input type="hidden" value="${board.num}" name="num"/>
		<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}"/>
		<table>
			<tr>
				<td colspan="2" style="text-align: center;">
					<h1>Q&A 글쓰기</h1><br>
				</td>
			</tr>
			<tr>
				<th style="width:10%; text-align:center;">제목</th>
				<td style="padding-bottom:10px; width:60%;"><input type="text" id="title" name="title" size="150" value="${board.title}"/></td>
			</tr>
			<tr>
				<th style="text-align:center;">내용</th>
				<td><textarea name="contents" id="contents" rows="10" cols="150">${board.contents}</textarea></td>
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





<script type="text/javascript">
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "contents",
    sSkinURI: "/FoodField/resources/editor/SmartEditor2Skin.html",
    fCreator: "createSEditor2"
});
</script>















<%-- 
	<form action="qnamodify" method="post"  id="modform">
		<fieldset
			style="width: 380px; display: table; margin-left: auto; margin-right: auto; margin-top: 50px;">

			<legend>수정 하기</legend>
            <input type="hidden" value="${board.num}" name="num"/>
			<label for="title">글 제목</label><br> <input type="text"	id="title" name="title" style="width: 370px;" value="${board.title}"/>
			<br><br><br>			
			<label for="contents">글 내용</label><br>
			<textarea rows="10" cols="50" id="contents" name="contents">${board.contents}</textarea>
			<br>
            <br>
			<button type="button" id="modifybtn" class="btn btn-warning btn-sm">Save</button>
			<br>
			
		</fieldset>
	</form>
 --%>
	