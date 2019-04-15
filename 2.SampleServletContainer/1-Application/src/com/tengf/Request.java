package com.tengf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.*;

public class Request implements ServletRequest {

    private InputStream input;
    private String uri;

    public Request(InputStream input){
        this.input = input;
    }

    public void parse(){
        StringBuffer request = new StringBuffer(3072);
        int i;
        byte[] buffer = new byte[3072];
        try{
            i = input.read(buffer);
        }
        catch (IOException e){
            e.printStackTrace();
            i = -1;
        }
        for(int j=0; j<i; j++){
            request.append((char) buffer[j]);
        }
        System.out.print(request.toString());
        uri = parseUri(request.toString());
    }

    private String parseUri(String requestString){
        int index1,index2;
        index1 = requestString.indexOf(' ');
        if(index1 != -1){
            index2 = requestString.indexOf(' ',index1+1);
            if(index2>index1)
                return requestString.substring(index1+1,index2);
        }
        return null;
    }

    public String getUri(){
        return uri;
    }

    /* implementation of the ServletRequest*/
    public Object getAttribute(String attribute){ return null;}

    public int getRemotePort(){return 8080;}

    public boolean isAsyncStarted(){return false;}

    public boolean isAsyncSupported(){return false;}

    public Enumeration<String> getAttributeNames() {
        return null;
    }

    public AsyncContext getAsyncContext(){return null;}

    public DispatcherType getDispatcherType(){ return null;}

    public  ServletContext getServletContext(){ return null;}

    public String getLocalName(){ return "127.0.0.1";}

    public int getLocalPort(){ return 8080;}

    public String getRealPath(String path) {
        return null;
    }

    public AsyncContext startAsync(ServletRequest var1, ServletResponse var2) throws IllegalStateException{ return null;}

    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    public boolean isSecure() {
        return false;
    }

    public String getCharacterEncoding() {
        return null;
    }

    public int getContentLength() {
        return 0;
    }

    public String getLocalAddr(){ return "";}

    public String getContentType() {
        return null;
    }

    public  AsyncContext startAsync() throws IllegalStateException{ return null;}

    public ServletInputStream getInputStream() throws IOException {
        return null;
    }

    public Locale getLocale() {
        return null;
    }

    public Enumeration getLocales() {
        return null;
    }

    public String getParameter(String name) {
        return null;
    }

    public Map getParameterMap() {
        return null;
    }

    public Enumeration getParameterNames() {
        return null;
    }

    public String[] getParameterValues(String parameter) {
        return null;
    }

    public String getProtocol() {
        return null;
    }

    public BufferedReader getReader() throws IOException {
        return null;
    }

    public String getRemoteAddr() {
        return null;
    }

    public String getRemoteHost() {
        return null;
    }

    public String getScheme() {
        return null;
    }

    public String getServerName() {
        return null;
    }

    public int getServerPort() {
        return 0;
    }

    public long getContentLengthLong(){ return (long)1; }

    public void removeAttribute(String attribute) {
    }

    public void setAttribute(String key, Object value) {
    }

    public void setCharacterEncoding(String encoding)
            throws UnsupportedEncodingException {
    }
}
