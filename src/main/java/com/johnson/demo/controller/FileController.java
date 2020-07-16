package com.johnson.demo.controller;

import java.io.File;

import com.johnson.core.mvc.annotation.Controller;
import com.johnson.core.mvc.annotation.RequestMapping;
import com.johnson.core.mvc.annotation.RequestMethod;
import com.johnson.core.mvc.view.UserDownloadFile;

@Controller
public class FileController {

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public UserDownloadFile userDownload() {
		return new UserDownloadFile(new File("C:\\Users\\Johnson Li\\workspace\\JohnsonMVC\\frontend\\src\\assets\\img\\nature-sky.jpg"));
	}
}
