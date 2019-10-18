package com.hxh.lifecycledemo;

import android.annotation.SuppressLint;
import android.arch.lifecycle.GenericLifecycleObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

/**
 * Created by HXH at 2019/10/18
 * {@link Fragment}'s lifecycle
 */
public class MainFragment extends Fragment {

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private static final String TAG = "HXH";
    private Callback mCallback1;
    private Callback mCallback2;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLifecycle().addObserver((GenericLifecycleObserver) (source, event) -> {
            Log.i(TAG, "fragment --> onStateChanged: " + source.getClass().getName());
            Log.i(TAG, "fragment --> onStateChanged: " + event.name());
        });

        mCallback1 = new DestroyCallback() {
            @Override
            protected void onSuccess0(String result) {
                Log.i(TAG, "fragment --> onSuccess0: " + result);
            }

            @Override
            protected void onFailure0(String error) {
                Log.i(TAG, "fragment --> onFailure0: " + error);
            }
        };
        getLifecycle().addObserver(mCallback1);

        mCallback2 = new SimpleCallback() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "fragment --> onSuccess: " + result);

            }

            @Override
            public void onFailure(String error) {
                Log.i(TAG, "fragment --> onFailure: " + error);
            }
        };


        new Thread(() -> {
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (random.nextBoolean()) {
                    mCallback1.onSuccess("成功：" + i);
                    mCallback2.onSuccess("成功：" + i);
                } else {
                    mCallback1.onFailure("失败：" + i);
                    mCallback2.onFailure("失败：" + i);
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        getLifecycle().removeObserver(mCallback1);
        super.onDestroy();
    }
}
