package com.husky.rtt.socket.listener;

public interface OnSocketServerCallback {
    void onSendMessage(byte[] frame);
}
