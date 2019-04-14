package com.tengf.realm;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;

import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleRealm implements Realm {

    public SimpleRealm(){
        createUserDatabase();
    }

    private Container container;
    private ArrayList users = new ArrayList();

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public String getInfo() {
        return "A simple Realm implementation";
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    public Principal authenticate(String username, String credentials) {
        System.out.println("SimpleRealm.authenticate()");
        if(username==null || credentials==null)
            return null;
        User user = getUser(username,credentials);
        if(user==null)
            return null;
        return new GenericPrincipal(this,user.username,user.password,user.getRoles());
    }

    public Principal authenticate(String s, byte[] bytes) {
        return null;
    }

    public Principal authenticate(String s, String s1, String s2, String s3, String s4, String s5, String s6, String s7) {
        return null;
    }

    public Principal authenticate(X509Certificate[] x509Certificates) {
        return null;
    }

    public boolean hasRole(Principal principal, String role) {
        if(principal==null || role==null || !(principal instanceof GenericPrincipal))
            return false;
        GenericPrincipal gp = (GenericPrincipal) principal;
        if(gp.getRealm() != this)
            return false;
        return gp.hasRole(role);
    }

    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    private User getUser(String username,String password){
        Iterator iterator = users.iterator();
        while (iterator.hasNext()){
            User user = (User) iterator.next();
            if(user.username.equals(username) && user.password.equals(password))
                return user;
        }
        return null;
    }

    public void createUserDatabase(){
        User user1 = new User("ken","blackcomb");
        user1.addRole("manager");
        user1.addRole("programmer");
        User user2 = new User("cindy", "bamboo");
        user2.addRole("programmer");

        users.add(user1);
        users.add(user2);
    }
    class User {

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String username;
        public ArrayList roles = new ArrayList();
        public String password;

        public void addRole(String role) {
            roles.add(role);
        }
        public ArrayList getRoles() {
            return roles;
        }
    }
}
