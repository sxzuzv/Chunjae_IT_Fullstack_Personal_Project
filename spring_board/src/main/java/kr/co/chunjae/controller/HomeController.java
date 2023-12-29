package kr.co.chunjae.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@Log4j
public class HomeController {
	// 두 가지 요청명 {"", "/"}을 처리하는 메서드를 정의한다.
	@RequestMapping({"", "/"})
	public String home() {
//		log.info("Welcome HOME!");

		return "index";
	}
}
