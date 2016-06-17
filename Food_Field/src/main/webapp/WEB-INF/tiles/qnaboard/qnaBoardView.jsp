<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<script type="text/javascript"	src="http://code.jquery.com/jquery-2.2.2.min.js"></script>
<script>
    $(function(){
    	$("#listbtn").on("click",goList);
    	$("#replbtn").on("click",goAnswer);
    	$("#delete").on("click",delAjax);
    	$("#modifybtn").on("click",modify);
    });
    
    function goList(){
    	location.href="qna?num="+${board.num};  	
    }
    
	function goAnswer() {
		location.href = "qnawriteForm?num="+${board.num};
	}
     
    function delAjax(){
    	var data = $("#infoform").serialize();
    	$.ajax({
    		url:"qnadelConfirm",
    		data:data,
    		dataType:"json",
    		type:"post",
    		success:function(obj){
    			if(obj.parent){
    				var c = confirm("부모글이어서 삭제가 가능합니다. 삭제하시겠습니까?");
    				if(c){
    				  location.href="qnadel?num="+$("input[name='num']").val();
    				}
    			}else{	
    				alert("부모글이어서 삭제가 불가능합니다. 자식을 먼저 삭제하세요~");
    			}	
    		},
    		error:function(){}
    	})
    }
    
    function modify(){
    	//alert("Script 01");
    	//$("#infoform").submit();
    	location.href="qnamodifyForm?num="+${board.num};
    }
</script>



<div style="height:100px;">겹치는부분</div>

<div style="width:70%; margin:auto;">
	<table id="view">
		<tr><th colspan="2"><h1>${board.title}</h1></th></tr>
		<tr>
			<th style="text-align: left;"><h6><span class="glyphicon glyphicon-user"> ${board.nickname} / <span class="glyphicon glyphicon-calendar"> ${board.w_date}</h6></th>
			<th style="text-align: right;"><h6><span class="glyphicon glyphicon-eye-open"></span> | ${board.hit}</h6></th>
		</tr>
		<tr><td colspan="2"><hr></td></tr>
		<tr><td colspan="2">${board.contents}</td></tr>
		<tr><td colspan="2"><br><br><br></td></tr>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<tr><td colspan="2" style="text-align:center;"><button type="button" id="replbtn" class="btn btn-info btn-sm"><span class="glyphicon glyphicon-comment"></span> Answer</button></td></tr>
		</sec:authorize>
	</table>
	
	<br><br><br><br><br>
	
	<div class="btn-group" style="float:right;">
		<button type="button" id="listbtn" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-th-list"></span> List</button>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		<button type="button" id="modifybtn" class="btn btn-warning btn-sm"><span class="glyphicon glyphicon-edit"></span> Modify</button>
		<button type="button" id="delete" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash"></span> Delete</button>
		</sec:authorize>
	</div>
	
	<br><br><br><br><br>
<%-- 
	<!-- Now Page Info submit ModifyForm -->
	<form action="qnamodifyForm" style="margin-left: 25%; display:inline;" id="infoform">
		<input type="hidden" name="num" value="${board.num}" /> 
		<input type="hidden" name="title" value="${board.title}" /> 
		<input type="hidden" name="w_date" value="${board.w_date}" /> 
		<input type="hidden" name="contents" value="${board.contents}" />
	</form>
--%>
</div>



