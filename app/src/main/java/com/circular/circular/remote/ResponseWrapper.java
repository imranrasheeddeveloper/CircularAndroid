package com.circular.circular.remote;

public class ResponseWrapper<T> {

    private final boolean loading;
    private final String error;
    private final T data;

    public ResponseWrapper(boolean loading, String error, T data) {
        this.loading = loading;
        this.error = error;
        this.data = data;
    }

    public boolean isLoading() {
        return loading;
    }

    public String getError() {
        return error;
    }

    public T getData() {
        return data;
    }
}
