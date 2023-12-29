package com.springmvc.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* HandlerInterceptorAdapter를 사용한 로그 기록 */
// 웹으로 접근하는 사용자를 파일에 출력하도록 구현한다.

// HandlerInterceptor 인터페이스는 반드시 세 가지의 메서드를 모두 구현해야 한다는 점에서 번거롭다.
// HandlerInterceptorAdapter 클래스를 사용할 시, 모든 메서드를 구현할 필요가 없다.

public class AuditingInterceptor extends HandlerInterceptorAdapter {
    // Logger 객체를 가져온다.
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    private String user;
    private String bookId;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getRequestURI().endsWith("books/add") && request.getMethod().equals("POST")) {
            user = request.getRemoteUser();
            bookId = request.getParameterValues("bookId")[0];
        }

        return true;
    }

    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler, Exception arg3) throws Exception {
        if (request.getRequestURI().endsWith("books/add")) {
            // 로그 메시지를 출력한다.
            logger.warn(String.format("신규 등록 도서 ID : %s, 접근자 : %s, 접근 시각 : %s", bookId, user, getCurrentTime()));
        }
    }

    // 현재 '/년/월/일 시:분:초'를 얻어 오는 메서드 getCurrentTime()
    private String getCurrentTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return formatter.format(calendar.getTime());
    }
}
