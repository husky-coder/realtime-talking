package com.husky.rtt.manager;

import android.content.Context;
import android.util.Log;
import android.view.SurfaceHolder;

import com.husky.rtt.camera1.Camera1;

/**
 * 1、开启摄像头预览
 * 2、连接网络
 * 3、开启编码并发送
 */
public class CallManager {
    private static final String TAG = "CallManager";

    private Context mContext;
    private SurfaceHolder mSurfaceHolder;
    private Camera1 mCamera;

    // 静态内部类
    private static class CallManagerHolder {
        private static final CallManager INSTANCE = new CallManager();
    }

    private CallManager() {
        Log.d(TAG, "CallManager>>构造函数");
        // 防止反射破坏代理模式
        if (getInstance() != null) {
            throw new RuntimeException();
        }
    }

    public static CallManager getInstance() {
        return CallManagerHolder.INSTANCE;
    }

    public CallManager context(Context context) {
        this.mContext = context;
        return CallManagerHolder.INSTANCE;
    }

    public CallManager surfaceHolder(SurfaceHolder surfaceHolder) {
        this.mSurfaceHolder = surfaceHolder;
        return CallManagerHolder.INSTANCE;
    }

    public void start() {
        Log.d(TAG, "start>>");
        // 1、开启摄像头预览
        mCamera = new Camera1(mContext, mSurfaceHolder, 1);
        mCamera.startPreview();
        // 2、连接网络

        // 3、开启编码并发送

    }

    public void stop() {
        Log.d(TAG, "stop>>");
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.closeCamera();
        }
    }
}
