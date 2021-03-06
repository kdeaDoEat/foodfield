﻿<%@page import="org.kdea.vo.CommentVO"%>
<%@page import="org.kdea.vo.UserVO"%>
<%@page import="org.kdea.vo.FreeBoardVO"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String cp = request.getContextPath();
	/* request.setCharacterEncoding("utf-8"); */
%>
<style>
.detail {
	display: inline;
}

.divIcon {
	display: inline;
}
</style>
<script src="http://code.jquery.com/jquery-2.1.1.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
<%String writeok = (String) session.getAttribute("w");
			String modifyok = (String) session.getAttribute("m");

			if (writeok != null) {
				if (writeok.equals("success")) {%>
 
  		alert("글이 성공적으로 저장되었습니다.");
 		<%session.setAttribute("w", null);
				}
			}

			if (modifyok != null) {
				if (modifyok.equals("success")) {%>

  	alert("글이 성공적으로 변경되었습니다.")
 	<%session.setAttribute("m", null);
				}
			}%>
 
 
  $(function(){
  	$('#commentBtn').on('click',comment);
 	// 댓글 읽어 온다
	$('#cmt').load("aftercomment?num=${wvalue.num}");
 	
	var nick = "${wvalue.nickname}";
	var myNick = "${sessionScope.userInfo.nickname}";
	console.log('wvalue.nickname: '+nick+", session.nick: "+myNick);
	if(nick == myNick){
		$('#modifyIcon').css("visibility","visible");
		$('#deleteIcon').css("visibility","visible");
	}

 		
  });
  //End of Ready
  function modify(num){

  	location.href="modify?num="+num;
 	
  }
  function freeboardDelete(pnum){
	
 		$('span#deleteIcon').show();
  	if(confirm("정말 삭제하시겠습니까?")){
  		console.log('상위 글번호: '+pnum);
  		var jobj={};
  		jobj.num=pnum;
 		$.ajax({
 			type:"post",
 			url:"beforeDelete?${_csrf.parameterName}=${_csrf.token}",
 			data:jobj,
 			dataType:"json",
 			success:function(obj){
 				if(obj.isParents){
 					alert("답글이 있어 삭제하실 수 없습니다.");
 				}
 				else{
 					
 					location.href="delete?num=${wvalue.num}&${_csrf.parameterName}=${_csrf.token}"
 				}
 			},
 			error:function(xhr,status,error){
 				
  			}
  			
  		});
 	
  	}
  }
  
  function comment(){
 	var nickname='${sessionScope.userInfo.nickname}';
 	console.log(nickname);
 	if(nickname==""){
 		alert('로그인하셔야 코멘트 입력이 가능합니다.');
	}else{
 	
  	$.ajax({
  		url:'comment?${_csrf.parameterName}=${_csrf.token}',
  		data:$('#commentForm').serialize(),
 		dataType:'json',
  		type:'post',
  		success:function(result){
  			$('textarea[name="contents"]').val('');
  			console.log('코멘트 성공'+result.commentsuc+',최신번호: '+result.cnum);
 			$('#cmt').load("aftercomment?num=${wvalue.num}");
 			//commentRead(result.cnum);
 
  		},error:function(er){
  			alert('에러 : '+er);
  		}
  	});
  }
 }
  
 
  function commentmodi(num){
  
  	console.log('수정할 글번호: '+num);
  	var obj={};
  	obj.number=num;
 	if(confirm("정말 수정하시겠습니까?")){
  	$.ajax({
  		type:"post",
  		url:"commentmodi?${_csrf.parameterName}=${_csrf.token}",
  		data:obj,
  		dataType:"json",
  		success:function(ob){
  			console.log('읽어오기 성공'+ob.Ccontent+", 글번호: "+num);
 			$('#content'+num).text('');
 			$('#content'+num).append('<input type="text" id="inputContents'+num+'" value='+ob.Ccontent+'>');
 			$('#modifyIcon'+num).hide();
 			$('#deleteIcon'+num).hide();
 			var str = '<div id="modi'+num+'"class="divoIcon"><span class="glyphicon glyphicon-check"'+
 			'style="font-size: 15px; cursor: pointer;"'+
 			'onclick="comodify('+num+')"></span></div>'
  			$('#tdbtn'+num).append(str);
  		},
  		error:function(xhr,status,error){
  			alert(error);
 		},
 		complete:function(data){
 		}
  	});
 	}
	
  }
   function commentdel(num){
  	 console.log('삭제할 번호: '+num);
  		var jobj={ };
  		jobj.num=num;
  		
 		if(confirm("정말 삭제하시겠습니까?")){
 			$.ajax({
 	  			type:"post",
 	  			url:"commentDelete?${_csrf.parameterName}=${_csrf.token}",
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
  } 
  function comodify(num){
 	console.log('동적으로 이벤트가 주어졌는가? '+$('input#inputContents'+num).val());
 	var commentval=$('input#inputContents'+num).val();
 	var jsonobj= {};
 	jsonobj.contents=commentval;
 	jsonobj.cnum=num;
  	
  	$.ajax({
  		type:"post",
  		url:"comodify?${_csrf.parameterName}=${_csrf.token}",
 		data:jsonobj,		
  		dataType:"json",
  		success:function(obj){
  			console.log(obj.Ccontent);
 			$('#content'+num).text(obj.Ccontent);
 			+$('input#inputContents'+num).attr("disabled","disabled");
  			$('#modi'+num).remove();
 			$('#modifyIcon'+num).show();
 			$('#deleteIcon'+num).show();
  		},
  		error:function(xhr,status,error){
 			console.log('error:'+error);	
  		}
  		
  	});
  }
  function recommendCtn(pnum,pnickname)
  {
  	console.log('추천누름'+pnum+', '+pnickname);
	var nickname='${sessionScope.userInfo.nickname}';
 	console.log(nickname);
 	if(nickname==""){
 		alert('로그인하셔야 추천이 가능합니다.');
 	}else{
  	var jobj= {};
  	jobj.nickname=pnickname;
  	jobj.num=pnum;
  	$.ajax({
 		type:"post",
 		url:"recommend?${_csrf.parameterName}=${_csrf.token}",
 		data:jobj,
 		dataType:"json",
 		success:function(obj){
  			console.log('추천했을경우 넘어온 내용'+obj.recommendsuc);
  			if(obj.recommendsuc){
  				$('#afterreco').text(obj.recommendCtn);
 			}else{
  			alert('이미 추천하셨습니다.');
 		}
  		},
  		error:function(xhr,status,error){
  			console.log('에러발생: '+error+", status: "+status
  					+",xhr: "+xhr);
  		}
  			
  		});
 	}
 	
  }
 function replyCtn(num){
 	console.log('넘어온num: '+num);
	var nickname='${sessionScope.userInfo.nickname}';
 	console.log(nickname);
 	if(nickname==""){
 		alert('로그인하셔야 답글쓰기가 가능합니다.');
 	}else{
 		
 		location.href="replyForm?num="+num;
 	}
 }	

  
  </script>

<div class="container">
	<div class="row">
		<div class="col-lg-12">
			<!--  제목 부분  -->
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label"></label>
				<div class="col-sm-10">
					<h3>
						<label for="name" class="col-sm-9 control-label">${wvalue.title}
						</label>
					</h3>
				</div>
			</div>
			<div style="margin-top: 5%; margin-bottom: 5%; margin-left: 8%">
				<div class="form-group">
					<div class="col-sm-10" style="text-align: right;">
						<span class="glyphicon glyphicon-eye-open"> ${wvalue.views }
						</span>
						<%-- <span class="glyphicon glyphicon-comment">　${wvalue.cmtnum}　</span> --%>
						<span id="afterreco" class="glyphicon glyphicon-thumbs-up">
							${wvalue.recommend} </span>
					</div>
				</div>
			</div>

			<div style="margin-top: 10%; margin-bottom: 10%;"></div>
			<!-- 내용 부분 -->

			<div class="form-group">
				<div id="ir1" style="width: 80%; margin-left: 10%">
					${wvalue.contents}</div>
			</div>

			<div class="form-group">
				<div style="width: 80%; margin-left: 10%">
					<div id="map" style="width: 100%; height: 350px;"></div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-10 col-sm-offset-2"
					style="text-align: right; width: 80%; margin-left: 10%">
					<div id="modifyIcon" class="divIcon" style="visibility: hidden;">
						<span class="glyphicon glyphicon-edit"
							style="font-size: 25px; cursor: pointer;"
							onclick="modify(${wvalue.num})"></span>
					</div>
					<div id="deleteIcon" class="divIcon" style="visibility: hidden;">
						<span class="glyphicon glyphicon-remove"
							style="font-size: 25px; cursor: pointer;"
							onclick="freeboardDelete(${wvalue.num})"></span>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-10 col-sm-offset-2"
					style="text-align: center; margin-top: 3%; margin-bottom: 3%">
					<a class="btn icon-btn btn-primary"
						onclick="recommendCtn(${wvalue.num},'${sessionScope.userInfo.nickname }')"
						href="#"> <span
						class="glyphicon btn-glyphicon glyphicon-thumbs-up img-circle text-muted"
						style="color: white; font: bold;"> </span> 추천
					</a> <a class="btn icon-btn btn-primary" href="/FoodField/free?page=1">
						<span
						class="glyphicon btn-glyphicon glyphicon-list img-circle text-muted">
					</span> 리스트보기
					</a> <a class="btn icon-btn btn-primary"
						onclick="replyCtn('${wvalue.num}')" href="#"> <span
						class="glyphicon btn-glyphicon glyphicon-list img-circle text-muted">
					</span> 답글쓰기
					</a>
				</div>
			</div>
			<div class="col-sm-10 col-sm-offset-2">
				<form id="commentForm">
					<input type="hidden" name="nickname"
						value="${sessionScope.userInfo.nickname }"> <input
						type="hidden" value="${wvalue.num }" name="num">
					<textarea rows="3" class="form-control" placeholder="댓글을 입력해주세요"
						name="contents"></textarea>
				</form>
				<div class="required-icon" style="margin: 3%">
					<div style="text-align: right;">
						<button type="button" class="btn icon-btn btn-primary"
							id="commentBtn">
							<span
								class="glyphicon btn-glyphicon glyphicon-list img-circle text-muted">
							</span> 입력
						</button>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-10 col-sm-offset-2"
						style="text-align: center; margin-bottom: 3%" id="cmt"></div>
				</div>
			</div>
		</div>
	</div>
</div>
