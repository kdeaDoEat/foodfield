<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	<link rel="shortcut icon" href="/FoodField/resources/favicon.ico">
</head>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
function msgRead(num) {
	location.href="read?num="+num;
}
function search_Option(evt) {
	$('#search_concept').text(evt);
}
function search() {
	var text = '';
	if($('#search_concept').text() == 'Option'){
		alert('검색 옵션을 설정해주세요!');
	}else if($('#searchContent').val() == ''){
		alert('검색 내용을 입력해주세요!');
	}else{
		if($('#search_concept').text() == '제목')		text='title';
		else if($('#search_concept').text() == '내용')	text='contents';
		else if($('#search_concept').text() == '작성자')	text='sender';
		
		location.href="mSearch?type="+text+"&word="+$('#searchContent').val()+"&page=1";
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
                        <a  href="list?page=1"><i class="fa fa-folder-open-o" aria-hidden="true"></i> 나의 쪽지함</a>
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
	                    <div class="panel panel-default">
	                        <div class="panel-heading">　</div>
	                        <div class="panel-body">	<!-- <tr class="success"> 안읽은쪽지 -->
	                            <div class="table-responsive" style="text-align: center;">
	                                <table class="table">
	                                    <thead>
	                                        <tr>
	                                            <th style="width: 60%; text-align: center;">제목</th>
	                                            <th style="width: 20%; text-align: center;">보낸사람</th>
	                                            <th style="width: 20%; text-align: center;">보낸날짜</th>
	                                        </tr>
	                                    </thead>
	                                    <tbody>
	                                    	<c:forEach var="l" items="${vo.list }">
	                                    		<c:choose>


	                                    			<c:when test="${l.enabled == '1'}">
		                                    			<tr>
				                                            <td onclick="msgRead('${l.num}')" style="cursor: pointer;">
				                                            <img src="http://get-xmas.com/images/new.gif">
				                                            	　${l.title }
				                                            </td>
				                                            <td>${l.sender}</td>
				                                            <td><fmt:formatDate value="${l.w_date }"
																pattern="MM-dd HH:mm" /></td>
				                                        </tr>
	                                    			</c:when>
	                                    			<c:otherwise>
	                                    				<tr>
				                                            <td onclick="msgRead('${l.num}')" style="cursor: pointer;">${l.title }</td>
				                                            <td>${l.sender}</td>
				                                            <td><fmt:formatDate value="${l.w_date }"
																pattern="MM-dd HH:mm" /></td>
				                                        </tr>
	                                    			</c:otherwise>
	                                    		</c:choose>
	                                    	</c:forEach>
	                                    	<tr>
	                                    		<td colspan="3">
									                <ul class="pagination" role="menubar" aria-label="Pagination">
													<c:choose>
														<c:when test="${vo.type eq 'list'}">
															<li class="arrow unavailable" aria-disabled="true">
																<a href="list?page=${vo.nowpage-1 }">&laquo;</a>
															</li>
															<c:forEach var="p" items="${vo.page }">
																<c:choose>
																	<c:when test="${p eq vo.nowpage }">
																		<li class="current">
																		<a href="list?page=${p }" style="font-weight: bold;">${p }</a>
																		</li>
																	</c:when>
																	<c:otherwise>
																		<li class="current">
																		<a href="list?page=${p }">${p }</a>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<li class="arrow">
																<a href="list?page=${vo.nowpage+1 }">&raquo;</a>
															</li>
														</c:when>
														
														<c:when test="${vo.type eq 'search'}">
															<li class="arrow unavailable" aria-disabled="true">
															<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${vo.nowpage-1 }">
												  			&laquo;</a></li>
												  			<c:forEach var="p" items="${vo.page }">
																<c:choose>
																	<c:when test="${p eq vo.nowpage }">
																		<li class="current">
																		<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${p }" style="font-weight: bold;">${p }</a>
																		</li>
																	</c:when>
																	<c:otherwise>
																		<li class="current">
																		<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${p }">${p }</a>
																		</li>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
															<li class="arrow">
												  			<a href="search?type=${vo.searchType }&word=${vo.searchWord }&page=${vo.nowpage+1 }">&raquo;</a></li>
														</c:when>
													</c:choose>
													</ul>
							                    </td>
	                                    	</tr>
	                                    	<tr><td colspan="3">
												<div class="input-group">
	                                    	<div class="input-group-btn search-panel">
			                                    		<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
									                    	<span id="search_concept">Option</span> <span class="caret"></span>
									                    </button>
									                    <ul class="dropdown-menu" role="menu">
									                      <li><span style="cursor: pointer;" onclick="search_Option('제목');">제목</span></li>
									                      <li><span style="cursor: pointer;" onclick="search_Option('내용');">내용</span></li>
									                      <li><span style="cursor: pointer;" onclick="search_Option('작성자');">보낸사람</span></li>
									                    </ul>
								                    </div>
								                    <input type="hidden" name="search_param" value="all" id="search_param">         
									                <input id="searchContent" type="text" class="form-control" placeholder="Search">
									                <span class="input-group-btn">
									                    <button class="btn btn-primary" type="button" onclick="search();"><span class="glyphicon glyphicon-search"></span></button>
									                </span></div>
	                                    	</td></tr>
	                                    </tbody>
	                                </table>
	                            </div>
	                        </div>
	                    <!--  end  Context Classes  -->
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

