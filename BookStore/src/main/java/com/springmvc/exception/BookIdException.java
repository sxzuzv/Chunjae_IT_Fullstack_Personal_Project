package com.springmvc.exception;

/* @ExceptionHandler를 이용한 예외 처리 */
// 도서 목록 중 존재하지 않는 도서 아이디를 요청하는 경우에 대한 예외 처리를 구현한다.

@SuppressWarnings("serial")
public class BookIdException extends RuntimeException {
    private String bookId;

    // 생성자
    public BookIdException(String bookId) {
        this.bookId = bookId;
    }

    // Getter()
    public String getBookId() {
        return bookId;
    }
}
