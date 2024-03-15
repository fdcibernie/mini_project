package com.example.consumerbill.cores.interfaces;

import com.example.consumerbill.cores.ApiResult;

public interface IApiListener<T> {
    void onApiResponse(ApiResult<T> result);
}
