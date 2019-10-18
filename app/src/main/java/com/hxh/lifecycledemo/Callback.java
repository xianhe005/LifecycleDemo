package com.hxh.lifecycledemo;

import android.arch.lifecycle.GenericLifecycleObserver;

/**
 * Created by HXH at 2019/10/18
 * 模拟回调
 */
@SuppressWarnings("RestrictedApi")
public interface Callback extends GenericLifecycleObserver {
    void onSuccess(String result);

    void onFailure(String error);
}
