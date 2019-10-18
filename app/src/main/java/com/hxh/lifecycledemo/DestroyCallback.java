package com.hxh.lifecycledemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

/**
 * Created by HXH at 2019/10/18
 * 支持生命周期销毁处理的回调
 */
public abstract class DestroyCallback implements Callback {

    private boolean destroyed;

    @Override
    public final void onSuccess(String result) {
        if (destroyed) {
            return;
        }
        this.onSuccess0(result);
    }

    protected abstract void onSuccess0(String result);

    @Override
    public final void onFailure(String error) {
        if (destroyed) {
            return;
        }
        this.onFailure0(error);
    }

    protected abstract void onFailure0(String error);

    @Override
    public final void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            destroyed = true;
        }
    }
}
