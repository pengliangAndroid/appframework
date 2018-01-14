package com.wstro.app.common.data.net.exception;

import android.content.Context;

import com.wstro.app.common.data.net.resp.BaseResp;
import com.wstro.app.common.utils.LogUtil;

import io.reactivex.Observer;

/**
 * Subscriber基类,可以在这里处理client网络连接状况
 * （比如没有，没有4g，没有联网，加载框处理等）
 * @author pengl
 */
public abstract class ApiSubscriber<T,R> implements Observer<T> {

    private Context context;

    public ApiSubscriber() {
    }

    public ApiSubscriber(Context context) {
        this.context = context;
    }


    @Override
    public void onError(Throwable e) {
        LogUtil.d("ApiSubscriber.throwable ="+e.toString());
        LogUtil.d("ApiSubscriber.throwable ="+e.getMessage());

        if(e instanceof Exception){
            //访问获得对应的Exception
            onFail(ExceptionHandle.handleException(e));
        }else {
            //将Throwable 和 未知错误的status code返回
            onFail(new ApiException(e,ExceptionHandle.ERROR.UNKNOWN));
        }

    }

    @Override
    public void onNext(T data) {
        if(data == null)
            return;

        BaseResp baseResp = (BaseResp) data;
        BaseResp.Meta meta = baseResp.getMeta();

        if(meta == null || meta.getCode() == 0){
            R model = (R) baseResp.getData();
            onSuccess(model);
        }else{
            onFail(new ApiException(meta.getMessage(),meta.getCode()));
        }

    }

    @Override
    public void onComplete() {
        LogUtil.d("ApiSubscriber.onCompleted()");
    }

    /**
     * 错误回调
     */
    protected abstract void onFail(ApiException ex);

    /**
     * 成功回调
     * @param data
     */
    public abstract void onSuccess(R data);

}