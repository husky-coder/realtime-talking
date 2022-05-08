package com.husky.rtt.manager;

import android.util.Log;

public class AnswerManager {
    private static final String TAG = "AnswerManager";

    // 静态内部类
    private static class AnswerManagerHolder {
        private static final AnswerManager INSTANCE = new AnswerManager();
    }

    private AnswerManager() {
        Log.d(TAG, "AnswerManager>>构造函数");
        // 防止反射破坏代理模式
        if (getInstance() != null) {
            throw new RuntimeException();
        }
    }

    public static AnswerManager getInstance() {
        return AnswerManagerHolder.INSTANCE;
    }

    public void start() {
        Log.d(TAG, "start>>");
    }

    public void stop() {
        Log.d(TAG, "stop>>");
    }
}
