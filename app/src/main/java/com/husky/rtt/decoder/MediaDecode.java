package com.husky.rtt.decoder;

import android.util.Log;
import android.view.Surface;

import com.husky.rtt.decoder.listener.OnVideoDecoderConfigListener;

public class MediaDecode implements OnVideoDecoderConfigListener {
    private static final String TAG = "MediaDecode";

    private VideoChannel videoChannel;

    public MediaDecode(Surface surface) {
        videoChannel = new VideoChannel(surface);
        videoChannel.setOnDecoderConfigListener(this);
    }

    public void start() {
        Log.d(TAG, "start>>");
        videoChannel.config();
    }

    public void stop() {
        Log.d(TAG, "stop>>");
        videoChannel.stop();
    }

    public VideoChannel getOnSocketCallback() {
        return videoChannel;
    }

    @Override
    public void onDecoderConfigSuccess() {
        Log.d(TAG, "onDecoderConfigSuccess>>");
        videoChannel.start();
    }

    @Override
    public void onDecoderConfigFailed() {
        Log.d(TAG, "onDecoderConfigFailed>>");
        videoChannel.stop();
    }
}
