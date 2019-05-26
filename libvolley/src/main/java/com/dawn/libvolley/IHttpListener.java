package com.dawn.libvolley;

import java.io.InputStream;

public interface IHttpListener {
    void onSuccess(InputStream inputStream);

    void onFailure();
}
