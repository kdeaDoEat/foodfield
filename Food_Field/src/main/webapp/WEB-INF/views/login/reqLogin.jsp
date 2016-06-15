<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<tiles:insertDefinition name="loginTemplate">
    <tiles:putAttribute name="title"></tiles:putAttribute>
    <%--tiles태그 안에 스크립트 요소를 사용하면 오류가 발생하므로 아래처럼 JSTL을 사용한다 --%>
        <c:forEach var="i" begin="1" end="5">
            ${i}
        </c:forEach>
         
</tiles:insertDefinition>
