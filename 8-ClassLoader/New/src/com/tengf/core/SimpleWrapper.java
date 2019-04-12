package com.tengf.core;

import org.apache.catalina.*;
import org.apache.catalina.util.LifecycleSupport;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class SimpleWrapper implements Wrapper, Pipeline,Lifecycle {

    private Servlet instance = null;
    private String servletClass;
    private Loader loader;
    private String name;
    private SimplePipeline pipeline = new SimplePipeline(this);
    protected Container parent = null;
    protected boolean started = false;
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);

    public SimpleWrapper(){
        pipeline.setBasic(new SimpleWrapperValve());
    }

    public Valve getBasic() {
        return pipeline.getBasic();
    }

    public void addLifecycleListener(LifecycleListener lifecycleListener) {

    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {

    }

    public void start() throws LifecycleException {
        System.out.println("Starting Wrapper " + name);
        if (started)
            throw new LifecycleException("Wrapper already started");

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent("BEFORE_START_EVENT", null);
        started = true;

        // Start our subordinate components, if any
        if ((loader != null) && (loader instanceof Lifecycle))
            ((Lifecycle) loader).start();

        // Start the Valves in our pipeline (including the basic), if any
        if (pipeline instanceof Lifecycle)
            ((Lifecycle) pipeline).start();

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent("AFTER_START_EVENT", null);
    }

    public void stop() throws LifecycleException {
        // Shut down our servlet instance (if it has been initialized)
        try {
            instance.destroy();
        }
        catch (Throwable t) {
        }
        instance = null;
        if (!started)
            throw new LifecycleException("Wrapper " + name + " not started");
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent("BEFORE_STOP_EVENT", null);

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;

        // Stop the Valves in our pipeline (including the basic), if any
        if (pipeline instanceof Lifecycle) {
            ((Lifecycle) pipeline).stop();
        }

        // Stop our subordinate components, if any
        if ((loader != null) && (loader instanceof Lifecycle)) {
            ((Lifecycle) loader).stop();
        }

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent("AFTER_STOP_EVENT", null);
    }

    public void setBasic(Valve valve) {
        pipeline.setBasic(valve);
    }

    public void addValve(Valve valve) {
        pipeline.addValve(valve);
    }

    public Valve[] getValves() {
        return pipeline.getValves();
    }

    public void removeValve(Valve valve) {
        pipeline.removeValve(valve);
    }

    public long getAvailable() {
        return 0;
    }

    public void setAvailable(long l) {

    }

    public String getJspFile() {
        return null;
    }

    public void setJspFile(String s) {

    }

    public int getLoadOnStartup() {
        return 0;
    }

    public void setLoadOnStartup(int i) {

    }

    public String getRunAs() {
        return null;
    }

    public void setRunAs(String s) {

    }

    public String getServletClass() {
        return null;
    }

    public void setServletClass(String s) {
        this.servletClass = s;
    }

    public boolean isUnavailable() {
        return false;
    }

    public void addInitParameter(String s, String s1) {

    }

    public void addInstanceListener(InstanceListener instanceListener) {

    }

    public void addSecurityReference(String s, String s1) {

    }

    public Servlet allocate() throws ServletException {
        if(instance == null){
            try{
                instance = loadServlet();
            }
            catch (ServletException e){
                throw e;
            }
            catch (Throwable e){
                throw new ServletException("Connot allocate a servlet instance",e);
            }
        }
        return instance;
    }

    private Servlet loadServlet() throws ServletException{
        if(instance!=null)
            return instance;

        Servlet servlet = null;
        String actualClass = servletClass;
        if(actualClass == null)
            throw  new ServletException("servlet class has not been specified");

        Loader loader = getLoader();
        // Acquire an instance of the class loader to be used
        if (loader==null) {
            throw new ServletException("No loader.");
        }
        ClassLoader classLoader = loader.getClassLoader();

        // Load the specified servlet class from the appropriate class loader
        Class classClass = null;
        try {
            if (classLoader!=null) {
                classClass = classLoader.loadClass(actualClass);
            }
        }
        catch (ClassNotFoundException e) {
            throw new ServletException("Servlet class not found");
        }
        // Instantiate and initialize an instance of the servlet class itself
        try {
            servlet = (Servlet) classClass.newInstance();
        }
        catch (Throwable e) {
            throw new ServletException("Failed to instantiate servlet");
        }

        // Call the initialization method of this servlet
        try {
            servlet.init(null);
        }
        catch (Throwable f) {
            throw new ServletException("Failed initialize servlet.");
        }
        return servlet;

    }

    public void deallocate(Servlet servlet) throws ServletException {

    }

    public String findInitParameter(String s) {
        return null;
    }

    public String[] findInitParameters() {
        return new String[0];
    }

    public String findSecurityReference(String s) {
        return null;
    }

    public String[] findSecurityReferences() {
        return new String[0];
    }

    public void load() throws ServletException {
        instance = loadServlet();
    }

    public void removeInitParameter(String s) {

    }

    public void removeInstanceListener(InstanceListener instanceListener) {

    }

    public void removeSecurityReference(String s) {

    }

    public void unavailable(UnavailableException e) {

    }

    public void unload() throws ServletException {

    }

    public String getInfo() {
        return null;
    }

    public Loader getLoader() {
        if (loader !=null)
            return loader;
        if(parent !=null)
            return parent.getLoader();
        return null;
    }

    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    public Logger getLogger() {
        return null;
    }

    public void setLogger(Logger logger) {

    }

    public Manager getManager() {
        return null;
    }

    public void setManager(Manager manager) {

    }

    public Cluster getCluster() {
        return null;
    }

    public void setCluster(Cluster cluster) {

    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        this.name = s;
    }

    public Container getParent() {
        return parent;
    }

    public void setParent(Container container) {
        parent = container;
    }

    public ClassLoader getParentClassLoader() {
        return null;
    }

    public void setParentClassLoader(ClassLoader classLoader) {

    }

    public Realm getRealm() {
        return null;
    }

    public void setRealm(Realm realm) {

    }

    public DirContext getResources() {
        return null;
    }

    public void setResources(DirContext dirContext) {

    }

    public void addChild(Container container) {

    }

    public void addContainerListener(ContainerListener containerListener) {

    }

    public void addMapper(Mapper mapper) {

    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    public Container findChild(String s) {
        return null;
    }

    public Container[] findChildren() {
        return new Container[0];
    }

    public Mapper findMapper(String s) {
        return null;
    }

    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request,response);
    }

    public Container map(Request request, boolean b) {
        return null;
    }

    public void removeChild(Container container) {

    }

    public void removeContainerListener(ContainerListener containerListener) {

    }

    public void removeMapper(Mapper mapper) {

    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }
}
