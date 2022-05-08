package com.husky.rtt.socket.listener;

public interface OnSocketClientCallback {
    void onReceiveMessage(byte[] frame);
}
