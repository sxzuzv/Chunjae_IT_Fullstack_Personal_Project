package com.springmvc.exception;

/* @ResponseStatus를 이용한 예외 처리 */
// 도서 목록 중 존재하지 않는 도서 분야(category)를 요청하는 경우에 대한 예외 처리를 구현한다.
// @ResponseStatus는 웹 요청을 할 때 예외가 발생하면 지정된 HTTP 상태 코드를 웹 브라우저에 전달한다.
// 예외 메서드, 예외 클래스에 사용할 때의 작성 형식은 아래와 같다.
// @ResponseStatus(value = HttpStatus.상태 코드, reasons = "오류 설명")

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
// @ResponseStatus를 이용한 예외 처리
// 클래스에 @ResponseStatus를 선언한다.
// 웹 요청에 대해 예외 발생 시, 지정된 HTTP 404 응답 상태 코드를 웹 브라우저에 전달하여 지정한 오류 메시지를 출력한다.
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "요청한 도서 분야를 찾을 수 없습니다.")
public class CategoryException extends RuntimeException {
}
