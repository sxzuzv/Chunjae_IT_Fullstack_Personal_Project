<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Security</title>
</head>
<body>
<h2>스프링 시큐리티 태그 예시</h2>
<sec:authorize access="hasRole('ROLE_MANAGER')" var="isAdmin">
    <p><h3>매니저 권한 화면입니다.</h3></p>
</sec:authorize>

<c:choose>
    <c:when test="${isAdmin}">
        <p>ROLE_MANAGER 권한 로그인 중입니다.</p>
        <p><a href="<c:url value='/exam01' />">[웹 요청 URL /exam01로 이동하기]</a></p>
    </c:when>
</c:choose>
<p>로그인 중이 아닙니다.</p>
<p><a href="<c:url value='/employee/tag' />">[웹 요청 URL /employee/tag로 이동하기]</a></p>
</body>
</html>
