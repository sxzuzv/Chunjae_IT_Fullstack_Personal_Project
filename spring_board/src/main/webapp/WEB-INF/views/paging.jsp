<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>paging</title>
</head>
<body>
<div>
    <table>
        <tr>
            <th>id</th>
            <th>title</th>
            <th>writer</th>
            <th>date</th>
            <th>hits</th>
        </tr>
        <c:forEach items="${boardList}" var="board">
            <tr>
                <td>${board.id}</td>
                <td>
                    <a href="/board?id=${board.id}&page=${paging.page}">${board.boardTitle}</a>
                </td>
                <td>${board.boardWriter}</td>
                <td>${board.boardCreatedTime}</td>
                <td>${board.boardHits}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<%-- 페이지 번호 표시 --%>
<div>
    <c:choose>
        <%-- 현재 페이지가 첫 번째 페이지면 [이전]을 텍스트 처리한다. ([이전]은 링크되지 않는 단순 텍스트이다.) --%>
        <c:when test="${paging.page<=1}">
            <span>[이전]</span>
        </c:when>
        <%-- 첫 번째 페이지가 아닌 경우에는 [이전]을 클릭하면 현재 페이지에서 1만큼 작은 페이지를 요청한다. --%>
        <c:otherwise>
            <a href="/board/paging?page=${paging.page-1}">[이전]</a>
        </c:otherwise>
    </c:choose>

    <%--  for (int i = startPage; i <= endPage; i++) --%>
    <c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="i" step="1">
        <c:choose>
            <%-- 요청한 페이지에 있는 경우 현재 페이지 번호는 텍스트만 보이도록 처리한다. (텍스트이므로 클릭이 불가하다.) --%>
            <c:when test="${i eq paging.page}">
                <span>${i}</span>
            </c:when>

            <%-- 요청한 페이지가 아닌 번호의 경우, 클릭 시 해당하는 페이지로 이동 가능하도록 링크 처리한다. --%>
            <c:otherwise>
                <a href="/board/paging?page=${i}">${i}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <c:choose>
        <%-- 현재 페이지가 마지막 페이지(표현 가능 최대 페이지)이면 [다음]을 텍스트 처리한다. ([다음]은 링크되지 않는 단순 텍스트이다.) --%>
        <%-- 현재 페이지 >= 전체 페이지 --%>
        <c:when test="${paging.page>=paging.maxPage}">
            <span>[다음]</span>
        </c:when>
        <%-- 현재 페이지가 전체 페이지 보다 작을 경우, [다음]을 클릭할 시 현재 페이지에서 1만큼 큰 페이지를 요청한다. --%>
        <c:otherwise>
            <a href="/board/paging?page=${paging.page+1}">[다음]</a>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>