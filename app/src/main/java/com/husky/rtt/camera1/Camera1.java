package com.husky.rtt.camera1;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 相机Camera1管理
 */
public class Camera1 {

    private final String TAG = "Camera1";

    private Context context;
    private Camera camera;              // 相机对象
    private Camera.Size previewSize;    // 预览尺寸
    private Camera.Size pictureSize;    // 实际尺寸

    private float rate = 1.778f;        // 宽高比例16:9
    private int minPreviewWidth = 720;  // 最小预览尺寸
    private int minPictureWidth = 720;  // 最小实际尺寸

    public Camera1(Context context, SurfaceHolder surfaceHolder, int cameraId) {
        this.context = context;
        openCamera(cameraId);
        setPreviewDisplay(surfaceHolder);
    }

    /**
     * 打开相机
     *
     * @param cameraId
     * @return
     */
    private boolean openCamera(int cameraId) {
        Log.d(TAG, "========openCamera========");
        try {
            camera = Camera.open(cameraId);
            Camera.Parameters parameters = camera.getParameters();
            parameters.set("orientation", "portrait");  // 设置方向为竖屏
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);   // 设置对焦方式为连续自动对焦图像

            previewSize = getPropPreviewSize(parameters.getSupportedPreviewSizes(), rate, minPreviewWidth);
            pictureSize = getPropPictureSize(parameters.getSupportedPictureSizes(), rate, minPictureWidth);
            parameters.setPreviewSize(previewSize.width, previewSize.height);   // 设置预览尺寸
            parameters.setPictureSize(pictureSize.width, pictureSize.height);   // 设置实际尺寸
            setCameraDisplayOrientation((Activity) context, cameraId, camera);  // 设置相机预览方向
            camera.setParameters(parameters);
            Log.d(TAG, "open camera suc");
        } catch (Exception e) {
            Log.w(TAG, "open camera fail");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 设置显示视图
     *
     * @param surfaceHolder
     */
    private void setPreviewDisplay(SurfaceHolder surfaceHolder) {
        if (camera != null) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开启预览
     */
    public void startPreview() {
        Log.d(TAG, "========startPreview========");
        if (camera != null) {
            camera.startPreview();
        }
    }

    /**
     * 停止预览
     */
    public void stopPreview() {
        Log.d(TAG, "========stopPreview========");
        if (camera != null) {
            camera.stopPreview();
        }
    }

    /**
     * 关闭相机
     */
    public void closeCamera() {
        Log.d(TAG, "========closeCamera========");
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    /**
     * 返回最佳实际尺寸
     *
     * @return
     */
    public Camera.Size getPictureSize() {
        return pictureSize;
    }

    /**
     * 返回最佳预览尺寸
     *
     * @return
     */
    public Camera.Size getPreviewSize() {
        return previewSize;
    }

    /**
     * 获取最佳预览尺寸
     *
     * @param list
     * @param th
     * @param minWidth
     * @return
     */
    private Camera.Size getPropPreviewSize(List<Camera.Size> list, float th, int minWidth) {

        Collections.sort(list, sizeComparator);

        int i = 0;
        for (Camera.Size s : list) {
            if ((s.height >= minWidth) && equalRate(s, th)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;
        }
        return list.get(i);
    }

    /**
     * 获取最佳实际尺寸
     *
     * @param list
     * @param th
     * @param minWidth
     * @return
     */
    private Camera.Size getPropPictureSize(List<Camera.Size> list, float th, int minWidth) {

        Collections.sort(list, sizeComparator);

        int i = 0;
        for (Camera.Size s : list) {
            if ((s.height >= minWidth) && equalRate(s, th)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            i = 0;
        }
        return list.get(i);
    }

    /**
     * Camera.Size列表排序规则
     */
    private Comparator<Camera.Size> sizeComparator = new Comparator<Camera.Size>() {

        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.height == rhs.height) {
                return 0;
            } else if (lhs.height > rhs.height) {
                return 1;
            } else {
                return -1;
            }
        }
    };

    /**
     * 比较宽高比例
     *
     * @param s
     * @param rate
     * @return
     */
    private boolean equalRate(Camera.Size s, float rate) {
        float r = (float) (s.width) / (float) (s.height);
        if (Math.abs(r - rate) <= 0.03) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置相机预览方向（官方推荐写法，open gles不再使用该方式设置相机预览方向，而是通过计算变换矩阵等来实现）
     *
     * @param activity
     * @param cameraId
     * @param camera
     */
    private void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }
}
