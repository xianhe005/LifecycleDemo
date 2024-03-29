package com.hxh.lifecycledemo;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Random;

/**
 * {@link android.support.v4.app.SupportActivity}'s lifecycle
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HXH";
    private Callback mCallback1;
    private Callback mCallback2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl, MainFragment.newInstance())
                .commit();

        getLifecycle().addObserver((GenericLifecycleObserver) (source, event) -> {
            Log.i(TAG, "onStateChanged: " + source.getClass().getName());
            Log.i(TAG, "onStateChanged: " + event.name());
        });

        mCallback1 = new DestroyCallback() {
            @Override
            protected void onSuccess0(String result) {
                Log.i(TAG, "onSuccess0: " + result);
            }

            @Override
            protected void onFailure0(String error) {
                Log.i(TAG, "onFailure0: " + error);
            }
        };
        getLifecycle().addObserver(mCallback1);

        mCallback2 = new SimpleCallback() {
            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: " + result);

            }

            @Override
            public void onFailure(String error) {
                Log.i(TAG, "onFailure: " + error);
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
    protected void onDestroy() {
        getLifecycle().removeObserver(mCallback1);
        super.onDestroy();
    }

}
