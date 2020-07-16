package com.johnson.demo.controller;

import com.johnson.core.mvc.annotation.Autowired;
import com.johnson.core.mvc.annotation.Controller;
import com.johnson.core.mvc.annotation.RequestMapping;
import com.johnson.core.mvc.annotation.RequestMethod;
import com.johnson.core.mvc.bean.Param;
import com.johnson.core.mvc.view.JsonData;
import com.johnson.demo.service.IUserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	IUserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public JsonData getUserById(Param<String> param) {
		Long id = param.getLong("id");
		return new JsonData(userService.getUserById(id));
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public JsonData getUsers() {
//		if (true) {
//			throw new BusinessException(5, "錯誤5"));
//		}
		return new JsonData(userService.findAll());
	}

}
