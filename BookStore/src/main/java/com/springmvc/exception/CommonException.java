package com.springmvc.exception;

/* @ControllerAdvice를 이용한 예외 처리 */
// 하나의 Controller가 아닌 여러 Controller에서 발생하는 에외를 공통으로 처리할 수 있다.
// 도서 목록 중 존재하지 않는 도서 분류를 요청하는 경우의 예외 처리를 구현한다.

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

// 전역 예외 처리를 위한 @ControllerAdvice를 선언한다.
@ControllerAdvice
// 모든 Controller의 예외 처리 클래스 CommonException
public class CommonException {
    // 예외 클래스 RuntimeException을 설정한다.
    @ExceptionHandler(RuntimeException.class)
    // Controller에서 발생되는 예외 처리 메서드 handlerErrorCommon()
    private ModelAndView handlerErrorCommon(Exception e) {
        // ModelAndView 클래스의 modelAndView 인스턴스를 생성한다.
        ModelAndView modelAndView = new ModelAndView();

        // 모델 속성 exception에 예외 처리 클래스 RuntimeException을 저장한다.
        modelAndView.addObject("exception", e);

        // 뷰 이름으로 errorCommon을 설정하고 errorCommon.jsp 파일을 출력한다.
        modelAndView.setViewName("errorCommon");

        // ModelAndView 클래스의 modelAndView 인스턴스를 반환한다.
        return modelAndView;
    }
}
