package com.husky.rtt;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.husky.rtt.manager.CallManager;

public class CallActivity extends AppCompatActivity {
    private static final String TAG = "CallActivity";

    private SurfaceView localSurfaceView;
    private SurfaceView remoteSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        localSurfaceView = findViewById(R.id.local_surface);
        remoteSurfaceView = findViewById(R.id.remote_surface);

        localSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "localSurfaceView-->surfaceCreated>>");
                // 开启摄像头预览
                CallManager.getInstance()
                        .context(CallActivity.this)
                        .surfaceHolder(surfaceHolder)
                        .start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.d(TAG, "localSurfaceView-->surfaceChanged>>");

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "localSurfaceView-->surfaceDestroyed>>");

            }
        });

        remoteSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "remoteSurfaceView-->surfaceCreated>>");

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.d(TAG, "remoteSurfaceView-->surfaceChanged>>");

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "remoteSurfaceView-->surfaceDestroyed>>");

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CallManager.getInstance().stop();
    }
}
