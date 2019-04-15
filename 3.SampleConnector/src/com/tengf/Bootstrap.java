package com.tengf;

import com.tengf.connector.http.HttpConnector;

public class Bootstrap {

    public static void main(String[] args) {
        System.out.println("HttpConnector Start! ");
        HttpConnector connector = new HttpConnector();
        connector.start();
        //System.out.println("HttpConnector Stop! ");
    }
}
