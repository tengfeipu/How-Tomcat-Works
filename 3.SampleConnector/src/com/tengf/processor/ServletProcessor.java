package com.tengf.processor;

import com.tengf.connector.http.*;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor {

    public void process(HttpRequest request, HttpResponse response){
        String uri = request.getRequestURI();
        String servletName = uri.substring(uri.lastIndexOf("/")+1);
        URLClassLoader loader = null;

        try{
            //Create a URLClassLoader
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);
            //System.out.println("classPath.getCanonicalPath():"+classPath.getCanonicalPath());
            String repository = (new URL("file",null,classPath.getCanonicalPath() + File.separator)).toString();
            //System.out.println("repository:"+repository);
            urls[0] = new URL(null,repository,streamHandler);
            loader = new URLClassLoader(urls);
        }
        catch (IOException e){
            System.out.println(e.toString());
        }
        Class myClass = null;
        try{
            myClass = loader.loadClass(servletName);
        }
        catch (ClassNotFoundException e){
            System.out.println(e.toString());
        }

        Servlet servlet = null;
        try{
            servlet = (Servlet) myClass.newInstance();
            HttpRequestFacade requestFacade = new HttpRequestFacade(request);
            HttpResponseFacade responseFacade = new HttpResponseFacade(response);
            servlet.service(requestFacade, responseFacade);
            ((HttpResponse) response).finishResponse();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        catch (Throwable e){
            System.out.println(e.toString());
        }
    }
}
