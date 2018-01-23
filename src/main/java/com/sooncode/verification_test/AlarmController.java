package com.sooncode.verification_test;


import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("alarmController")
public class AlarmController {

	@RequestMapping("addAlarm")
	@ResponseBody
	public Map<String,Object> addAlarm(@RequestBody String jsonData){
		System.out.println("WeixinController.getInfo() jsonData="+jsonData);
		Map<String,Object> map = new HashMap<>();
		map.put("OK", 1);
		return map;
		
		
	}
	
	
	 
}
