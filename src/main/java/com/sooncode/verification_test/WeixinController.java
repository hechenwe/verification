package com.sooncode.verification_test;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("weixin")
public class WeixinController {

	@RequestMapping("getInfo")
	@ResponseBody
	public String getInfo(HttpServletRequest request,HttpServletResponse response){
		return "OK";
		
		
	}
}
