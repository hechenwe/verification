package com.sooncode.verification_test;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("weixin")
public class WeixinController {

	@RequestMapping("getInfo")
	@ResponseBody
	public Map<String,Object> getInfo(@RequestBody String jsonData){
		System.out.println("WeixinController.getInfo() jsonData="+jsonData);
		Map<String,Object> map = new HashMap<>();
		map.put("OK", 1);
		return map;
		
		
	}
	
	
	@RequestMapping("getInfo2")
	@ResponseBody
	public Map<String,Object> getInfo2(@RequestBody Student student){
		
		System.out.println("WeixinController.getInfo() jsonData="+student);
		Map<String,Object> map = new HashMap<>();
		map.put("OK", 1);
		
		return map;
		
		
	}
}
