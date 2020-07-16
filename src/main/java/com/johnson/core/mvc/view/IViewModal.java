package com.johnson.core.mvc.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IViewModal {
	void toClient(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
}
