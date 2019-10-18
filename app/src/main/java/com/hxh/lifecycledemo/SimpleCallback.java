package com.hxh.lifecycledemo;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;

/**
 * Created by HXH at 2019/10/18
 * 不支持生命周期监听处理的回调
 */
public abstract class SimpleCallback implements Callback {

    @Override
    public final void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        // do no
    }
}
