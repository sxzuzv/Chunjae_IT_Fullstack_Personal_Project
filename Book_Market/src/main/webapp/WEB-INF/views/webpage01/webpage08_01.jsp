<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" url="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Security</title>
</head>
<body>
    <h3>스프링 시큐리티 예제</h3>
    <ul>
        <li>웹 요청 URL : <a href="<c:url value='/home/main' />">/home/main</a></li>
        <li>웹 요청 URL : <a href="<c:url value='/member/main' />">/member/main</a></li>
        <li>웹 요청 URL : <a href="<c:url value='/employee/main' />">/employee/main</a></li>
        <li>웹 요청 URL : <a href="<c:url value='/admin/main' />">/admin/main</a></li>
    </ul>
</body>
</html>
