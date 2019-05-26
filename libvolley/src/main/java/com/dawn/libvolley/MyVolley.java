package com.dawn.libvolley;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.FutureTask;

public class MyVolley {
    private MyVolley myVolley;

    public static <T extends Serializable, M> void  sendRequest(String url,T requestBody, Class<M> response, IJsonListener<M> httpCallBack){
        IHttpListener iHttpListener = new JsonDealListener(response, httpCallBack);
        HttpTask<T> httpTask = new HttpTask<T>(requestBody, url,getRequestHead(),iHttpListener);
        ThreadPoolManager.getInstance().execute(new FutureTask<Object>(httpTask, null));
    };


    private static HashMap<String,String> getRequestHead(){
        return new HashMap<>();
    }
}
