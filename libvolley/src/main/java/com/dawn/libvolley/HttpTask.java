package com.dawn.libvolley;

import java.io.Serializable;
import java.util.HashMap;

public class HttpTask<T> implements Runnable {
    private IHttpService httpService;

    public <T extends Serializable> HttpTask(T requestInfo, String Url, HashMap<String, String> requestHead, IHttpListener iHttpListener){
        //依据type设置不同的请求工具
        this.httpService = new JsonHttpService();
        ((JsonHttpService) this.httpService).url = Url;
        ((JsonHttpService) this.httpService).requestHead = requestHead;
        ((JsonHttpService) this.httpService).requestBody = requestInfo;
        ((JsonHttpService) this.httpService).iHttpListener = iHttpListener;
    }

    @Override
    public void run() {
        this.httpService.execute();
    }
}
