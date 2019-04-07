package com.tengf.processor;

import com.tengf.connector.http.HttpRequest;
import com.tengf.connector.http.HttpResponse;

import java.io.IOException;

public class StaticResourceProcessor {

    public void process(HttpRequest request, HttpResponse response){
        try {
            response.sendStaticResource();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
