package com.circular.circular.remote;

import okhttp3.ResponseBody;

public class ResponseWrapper<T> {

    private final boolean loading;
    private final ResponseBody error;
    private final T data;

    public ResponseWrapper(boolean loading, ResponseBody error, T data) {
        this.loading = loading;
        this.error = error;
        this.data = data;
    }

    public boolean isLoading() {
        return loading;
    }

    public ResponseBody getError() {
        return error;
    }

    public T getData() {
        return data;
    }
}
