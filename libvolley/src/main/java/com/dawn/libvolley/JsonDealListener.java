package com.dawn.libvolley;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonDealListener<M> implements IHttpListener{
    private Class<M> reponceClass;
    private IJsonListener<M> jsonListener;
    Handler handler = new Handler(Looper.getMainLooper());

    public JsonDealListener(Class<M> reponceClass, IJsonListener<M> jsonListener) {
        this.reponceClass = reponceClass;
        this.jsonListener = jsonListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        try {
            String content = getContent(inputStream);
            final M response;
            response = JSON.parseObject(content, reponceClass);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(null != jsonListener){
                        jsonListener.onSuccess(response);
                    }
                }
            });
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(null != jsonListener){
                        jsonListener.onError(new Exception("Parse Error"));
                    }
                }
            });
        }

    }

    private String getContent(InputStream inputStream) throws Throwable{
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();

            String line = "";
            while (TextUtils.isEmpty(line = reader.readLine())){
                sb.append(line).append("\n");
                line = "";
            }

            if(null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        return sb.toString();
    }

    @Override
    public void onFailure() {

    }
}
