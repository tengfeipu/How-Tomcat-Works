package com.tengf.core;

import org.apache.catalina.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SimpleWrapperValve implements Valve, Contained {

    protected Container container;

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public String getInfo() {
        return null;
    }

    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {

        SimpleWrapper wrapper = (SimpleWrapper) getContainer();
        ServletRequest servletRequest = request.getRequest();
        ServletResponse servletResponse = response.getResponse();
        Servlet servlet = null;
        HttpServletRequest httpServletRequest = null;
        if(servletRequest instanceof HttpServletRequest)
            httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = null;
        if(servletResponse instanceof HttpServletResponse)
            httpServletResponse = (HttpServletResponse) servletResponse;
        try{
            servlet = wrapper.allocate();
            if(httpServletRequest !=null && httpServletResponse !=null){
                servlet.service(httpServletRequest,httpServletResponse);
            }
            else servlet.service(servletRequest,servletResponse);
        }
        catch (ServletException e){
            System.out.println(e.toString());
        }

    }
}
