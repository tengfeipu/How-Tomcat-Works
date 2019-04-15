package com.tengf;


import com.tengf.core.*;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappLoader;


public class Bootstrap {

    public static void main(String[] args) {
        System.setProperty("catalina.base",System.getProperty("user.dir"));
        Connector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        //Context context = new StandardContext();
        Context context = new SimpleContext();
        // StandardContext's start method adds a default mapper
//        context.setPath("/myApp");
//        context.setDocBase("myApp");

        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        context.addMapper(mapper);
        // context.addServletMapping(pattern, name);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");

        LifecycleListener listener = new SimpleContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        // here is our loader
        //Loader loader = new WebappLoader();
        Loader loader = new SimpleLoader();
        // associate the loader with the Context
        context.setLoader(loader);

        connector.setContainer(context);

        try {
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) context).start();
            // now we want to know some details about WebappLoader
//            WebappClassLoader classLoader = (WebappClassLoader) loader.getClassLoader();
//            String[] repositories = classLoader.findRepositories();
//            for (int i=0; i<repositories.length; i++) {
//                System.out.println("  repository: " + repositories[i]);
//            }

            // make the application wait until we press a key.
            System.in.read();
            ((Lifecycle) context).stop();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
