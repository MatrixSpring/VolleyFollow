package com.dawn.libvolley;

public interface IHttpService {
    void setUrl(String url);

    void execute();

    void setHttpCallBack(IHttpListener httpListener);
}
