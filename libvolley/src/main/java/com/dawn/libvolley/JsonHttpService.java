package com.dawn.libvolley;

import android.util.SparseArray;

import java.util.HashMap;

public class JsonHttpService implements IHttpService{
    IHttpListener iHttpListener;
    String url = "";
    HashMap<String,String> requestHead;
    //requestType;
//    HashMap<String, String> requestBody;
    Object requestBody;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void execute() {
        //封装请求
    }

    @Override
    public void setHttpCallBack(IHttpListener httpListener) {

    }
}
