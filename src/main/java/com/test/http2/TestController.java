package com.test.http2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	public String test() {
		return "this is a HTTP2 test";
	}
	
	@GetMapping("/test1")
	@ResponseStatus(HttpStatus.OK)
	public void test1() {
		//do something
	}
	

}
