<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/FoodField/resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script><!-- Smart Editor 2.0 -->

<!-- <div style="height:100px;">겹치는부분</div> -->
<div style="width:70%; margin:auto; margin-top:100px; margin-bottom:100px;">
<form action="qnainsert" method="post">
	<input type="hidden"name="${_csrf.parameterName}"value="${_csrf.token}"/>
	<input type="hidden" name="ref" value="${num}"/>
	<table>
		<tr>
			<td colspan="2" style="text-align: center;">
				<h1>Q&A 글쓰기</h1><br>
			</td>
		</tr>
		<tr>
			<th style="width:10%; text-align:center;">제목</th>
			<td style="padding-bottom:10px; width:60%;"><input type="text" id="title" name="title" size="150"/></td>
		</tr>
		<tr>
			<th style="text-align:center;">내용</th>
			<td><textarea name="contents" id="contents" rows="10" cols="150">Fucking Smart Editor2</textarea></td>
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
var oEditors = [];
nhn.husky.EZCreator.createInIFrame({
    oAppRef: oEditors,
    elPlaceHolder: "contents",
    sSkinURI: "/FoodField/resources/editor/SmartEditor2Skin.html",
    fCreator: "createSEditor2",
    htParams :
    { 
    	fOnBeforeUnload : function(){
    		
		}
	}
});

function cancel(){
	location.href="qna";
}
</script>