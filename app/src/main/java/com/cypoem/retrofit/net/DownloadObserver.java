package com.cypoem.retrofit.net;

import com.cypoem.retrofit.utils.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhpan on 2018/3/20.
 */

public abstract   class DownloadObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        onFail(e);
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    abstract public void onSuccess(T t);

     public void onFail(Throwable e){
         ToastUtils.show(e.getMessage());
     }

     public void onFinish(){

     }

}
