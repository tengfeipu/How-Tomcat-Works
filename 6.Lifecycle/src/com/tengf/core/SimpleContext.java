package com.tengf.core;

import org.apache.catalina.*;
import org.apache.catalina.deploy.*;
import org.apache.catalina.util.CharsetMapper;
import org.apache.catalina.util.LifecycleSupport;

import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.HashMap;

public class SimpleContext implements Context, Pipeline,Lifecycle {

    public SimpleContext() {
        pipeline.setBasic(new SimpleContextValve());
    }

    protected HashMap children = new HashMap();
    protected Loader loader = null;
    protected SimplePipeline pipeline = new SimplePipeline(this);
    protected HashMap servletMappings = new HashMap();
    protected Mapper mapper = null;
    protected HashMap mappers = new HashMap();
    private Container parent = null;
    protected boolean started = false;
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);

    public void addLifecycleListener(LifecycleListener lifecycleListener) {
        lifecycle.addLifecycleListener(lifecycleListener);
    }

    public void removeLifecycleListener(LifecycleListener lifecycleListener) {
        lifecycle.removeLifecycleListener(lifecycleListener);
    }

    public void start() throws LifecycleException {

        if(started)
            throw new LifecycleException("SimpleContext has already started");

        lifecycle.fireLifecycleEvent("BEFORE_START_EVENT", null);
        started = true;
        try{
            if(loader != null && loader instanceof Lifecycle)
                ((Lifecycle) loader).start();

            // Start our child containers, if any
            Container children[] = findChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i] instanceof Lifecycle)
                    ((Lifecycle) children[i]).start();
            }

            if (pipeline instanceof Lifecycle)
                ((Lifecycle) pipeline).start();
            // Notify our interested LifecycleListeners
            lifecycle.fireLifecycleEvent(START_EVENT, null);

            // Notify our interested LifecycleListeners
            lifecycle.fireLifecycleEvent("AFTER_START_EVENT", null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws LifecycleException {
        if (!started)
            throw new LifecycleException("SimpleContext has not been started");
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent("BEFORE_STOP_EVENT", null);
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);

        started = false;
        try {
            // Stop the Valves in our pipeline (including the basic), if any
            if (pipeline instanceof Lifecycle) {
                ((Lifecycle) pipeline).stop();
            }

            // Stop our child containers, if any
            Container children[] = findChildren();
            for (int i = 0; i < children.length; i++) {
                if (children[i] instanceof Lifecycle)
                    ((Lifecycle) children[i]).stop();
            }
            if ((loader != null) && (loader instanceof Lifecycle)) {
                ((Lifecycle) loader).stop();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent("AFTER_STOP_EVENT", null);
    }

    public Object[] getApplicationListeners() {
        return new Object[0];
    }

    public void setApplicationListeners(Object[] objects) {

    }

    public boolean getAvailable() {
        return false;
    }

    public void setAvailable(boolean b) {

    }

    public CharsetMapper getCharsetMapper() {
        return null;
    }

    public void setCharsetMapper(CharsetMapper charsetMapper) {

    }

    public boolean getConfigured() {
        return false;
    }

    public void setConfigured(boolean b) {

    }

    public boolean getCookies() {
        return false;
    }

    public void setCookies(boolean b) {

    }

    public boolean getCrossContext() {
        return false;
    }

    public void setCrossContext(boolean b) {

    }

    public String getDisplayName() {
        return null;
    }

    public void setDisplayName(String s) {

    }

    public boolean getDistributable() {
        return false;
    }

    public void setDistributable(boolean b) {

    }

    public String getDocBase() {
        return null;
    }

    public void setDocBase(String s) {

    }

    public LoginConfig getLoginConfig() {
        return null;
    }

    public void setLoginConfig(LoginConfig loginConfig) {

    }

    public String getPath() {
        return null;
    }

    public void setPath(String s) {

    }

    public String getPublicId() {
        return null;
    }

    public void setPublicId(String s) {

    }

    public boolean getReloadable() {
        return false;
    }

    public void setReloadable(boolean b) {

    }

    public boolean getOverride() {
        return false;
    }

    public void setOverride(boolean b) {

    }

    public boolean getPrivileged() {
        return false;
    }

    public void setPrivileged(boolean b) {

    }

    public ServletContext getServletContext() {
        return null;
    }

    public int getSessionTimeout() {
        return 0;
    }

    public void setSessionTimeout(int i) {

    }

    public String getWrapperClass() {
        return null;
    }

    public void setWrapperClass(String s) {

    }

    public void addApplicationListener(String s) {

    }

    public void addApplicationParameter(ApplicationParameter applicationParameter) {

    }

    public void addConstraint(SecurityConstraint securityConstraint) {

    }

    public void addEjb(ContextEjb contextEjb) {

    }

    public void addEnvironment(ContextEnvironment contextEnvironment) {

    }

    public void addErrorPage(ErrorPage errorPage) {

    }

    public void addFilterDef(FilterDef filterDef) {

    }

    public void addFilterMap(FilterMap filterMap) {

    }

    public void addInstanceListener(String s) {

    }

    public void addLocalEjb(ContextLocalEjb contextLocalEjb) {

    }

    public void addMimeMapping(String s, String s1) {

    }

    public void addParameter(String s, String s1) {

    }

    public void addResource(ContextResource contextResource) {

    }

    public void addResourceEnvRef(String s, String s1) {

    }

    public void addRoleMapping(String s, String s1) {

    }

    public void addSecurityRole(String s) {

    }

    public void addServletMapping(String s, String s1) {
        synchronized (servletMappings){
            servletMappings.put(s,s1);
        }
    }

    public void addTaglib(String s, String s1) {

    }

    public void addWelcomeFile(String s) {

    }

    public void addWrapperLifecycle(String s) {

    }

    public void addWrapperListener(String s) {

    }

    public Wrapper createWrapper() {
        return null;
    }

    public String[] findApplicationListeners() {
        return new String[0];
    }

    public ApplicationParameter[] findApplicationParameters() {
        return new ApplicationParameter[0];
    }

    public SecurityConstraint[] findConstraints() {
        return new SecurityConstraint[0];
    }

    public ContextEjb findEjb(String s) {
        return null;
    }

    public ContextEjb[] findEjbs() {
        return new ContextEjb[0];
    }

    public ContextEnvironment findEnvironment(String s) {
        return null;
    }

    public ContextEnvironment[] findEnvironments() {
        return new ContextEnvironment[0];
    }

    public ErrorPage findErrorPage(int i) {
        return null;
    }

    public ErrorPage findErrorPage(String s) {
        return null;
    }

    public ErrorPage[] findErrorPages() {
        return new ErrorPage[0];
    }

    public FilterDef findFilterDef(String s) {
        return null;
    }

    public FilterDef[] findFilterDefs() {
        return new FilterDef[0];
    }

    public FilterMap[] findFilterMaps() {
        return new FilterMap[0];
    }

    public String[] findInstanceListeners() {
        return new String[0];
    }

    public ContextLocalEjb findLocalEjb(String s) {
        return null;
    }

    public ContextLocalEjb[] findLocalEjbs() {
        return new ContextLocalEjb[0];
    }

    public String findMimeMapping(String s) {
        return null;
    }

    public String[] findMimeMappings() {
        return new String[0];
    }

    public String findParameter(String s) {
        return null;
    }

    public String[] findParameters() {
        return new String[0];
    }

    public ContextResource findResource(String s) {
        return null;
    }

    public String findResourceEnvRef(String s) {
        return null;
    }

    public String[] findResourceEnvRefs() {
        return new String[0];
    }

    public ContextResource[] findResources() {
        return new ContextResource[0];
    }

    public String findRoleMapping(String s) {
        return null;
    }

    public boolean findSecurityRole(String s) {
        return false;
    }

    public String[] findSecurityRoles() {
        return new String[0];
    }

    public String findServletMapping(String s) {
        synchronized (servletMappings){
            return (String)servletMappings.get(s);
        }
    }

    public String[] findServletMappings() {
        return new String[0];
    }

    public String findStatusPage(int i) {
        return null;
    }

    public int[] findStatusPages() {
        return new int[0];
    }

    public String findTaglib(String s) {
        return null;
    }

    public String[] findTaglibs() {
        return new String[0];
    }

    public boolean findWelcomeFile(String s) {
        return false;
    }

    public String[] findWelcomeFiles() {
        return new String[0];
    }

    public String[] findWrapperLifecycles() {
        return new String[0];
    }

    public String[] findWrapperListeners() {
        return new String[0];
    }

    public void reload() {

    }

    public void removeApplicationListener(String s) {

    }

    public void removeApplicationParameter(String s) {

    }

    public void removeConstraint(SecurityConstraint securityConstraint) {

    }

    public void removeEjb(String s) {

    }

    public void removeEnvironment(String s) {

    }

    public void removeErrorPage(ErrorPage errorPage) {

    }

    public void removeFilterDef(FilterDef filterDef) {

    }

    public void removeFilterMap(FilterMap filterMap) {

    }

    public void removeInstanceListener(String s) {

    }

    public void removeLocalEjb(String s) {

    }

    public void removeMimeMapping(String s) {

    }

    public void removeParameter(String s) {

    }

    public void removeResource(String s) {

    }

    public void removeResourceEnvRef(String s) {

    }

    public void removeRoleMapping(String s) {

    }

    public void removeSecurityRole(String s) {

    }

    public void removeServletMapping(String s) {

    }

    public void removeTaglib(String s) {

    }

    public void removeWelcomeFile(String s) {

    }

    public void removeWrapperLifecycle(String s) {

    }

    public void removeWrapperListener(String s) {

    }

    public String getInfo() {
        return null;
    }

    public Loader getLoader() {
        if (loader != null)
            return (loader);
        if (parent != null)
            return (parent.getLoader());
        return (null);
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
        return null;
    }

    public void setName(String s) {

    }

    public Container getParent() {
        return null;
    }

    public void setParent(Container container) {

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
        container.setParent((Container) this);
        children.put(container.getName(), container);
    }

    public void addContainerListener(ContainerListener containerListener) {

    }

    public void addMapper(Mapper mapper) {

        mapper.setContainer((Container) this);      // May throw IAE
        this.mapper = mapper;
        synchronized(mappers) {
            if (mappers.get(mapper.getProtocol()) != null)
                throw new IllegalArgumentException("addMapper:  Protocol '" +
                        mapper.getProtocol() + "' is not unique");
            mapper.setContainer((Container) this);      // May throw IAE
            mappers.put(mapper.getProtocol(), mapper);
            if (mappers.size() == 1)
                this.mapper = mapper;
            else
                this.mapper = null;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    public Container findChild(String s) {
        if(s == null)
            return null;
        synchronized (children){
            return (Container) children.get(s);
        }
    }

    public Container[] findChildren() {
        synchronized (children){
            Container results[] = new Container[children.size()];
            return (Container[]) children.values().toArray(results);
        }
    }

    public Mapper findMapper(String s) {
        // the default mapper will always be returned, if any,
        // regardless the value of protocol
        if (mapper != null)
            return (mapper);
        else
            synchronized (mappers) {
                return ((Mapper) mappers.get(s));
            }
    }

    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    public void invoke(Request request, Response response) throws IOException, ServletException {
        pipeline.invoke(request, response);
    }

    public Container map(Request request, boolean b) {
        Mapper mapper = findMapper(request.getRequest().getProtocol());
        if (mapper == null)
            return (null);

        // Use this Mapper to perform this mapping
        return (mapper.map(request, b));
    }

    public void removeChild(Container container) {

    }

    public void removeContainerListener(ContainerListener containerListener) {

    }

    public void removeMapper(Mapper mapper) {

    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    public Valve getBasic() {
        return pipeline.getBasic();
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

    }
}
