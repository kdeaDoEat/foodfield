<%@page import="org.kdea.vo.FreeBoardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String cp = request.getContextPath(); 
/* request.setCharacterEncoding("utf-8"); */%>
<%--ContextPath 선언 --%>

<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
<% 
String writeok=(String)session.getAttribute("w");
String modifyok=(String)session.getAttribute("m");
//System.out.println("글 성공: "+writeok+", 글수정: "+modifyok);

if(writeok!=null||modifyok!=null){
	if(writeok.equals("success")){
		%>
		alert("글이 성공적으로 저장되었습니다.");
		<% 
	}else if(modifyok.equals("success")){
			%>
			alert("글이 성공적으로 변경되었습니다.")
			<%
	}
}
%>
$(function(){
	$('#commentBtn').on('click',comment);
	$('#reply').on('click',reply);
	$('#del').on('click', function(){
	if(confirm("정말 삭제하시겠습니까?")){
		var delform=$('#bform').serialize();
		
		$.ajax({
			type:"post",
			url:"beforeDelete",
			data:delform,
			dataType:"json",
			success:function(obj){
				if(obj.isParents){
					alert("답글이 있어 삭제하실 수 없습니다.");
				}
				else{
					
					location.href="delete?num=${wvalue.num}"
				}
			},
			error:function(xhr,status,error){
				
			}
			
		});
	}
	});
});
function reply(){
	location.href="replyForm?num=${wvalue.num}";
}
function comment(){
	$('#comment').css('visibility','visible');
}
function commentmodi(num){

	console.log('수정할 글번호: '+num);
	var obj={};
	obj.number=num;
	$.ajax({
		type:"post",
		url:"commentmodi",
		data:obj,
		dataType:"json",
		success:function(ob){
			console.log('읽어오기 성공'+ob.Ccontent+", 글번호: "+num);
			$('#content'+num).removeAttr("disabled");
			$('#mbtn'+num).hide();
			$('#dbtn'+num).hide();
			var str = '<input type="button" id="modi'+num+'" value="확인" onclick="comodify('+num+')" />';
			$('#tdbtn'+num).append(str);
		},
		error:function(xhr,status,error){
			alert(error);
		},
		complete:function(data){
			//console.log('컴플리트로 온 내용: '+data)
		}
		
	});
	
}
 function commentdel(num){
	 console.log('삭제할 번호: '+num);
		var jobj={ };
		jobj.num=num;
		
		$.ajax({
			type:"post",
			url:"commentDelete",
			data:jobj,
			dataType:"json",
			success:function(obj){
				console.log(obj.Cdelsuc);
				
				if(obj.Cdelsuc){
					console.log('글삭제 성공');
					$('#tr'+num).remove();
				}
				
			},
			error:function(xhr,status,error){
				
			}
			
		});
} 
function comodify(num){
	console.log('동적으로 이벤트가 주어졌는가? '+$('#content'+num).val());
	var commentval=$('#content'+num).val();
	var jobj= {};
	jobj.contents=commentval;
	jobj.num=num;
	
	$.ajax({
		type:"post",
		url:"comodify",
		data:jobj,
		dataType:"json",
		success:function(obj){
			console.log(obj.Ccontent);
			$('#content'+num).val(obj.Ccontent);
			$('#content'+num).attr("disabled","disabled");
			$('#modi'+num).remove();
			$('#mbtn'+num).show();
			$('#dbtn'+num).show();
		},
		error:function(xhr,status,error){
			
		}
		
	});
	
}
</script>
<style>
.detail{display: inline;}
</style>
<body>
	<h2 id="bigTitle">글읽기</h2>
	<form id="bform" name="input" method="post" action="modify">
		<input type="hidden" name="nickname" value="${wvalue.nickname }"> <input
			type="hidden" name="num" value=" ${wvalue.num }">
		<p>
		<div class="detail">글번호: ${wvalue.num }</div><div class="detail">조회수: ${wvalue.views}</div>
		<div>글제목</div>
		<input type="text" name="title" id="title" value="${wvalue.title} "
			disabled="disabled">

		<p>
			<textarea name="contents" id="content" disabled="disabled">
${wvalue.contents}
</textarea>
		<p>
			<button type="submit">수정</button>
			<button type="button" id="commentBtn">댓글쓰기</button>
			<button type="button" id="del">삭제</button>
			<button type="button" id="reply">답글 쓰기</button>
	</form>

	

	<div id="commentList">
		<form id="clForm">
		<table id="commentTable">
			<c:forEach var="cList" items="${cvalue}">
				<tr id="tr${cList.cnum }">
					<td><input type="hidden" name="num" disabled="disabled" value="${cList.nickname}"></td>
					<td><input type="text" disabled="disabled" value="${cList.nickname}"></td>
					<td><input type="text" id="content${cList.cnum }" name="conent" disabled="disabled" value="${cList.contents}"></td>
					<td><input type="text" disabled="disabled" value="${cList.w_date}"></td>
					<td id="tdbtn${cList.cnum }"><button type="button" id="mbtn${cList.cnum }" onclick="commentmodi(${cList.cnum });">수정</button></td>
					<td><button type="button" id="dbtn${cList.cnum }" onclick="commentdel(${cList.cnum });">삭제</button></td>
				</tr>
			</c:forEach> 
			
		</table>
		</form>
	</div>


	<div id="comment" style="visibility: hidden">
		<form name="input" method="post" action="comment">
			<input type="hidden" name="nickname" value="${wvalue.nickname }"> 
				<input	type="hidden" name="num" value=" ${wvalue.num }">
			<p>
				<textarea name="contents">
코멘트 내용
</textarea>
			<p>
				<button type="submit">댓글 저장</button>
		</form>
	</div>


