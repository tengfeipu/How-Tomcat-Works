package com.tengf;

import com.tengf.core.SimpleLoader;
import com.tengf.core.SimpleWrapper;
import com.tengf.valves.ClientIPLoggerValve;
import com.tengf.valves.HeaderLoggerValve;
import org.apache.catalina.Loader;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

public class Bootstrap {

    public static void main(String[] args) {
/* call by using http://localhost:8080/ModernServlet,
   but could be invoked by any name */
        HttpConnector connector = new HttpConnector();
        Wrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("ModernServlet");
        Loader loader = new SimpleLoader();
        Valve valve1 = new HeaderLoggerValve();
        Valve valve2 = new ClientIPLoggerValve();

        wrapper.setLoader(loader);
        ((SimpleWrapper) wrapper).addValve(valve1);
        ((SimpleWrapper) wrapper).addValve(valve2);

        connector.setContainer(wrapper);
        try {
            connector.initialize();
            connector.start();

            // make the application wait until we press a key.
            System.in.read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
