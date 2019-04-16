package com.tengf;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;
    PrintWriter writer;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException{
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try{
            File file = new File(Constants.WEB_ROOT,request.getUri());
            //System.out.println(file.getPath());
            if(file.exists()){
                fis = new FileInputStream(file);
                int ch = fis.read(bytes,0,BUFFER_SIZE);
                String type = null;
                if(request.getUri().endsWith("gif")) type="image/gif ";
                else  type = "text/html";
                String ResponseMessage = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: "+type+"\r\n" +
                        "Content-Length: "+file.length()+"\r\n" +
                        "\r\n";
                output.write(ResponseMessage.getBytes());
                while (ch!=-1){
                    output.write(bytes,0,ch);
                    ch = fis.read(bytes,0,BUFFER_SIZE);
                }
            }
            else{
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        finally {
            if(fis!=null)
                fis.close();
        }
    }

    /** implementation of ServletResponse  */

    public String getCharacterEncoding(){return null;}

    public String getContentType(){return null;}

    public ServletOutputStream getOutputStream() throws IOException {return null;}

    public PrintWriter getWriter() throws IOException{
        writer = new PrintWriter(output,true);
        return writer;
    }

    public void setCharacterEncoding(String var1){}

    public void setContentLength(int var1){}

    public void setContentLengthLong(long var1){}

    public void setContentType(String var1){}

    public void setBufferSize(int var1){}

    public int getBufferSize(){return BUFFER_SIZE;}

    public void flushBuffer() throws IOException{}

    public void resetBuffer(){}

    public boolean isCommitted(){return false;}

    public void reset(){}

    public void setLocale(Locale var1){}

    public Locale getLocale(){return  null;}

}
