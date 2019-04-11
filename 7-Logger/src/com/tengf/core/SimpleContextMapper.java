package com.tengf.core;

import org.apache.catalina.*;

import javax.servlet.http.HttpServletRequest;

public class SimpleContextMapper implements Mapper {

    private SimpleContext context = null;

    public Container getContainer() {
        return context;
    }

    public void setContainer(Container container) {
        if (!(container instanceof SimpleContext))
            throw new IllegalArgumentException
                    ("Illegal type of container");
        context = (SimpleContext) container;
    }

    public String getProtocol() {
        return null;
    }

    public void setProtocol(String s) {

    }

    public Container map(Request request, boolean b) {
        // Identify the context-relative URI to be mapped
        String contextPath =
                ((HttpServletRequest) request.getRequest()).getContextPath();
        //System.out.println("contextpath: "+contextPath);
        String requestURI = ((HttpServletRequest) request.getRequest()).getRequestURI();
        String relativeURI = requestURI.substring(contextPath.length());
        //System.out.println("requestURI: "+requestURI);
        // Apply the standard request URI mapping rules from the specification
        Wrapper wrapper = null;
        String servletPath = relativeURI;
        String pathInfo = null;
        String name = context.findServletMapping(relativeURI);
        if (name != null)
            wrapper = (Wrapper) context.findChild(name);
        return (wrapper);
    }
}
