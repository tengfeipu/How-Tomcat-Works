package com.tengf;

import java.io.IOException;

public class StaticResourceProcessor {

    public void process(Request request,Response response){
        try {
            response.sendStaticResource();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
