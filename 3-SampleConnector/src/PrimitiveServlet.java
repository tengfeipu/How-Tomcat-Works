
import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet {

    public void init(ServletConfig config) throws ServletException {
        System.out.println("init");
    }

    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        System.out.println("from service");
        PrintWriter out = response.getWriter();
        String s = "Hello. Roses are red.\r\n"+"Violets are blue.";
        out.print("HTTP/1.1 200 ok\r\n" +
                "Content-Type: text/html\r\n" +
                "Content-Length: "+s.length()+"\r\n" +
                "\r\n");
        out.print(s);
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public String getServletInfo() {
        return null;
    }
    public ServletConfig getServletConfig() {
        return null;
    }

}

