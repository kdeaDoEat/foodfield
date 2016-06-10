<%@ page contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
function select(nickname) {
	$('#searchOption').val('');
	$('#receiver').val(nickname);
	$('div.modal').modal('hide');
}
</script>
<div class="panel panel-warning">
     <div class="panel-heading">
         	회원 목록
     </div>
     <div class="panel-body">
      <div class="table-responsive" style="text-align: center;">
             <table class="table">
                 <thead>
                     <tr>
                         <th style="width: 40%">아이디</th>
                         <th style="width: 40%">닉네임</th>
                         <th style="width: 20%">성별</th>
                     </tr>
                 </thead>
                 <tbody>
                 	<c:forEach var="l" items="${vo}">
     					<tr>
                       		<td>${l.email }</td>
                       		<td onclick="select('${l.nickname }')" style="cursor: pointer;">${l.nickname }</td>
                       		<td>${l.gender }</td>
                     	</tr>
     				</c:forEach>
                 </tbody>
             </table>
         </div>
     </div>
 </div>