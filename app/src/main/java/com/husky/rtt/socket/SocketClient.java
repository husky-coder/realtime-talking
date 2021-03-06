package com.husky.rtt.socket;

import android.util.Log;
import android.view.Surface;

import com.husky.rtt.socket.listener.OnSocketClientCallback;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

public class SocketClient {
    private static final String TAG = "SocketClient";

    private static final String HOST = "192.168.1.104";
    private static final int PORT = 8889;

    private String host = HOST;
    private int port = PORT;
//    private MediaDecode mediaDecode;
    private ProjectionWebSocketClient mWebSocketClient;
    // 回调监听
    private OnSocketClientCallback onSocketClientCallback;

    public SocketClient(Surface surface) {
        // 创建解码
//        this.mediaDecode = new MediaDecode(surface);
//        this.onSocketClientCallback = mediaDecode.getOnSocketCallback();
        try {
            // 创建socket客户端
            URI uri = new URI("ws://192.168.1.104:8889");
            mWebSocketClient = new ProjectionWebSocketClient(uri);
        } catch (URISyntaxException e) {
            Log.w(TAG, "");
        }
    }

    /**
     * 开启链接
     */
    public void start() {
        Log.d(TAG, "start>>");
        if (mWebSocketClient != null) {
            mWebSocketClient.connect();
        }
    }

    /**
     * 关闭链接
     */
    public void stop() {
        Log.d(TAG, "stop>>");
        if (mWebSocketClient != null) {
            mWebSocketClient.close();
        }
    }

    /**
     * WebSocket客户端
     */
    class ProjectionWebSocketClient extends WebSocketClient {

        public ProjectionWebSocketClient(URI serverUri) {
            super(serverUri);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            Log.d(TAG, "onOpen>>");
//            if (mediaDecode != null) {
//                mediaDecode.start();
//            }
        }

        @Override
        public void onMessage(String message) {
            Log.d(TAG, "onMessage>>" + message);
        }

        @Override
        public void onMessage(ByteBuffer bytes) {
            Log.d(TAG, "onMessage>>");
            byte[] frame = new byte[bytes.remaining()];
            bytes.get(frame);
            // 回调
            if (onSocketClientCallback != null) {
                onSocketClientCallback.onReceiveMessage(frame);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            Log.d(TAG, "onClose>>");
            mWebSocketClient = null;
//            if (mediaDecode != null) {
//                mediaDecode.stop();
//                mediaDecode = null;
//            }
        }

        @Override
        public void onError(Exception ex) {
            Log.d(TAG, "onError>>" + ex.toString());
            mWebSocketClient = null;
//            if (mediaDecode != null) {
//                mediaDecode.stop();
//                mediaDecode = null;
//            }
        }
    }
}
