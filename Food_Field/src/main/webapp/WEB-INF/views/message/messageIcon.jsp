<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script src="http://code.jquery.com/jquery-2.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
function message() {
	window.open("/FoodField/message/list","","width=870, height=660, resizable=no, scrollbars=no, status=no;");
}
</script>
<a data-toggle="modal" onclick="message()" style="cursor: pointer;">
	<span class="notification-icon">  
		<span class="glyphicon glyphicon-envelope" style="font-size: large;"></span>
		<span class="badge" style="background-color: #b94a48;">${messageCount}</span>
	</span>
</a>