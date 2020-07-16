package com.johnson.demo.controller;

import com.johnson.core.mvc.annotation.Controller;
import com.johnson.core.mvc.annotation.RequestMapping;
import com.johnson.core.mvc.annotation.RequestMethod;
import com.johnson.core.mvc.view.View;

@Controller
public class Welcome {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public View welcome() {
		return new View("index.html");
	}
}
