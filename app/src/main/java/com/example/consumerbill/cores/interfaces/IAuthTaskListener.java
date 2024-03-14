package com.example.consumerbill.cores.interfaces;

import com.example.consumerbill.cores.ApiResult;

public interface IAuthTaskListener {
    void onCompleteTask(ApiResult<Void> result);
}
