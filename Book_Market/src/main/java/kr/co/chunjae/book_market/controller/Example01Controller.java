package kr.co.chunjae.book_market.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Example01Controller {
    // 요청명이 /exam01일 경우, 아래의 메소드가 실행되며 webpage08_01.jsp가 출력된다.
    @GetMapping("/exam01")
    public String requestMethod(Model model) {
        return "webpage08_01";
    }

    // 요청명이 /home/main일 경우, 아래의 메소드가 실행된다.
    @GetMapping("/admin/main")
    public String requestMethod2(Model model) {
        // 모델에 data라는 이름으로 /webpage01/adminPage.jsp를 담는다.
        model.addAttribute("data", "/webpage01/adminPage.jsp");
        return "webpage01/adminPage";
    }

    @GetMapping("/employee/main")
    public String requestMethod3(Model model) {
        // 모델에 data라는 이름으로 /webpage01/adminPage.jsp를 담는다.
        model.addAttribute("data", "/webpage01/adminPage.jsp");
        return "webpage01/adminPage";
    }

    @GetMapping("/member/main")
    public String requestMethod4(Model model) {
        // 모델에 data라는 이름으로 /webpage01/adminPage.jsp를 담는다.
        model.addAttribute("data", "/webpage01/adminPage.jsp");
        return "webpage01/adminPage";
    }

    @GetMapping("/home/main")
    public String requestMethod5(Model model) {
        // 모델에 data라는 이름으로 /webpage01/adminPage.jsp를 담는다.
        model.addAttribute("data", "/webpage01/adminPage.jsp");
        return "webpage01/adminPage";
    }
}
