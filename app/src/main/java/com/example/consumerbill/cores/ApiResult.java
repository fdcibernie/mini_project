package com.example.consumerbill.cores;

public class ApiResult<T> {
    private final T data;
    private final ResponseStatus apiStatus;
    private final String errorMessage;

    public ApiResult(T data, ResponseStatus apiStatus,String errorMessage) {
        this.data = data;
        this.apiStatus = apiStatus;
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public ResponseStatus getApiStatus() {
        return apiStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
