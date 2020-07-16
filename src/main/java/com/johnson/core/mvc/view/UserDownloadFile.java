package com.johnson.core.mvc.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDownloadFile implements IViewModal {

	private static final Logger LOGGER = LogManager.getLogger(UserDownloadFile.class);

	public UserDownloadFile(File file) {
		this.file = file;
		this.setFileName(file.getName());
	}

	public UserDownloadFile(File file, String fileName) {
		this.file = file;
		this.setFileName(fileName);
	}

	private File file;

	private String fileName;

	@Override
	public void toClient(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if (file != null && file.exists()) {
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + getFileName() + "\"");
			response.setHeader("Omni-Action", "download");
			try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
				byte[] buffer = new byte[16384];
				while (bis.read(buffer) > 0) {
					bos.write(buffer);
				}
				bos.flush();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "file transfer error.");
			}
		} else {
			response.sendError(HttpServletResponse.SC_GONE, "file not found.");
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
