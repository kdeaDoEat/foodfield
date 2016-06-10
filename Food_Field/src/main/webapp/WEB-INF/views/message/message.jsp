<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
      <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Free Bootstrap Admin Template : Binary Admin</title>
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
function msgRead(num) {
	location.href="read?num="+num;
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
                        <a  href="list"><i class="fa fa-dashboard fa-3x"></i> 쪽지함</a>
                    </li>
                      <li>
                        <a  href="write"><i class="fa fa-desktop fa-3x"></i> 쪽지 쓰기</a>
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
	                                            <th style="width: 60%">제목</th>
	                                            <th style="width: 20%">보낸사람</th>
	                                            <th style="width: 20%">보낸날짜</th>
	                                        </tr>
	                                    </thead>
	                                    <tbody>
	                                    	<c:forEach var="l" items="${vo }">
	                                    		<c:choose>
	                                    			<c:when test="${l.enabled == 1}">
		                                    			<tr class="success">
				                                            <td onclick="msgRead('${l.num}')" style="cursor: pointer;">${l.title }</td>
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

