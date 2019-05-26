package com.dawn.libvolley;

public interface IJsonListener<M> {
    void onSuccess(M m);

    void onError(Exception e);
}
