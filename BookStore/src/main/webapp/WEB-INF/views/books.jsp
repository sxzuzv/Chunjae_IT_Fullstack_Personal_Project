<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>"
          rel="stylesheet">
    <title>도서 목록</title>
</head>
<body>
<nav class="navbar navbar-expand  navbar-dark bg-dark">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="./home">Home</a>
        </div>
    </div>
</nav>
<div class="jumbotron">
    <div class="container">
        <h1 class="display-3">도서 목록</h1>
    </div>
</div>
<div class="container">
    <div class="row" align="center">
        <c:forEach items="${bookList}" var="book">
            <div class="col-md-4">
                <c:choose>
                    <c:when test="${book.getBookImage()==null}">
                        <%-- DTO에 이미지 관련 멤버 변수가 존재하기는 하나, 실제 값은 지정하지 않았다. --%>
                        <%-- 그러므로 upload 폴더 하위에 미리 넣어둔 이미지를 가져오기 위하여 도서 ID를 기준으로 이미지 파일을 불러와서 설정한다. --%>
                        <img src="${pageContext.request.contextPath}/upload/${book.getBookId()}.png" style="width: 60%"/>
                    </c:when>
                    <c:otherwise>
                        <%-- 도서를 추가하여 이미지 파일이 추후에 업로드 된 경우,
                        DTO에 정의해둔 이미지 관련 멤버 변수에 이미지에 대한 정보가 세팅된 상태이므로 아래와 같은 방식으로 이미지 파일을 불러와서 설정한다. --%>
                        <img src="${pageContext.request.contextPath}/upload/${book.getBookImage().getOriginalFilename()}"style="width: 60%" />
                    </c:otherwise>
                </c:choose>

                <h3>${book.name}</h3>
                <p>${book.author}
                    <br> ${book.publisher} | ${book.releaseDate}
                <p align=left>${fn:substring(book.description, 0, 100)}...
                <p>${book.unitPrice}원
                <p><a href="<c:url value="/books/book?id=${book.bookId}"/>" class="btn btn-Secondary" role="button">상세 정보 &raquo;</a>
            </div>
        </c:forEach>
    </div>
    <hr>
    <footer>
        <p>&copy; BookMarket</p>
    </footer>
</div>
</body>
</html>