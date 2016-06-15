<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Message</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="/FoodField/resources/assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="/FoodField/resources/assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="/FoodField/resources/assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
	<link href='http://fonts.googleapis.com/earlyaccess/jejugothic.css' rel='stylesheet' type='text/css'>
	<link href='http://fonts.googleapis.com/earlyaccess/notosanskr.css' rel='stylesheet' type='text/css'>
</head>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
    $("#memberSearch").on('click',function(){
    	if($('#searchOption').val() ==''){
    		alert('검색할 회원을 입력해주세요!');
    	}else{
            $('div.modal').modal({remote : '/FoodField/message/write/search?nickname='+$('#searchOption').val()});
    	}
    })
    $('#sendMsg').on('click',function(){
    	if($('#receiver').val()==''){
    		alert('보낼 회원을 검색하세요!');
    	}else if($('#title').val() == ''){
    		alert('제목을 입력해주세요!');
    	}else if($('#contents').val() == ''){
    		alert('내용을 입력해주세요!');
    	}else{
    		var result = confirm('내용을 확인하셨습니까?');
			if(result){
				$.ajax({
					url:'sendMsg?${_csrf.parameterName}=${_csrf.token}',
					type:'post',
					data:$('#msgForm').serialize(),
					dataType:'json',
					success:function(result){
						if(result.ok==true){
							alert('전송완료!');
						}else{
							alert('전송실패');
						}
					},error:function(request,status,error){
						alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
					}
				});
			}
    	}
    });
    
})

function reply() {
	location.href="/FoodField/message/write?receiver=${vo.sender }";
}

function del() {
	var result = confirm('정말 삭제하시겠습니까?');
	if(result){
		$.ajax({
			url:'del?${_csrf.parameterName}=${_csrf.token}',
			type:'post',
			data:{num:"${vo.num }"},
			dataType:'json',
			success:function(result){
				if(result.ok==true){
					alert('삭제완료!');
					location.href="/FoodField/message/list?page=1";
				}else{
					alert('삭제실패');
				}
			},error:function(request,status,error){
				alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
		});
	}
}
</script>
<body>
    <div id="wrapper">
    
    <nav class="navbar navbar-default navbar-cls-top " role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.html">Message</a> 
            </div>
        </nav>   
           <!-- /. NAV TOP  -->
		<nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu" style="text-align: center;">
					<li class="text-center"></li>
                    <li>
                        <a  href="list?page=1"><i class="fa fa-folder-o" aria-hidden="true"></i> 나의 쪽지함</a>
                    </li>
                      <li>
                        <a  href="write"><i class="fa fa-folder-o" aria-hidden="true"></i> 쪽지 쓰기</a>
                    </li>
                      <li>
                        <a  href="sendBox?page=1"><i class="fa fa-folder-o" aria-hidden="true"></i> 보낸 쪽지함</a>
                    </li>
                </ul>
			</div>
        </nav>  
        
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-xs-12 col-md-12">
	                     <!--    Context Classes  -->
	                    <div class="col-md-12" >
                            <div class="panel panel-warning">
		                        <div class="panel-heading" style="font-size: 20px">
		                            <i class="fa fa-angle-double-right" aria-hidden="true"></i>　　${vo.title }
		                        </div>
		                        <div class="panel-heading" style="font-size: 20px">
		                            <i class="fa fa-user" aria-hidden="true"></i>　　${vo.sender }
		                        </div>
		                        <div class="panel-body" style="text-align: center;">${vo.contents }</div>
		                        <div class="panel-footer" style="text-align: right;">	
		                        	<span class="btn btn-primary" style="cursor: pointer;" onclick="reply()"><i class="fa fa-envelope" aria-hidden="true"></i>　답장하기</span>
		                        	　<span class="btn btn-primary" style="cursor: pointer;" onclick="del()"><i class="fa fa-envelope" aria-hidden="true"></i>　삭제</span>
		                        </div>
                    		</div>
    					</div>
                    </div>
                </div>
			</div>
             <!-- /. PAGE INNER  -->
		</div>
         <!-- /. PAGE WRAPPER  -->
        </div>
     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="/FoodField/resources/assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="/FoodField/resources/assets/js/bootstrap.min.js"></script>
    <!-- METISMENU SCRIPTS -->
    <script src="/FoodField/resources/assets/js/jquery.metisMenu.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="/FoodField/resources/assets/js/custom.js"></script>
</body>
</html>

