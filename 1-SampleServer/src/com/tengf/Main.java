package com.tengf;

public class Main {

    public static void main(String[] args) {

        System.out.println("HttpServer Start! ");
        HttpServer server = new HttpServer();
        server.await();
        System.out.println("HttpServer Stop! ");
    }
}
