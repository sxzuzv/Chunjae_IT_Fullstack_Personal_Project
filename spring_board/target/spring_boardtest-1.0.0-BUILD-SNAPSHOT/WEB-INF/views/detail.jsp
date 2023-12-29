<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>detail.jsp</title>
  <%-- ajax 사용을 위한 jquery 설정 --%>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
<table>
  <tr>
    <th>id</th>
    <td>${board.id}</td>
  </tr>
  <tr>
    <th>writer</th>
    <td>${board.boardWriter}</td>
  </tr>
  <tr>
    <th>date</th>
    <td>${board.boardCreatedTime}</td>
  </tr>
  <tr>
    <th>hits</th>
    <td>${board.boardHits}</td>
  </tr>
  <tr>
    <th>title</th>
    <td>${board.boardTitle}</td>
  </tr>
  <tr>
    <th>contents</th>
    <td>${board.boardContents}</td>
  </tr>
</table>
<button onclick="listFn()">목록</button>
<button onclick="updateFn()">수정</button>
<button onclick="deleteFn()">삭제</button>
</body>
<%-- 댓글 작성 관련 <div> 태그 추가 --%>
<div>
  <input type="text" id="commentWriter" placeholder="작성자">
  <input type="text" id="commentContents" placeholder="내용">
  <button id="comment-write-btn" onclick="commentWrite()">댓글 작성</button>
</div>
<%-- 댓글 리스트 출력을 위한 <div> 태그 추가 --%>
<%-- 댓글 출력 형태를 <div> 태그 내부에 작성한다. --%>
<div id="comment-list">
  <table>
    <tr>
      <th>댓글 번호</th>
      <th>작성자</th>
      <th>내용</th>
      <th>작성 시간</th>
    </tr>
    <%-- 불러온 댓글 리스트를 형식에 맞게 출력한다. (반복문 활용) --%>
    <c:forEach items="${commentList}" var="comment">
      <tr>
        <td>${comment.id}</td>
        <td>${comment.commentWriter}</td>
        <td>${comment.commentContents}</td>
        <td>${comment.commentCreatedTime}</td>
      </tr>
    </c:forEach>
  </table>
</div>

<script>
  <%-- '목록' 버튼 클릭 시 실행 --%>
  const listFn = () => {
    // 현재 페이지를 기억한다.
    const page = '${page}';
    // 기억한 페이지를 바탕으로 '목록' 버튼 클릭 시, 보고 있던 페이지로 돌아가도록 한다.
    location.href = "/board/paging?page=" + page;
  }
  <%-- '수정' 버튼 클릭 시 실행 --%>
  const updateFn = () => {
    const id = '${board.id}';
    location.href = "/board/update?id=" + id;
  }
  <%-- '삭제' 버튼 클릭 시 실행 --%>
  const deleteFn = () => {
    const id = '${board.id}';
    location.href = "/board/delete?id=" + id;
  }

  const commentWrite = () => {
    // html document로부터 'commentWriter' 이름을 가진 값을 write에 저장한다.
    const writer = document.getElementById("commentWriter").value;
    // html document로부터 'commentContents' 이름을 가진 값을 contents에 저장한다.
    const contents = document.getElementById("commentContents").value;
    // 게시글 번호를 board에 저장한다.
    const board = '${board.id}';
    $.ajax({
      // GET, POST, PUT, DELETE 모두 설정 가능하다.
      type: "post",
      // 데이터를 '/comment/save'로 전달한다.
      url: "/comment/save",
      data: {
        commentWriter: writer,
        commentContents: contents,
        boardId: board
      },
      // 데이터 타입 : json
      dataType: "json",
      // 데이터 전달에 성공했을 시
      success: function(commentList) {
        // 아래 내용을 실행한다.
        console.log("작성 성공");
        console.log(commentList);

        // 댓글 리스트를 출력한다.
        let output = "<table>";
        output += "<tr><th>댓글번호</th>";
        output += "<th>작성자</th>";
        output += "<th>내용</th>";
        output += "<th>작성시간</th></tr>";
        for(let i in commentList){
          output += "<tr>";
          output += "<td>"+commentList[i].id+"</td>";
          output += "<td>"+commentList[i].commentWriter+"</td>";
          output += "<td>"+commentList[i].commentContents+"</td>";
          output += "<td>"+commentList[i].commentCreatedTime+"</td>";
          output += "</tr>";
        }
        output += "</table>";
        document.getElementById('comment-list').innerHTML = output;
        document.getElementById('commentWriter').value='';
        document.getElementById('commentContents').value='';
      },
      // 데이터 전달에 실패했을 시
      error: function() {
        // 아래 내용을 실행한다.
        console.log("실패");
      }
    });
  }

</script>
</html>
