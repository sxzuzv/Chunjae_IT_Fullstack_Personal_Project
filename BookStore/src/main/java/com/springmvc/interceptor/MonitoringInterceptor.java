package com.springmvc.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* HandlerInterceptor를 사용한 로그 기록 */
// 웹에서 들어오는 모든 요청에 대한 접근 내역을 콘솔 화면에 출력하도록 한다.

// HandlerInterceptor 인터페이스는 preHandle(), postHandle(), afterCompletion() 메서드를 가지고 있다.
// PreHandle() : 웹 요청 URL이 Controller에 들어가기 전에 호출된다. false로 반환할 시, 이후 내용은 실행하지 않는다.
// postHandle() : 웹 요청 URL을 Controller가 처리한 후 호출된다. Controller에서 예외가 발생할 시 더 이상 실행되지 않는다.
// afterCompletion() : Controller가 웹 요청을 처리하여 뷰에 응답 전송이 종료된 후 호출된다.

// 인터셉터를 만들려면 HandlerInterceptor 인터페이스를 구현하거나
// HandlerInterceptorAdaptor 클래스를 상속받아야 한다.
public class MonitoringInterceptor implements HandlerInterceptor {
    ThreadLocal<StopWatch> stopWatchLocal = new ThreadLocal<StopWatch>();

    // Logger 객체를 가져온다.
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    // HandlerInterceptor 인터페이스 메서드 preHandle() : 컨트롤러 호출 전에 실행된다.
    /* [메서드 형식]
    boolean preHandle(HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler) throws Exception;
     * 역할 : Controller를 호출하기 이전에 핸들러 실행을 차단한다.
     * 매개변수 : request(현재 HTTP 요청), response(현재 HTTP 응답), handler(실행 핸들러 선택)
     */
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        StopWatch stopWatch = new StopWatch(handler.toString());
        stopWatch.start(handler.toString());
        stopWatchLocal.set(stopWatch);

        // 로그 메시지를 출력한다.
        logger.info("접근한 URL 경로 : " + getURLPath(request));
        logger.info("요청 처리 시작 시각 : " + getCurrentTime());

        return true;
    }

    // HandlerInterceptor 인터페이스 메서드 postHandle() : 컨트롤러를 호출하여 처리한 후에 실행된다.
    /* [메서드 형식]
    void postHandle(HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler,
                    ModelAndView modelAndView) throws Exception;
     * 역할 : Controller를 호출하여 처리한 후 핸들러 실행을 차단한다.
     * 매개변수 : request(현재 HTTP 요청), response(현재 HTTP 응답), handler(시작된 비동기 실행 핸들러),
                modelAndView(ModelAndView로 반환한 핸들러)
     */
    public void postHandle(HttpServletRequest arg0,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 로그 메시지를 출력한다.
        logger.info("요청 처리 종료 시각 : " + getCurrentTime());
    }

    // HandlerInterceptor 인터페이스 메서드 afterCompletion() : 뷰에 최종 결과를 반환한 후에 실행된다.
    /* [메서드 형식]
    void afterCompletion(HttpServletRequest request,
                    HttpServletResponse response,
                    Object handler,
                    Exception ex) throws Exception;
     * 역할 : 뷰에 최종 결과를 반환한 후에 핸들러 실행을 차단한다.
     * 매개변수 : request(현재 HTTP 요청), response(현재 HTTP 응답), handler(시작된 비동기 실행 핸들러)
     */
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception exception) throws Exception {
        StopWatch stopWatch = stopWatchLocal.get();
        stopWatch.stop();

        // 로그 메시지를 출력한다.
        logger.info("요청 처리 소요 시간 : " + stopWatch.getTotalTimeMillis() + "ms");
        stopWatchLocal.set(null);
        logger.info("======================================================");
    }

    // 요청 URL과 쿼리문을 얻어 오는 메서드 getURLPath()
    private String getURLPath(HttpServletRequest request) {
        String currentPath = request.getRequestURI();
        String queryString = request.getQueryString();
        queryString = (queryString == null) ? "" : "?" + queryString;

        return currentPath + queryString;
    }

    // 현재 '년/월/일 시:분:초'를 얻어 오는 메서드 getCurrentTime()
    private String getCurrentTime() {
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        return formatter.format(calendar.getTime());
    }
}
