<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript" src="/FoodField/resources/ckeditor/ckeditor.js" charset="utf-8"></script><!--  CKEditor -->
<!-- <div style="height:100px;">겹치는부분</div> -->
<div style="width:80%; margin:auto; margin-top:100px; margin-bottom:100px;">
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
			<th style="width:20%; text-align:center;">제목</th>
			<td style="padding-bottom:10px; width:60%;"><input type="text" id="title" name="title" size="150"/></td>
		</tr>
		<tr>
			<th style="text-align:center;">내용</th>
			<td><textarea name="contents" id="contents" class="ckeditor" rows="10" cols="150">All of the free templates and themes on Start Bootstrap are now licensed under the MIT license instead of Apache 2.0. The MIT license is simple, and it allows you to do just about anything you want with the templates. In a nutshell, with the MIT license you can use any of the templates or themes on Start Bootstrap for commercial or private use, and you can distribute and/or modify the templates as you wish. Every template on Start Bootstrap has a custom CSS file, and some have a custom JS file, which has a comment in the first few lines of code. For example, if you open the custom CSS for the Agency theme youÃÂ¢ÃÂÃÂll find this in the first few lines: /*! * Agency v1.0.7 (http://startbootstrap.com/template-overviews/agency) * Copyright 2013-2016 Start Bootstrap * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE) */ In order to safely use the MIT license, just keep that little snippet in the CSS and JS and youÃÂ¢ÃÂÃÂre good to go! The whole point of Start Bootstrap is to remain open source, and to keep the templates and themes useable and extendable to a broad range of users. Switching to the MIT license is simply another step in this direction.</textarea></td>
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