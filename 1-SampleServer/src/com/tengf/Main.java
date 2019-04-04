package com.tengf;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        System.out.println("HttpServer Start! ");
        HttpServer server = new HttpServer();
        server.await();
    }
}
