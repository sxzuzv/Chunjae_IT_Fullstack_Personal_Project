<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>list</title>
</head>
<body>
<table>
  <tr>
    <th>id</th>
    <th>title</th>
    <th>writer</th>
    <th>date</th>
    <th>hits</th>
  </tr>
  <!-- 한 줄(recode) 단위로 자른다. -->
  <c:forEach items="${boardList}" var="board">
    <!-- 한 줄(recode)을 컬럼(field) 단위로 자른다. -->
    <!-- <tr> : 줄 단위 구분 -->
    <tr>
      <td>${board.id}</td>
      <td>
        <a href="/board?id=${board.id}">${board.boardTitle}</a>
      </td>
      <td>${board.boardWriter}</td>
      <td>${board.boardCreatedTime}</td>
      <td>${board.boardHits}</td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
