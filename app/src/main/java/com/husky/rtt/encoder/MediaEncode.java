package com.husky.rtt.encoder;

import android.content.Context;
import android.util.Log;

import com.husky.rtt.encoder.listener.OnVideoEncoderConfigListener;
import com.husky.rtt.socket.listener.OnSocketServerCallback;

public class MediaEncode implements OnVideoEncoderConfigListener {
    private static final String TAG = "MediaEncode";
    // 视频轨道编码
    private VideoChannel videoChannel;

    public MediaEncode(Context context, OnSocketServerCallback onSocketServerCallback) {
        videoChannel = new VideoChannel(context, onSocketServerCallback);
        videoChannel.setOnVideoEncoderConfigListener(this);
    }

    /**
     * 开启
     *
     * @return
     */
    public void start() {
        Log.d(TAG, "start>>");
        if (videoChannel != null) {
            videoChannel.config();
        }
    }

    /**
     * 关闭
     */
    public void stop() {
        Log.d(TAG, "stop>>");
        if (videoChannel != null) {
            videoChannel.stop();
        }
    }

    @Override
    public void onEncoderConfigSuccess() {
        Log.d(TAG, "onEncoderConfigSuccess>>");
        videoChannel.start();
    }

    @Override
    public void onEncoderConfigFailed() {
        Log.d(TAG, "onEncoderConfigFailed>>");
        videoChannel.stop();
    }
}
