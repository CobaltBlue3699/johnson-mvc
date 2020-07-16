package com.johnson.core.filter.compression;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CompressionResponseWrapper extends HttpServletResponseWrapper {
    private GZipServletOutputStream gzServletOutputStream;
    private PrintWriter printWriter;

    public CompressionResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if(printWriter != null) {
            throw new IllegalStateException();
        }
        if (gzServletOutputStream == null) {
            gzServletOutputStream = 
                    new GZipServletOutputStream(getResponse().getOutputStream());
        }
        return gzServletOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if(gzServletOutputStream != null) {
            throw new IllegalStateException();
        }
        if (printWriter == null) {
            gzServletOutputStream = 
                    new GZipServletOutputStream(getResponse().getOutputStream());
            OutputStreamWriter osw = 
                    new OutputStreamWriter(
                        gzServletOutputStream, getResponse().getCharacterEncoding());
            printWriter = new PrintWriter(osw);
        }
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        if(this.printWriter != null) {
            this.printWriter.flush();
        }
        else if(this.gzServletOutputStream != null) {
            this.gzServletOutputStream.flush();
        }
        super.flushBuffer();
    }    

    public void finish() throws IOException {
        if(this.printWriter != null) {
            this.printWriter.close();
        }
        else if(this.gzServletOutputStream != null) {
            this.gzServletOutputStream.finish();
        }
    }

    @Override
    public void setContentLength(int len) {}

    @Override
    public void setContentLengthLong(long length) {}
}