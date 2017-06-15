package com.ehang.testappscan.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.ehang.testappscan.qrcode.camera.CameraManager;
import com.ehang.testappscan.qrcode.decoding.CaptureActivityHandler;
import com.ehang.testappscan.qrcode.decoding.InactivityTimer;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;

/**
 * 扫描二维码
 * Created by GuoShaoHong on 2017/5/3.
 */

public class ScanQRUtil {
    private SurfaceView mQRSurfaceView;
    private Context context;
    private boolean hasSurface = false;

    private Vector<BarcodeFormat> decodeFormats;

    private CaptureActivityHandler handler;

    private String characterSet;

    private OnScanQRCodeListener listener;

    private InactivityTimer inactivityTimer;


    private ViewfinderView viewfinderView;

    public ScanQRUtil(Context context, FrameLayout frameLayout, ViewfinderView viewfinderView) {
        this.context = context;
        this.viewfinderView = viewfinderView;

        CameraManager.init(context);

        openQR(frameLayout);
    }

    //打开二维码界面
    private void openQR(FrameLayout frameLayout) {
//		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);

        mQRSurfaceView = new SurfaceView(context);
        frameLayout.addView(mQRSurfaceView, 0);

        SurfaceHolder surfaceHolder = mQRSurfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(new QRSurfaceviewCallback());
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    //初始化相机
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            Log.d("ScanQRUtil", "surfaceHolder == null:" + (null == surfaceHolder));
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException | RuntimeException ioe) {
            ioe.printStackTrace();
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }


    //扫二维码的surface view的回调方法
    class QRSurfaceviewCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (!hasSurface) {
                hasSurface = true;
                initCamera(holder);
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            hasSurface = false;
            if (handler != null) {
                handler.stopScan();
                handler.quitSynchronously();
                handler = null;
            }
            CameraManager.get().closeDriver();

//            inactivityTimer.shutdown();
        }
    }

    /**
     * 对二维码的结果进行处理
     *
     * @param resultString
     * @param bitmap
     */
    private void onResultHandler(String resultString, Bitmap bitmap) {
        if (TextUtils.isEmpty(resultString)) {
//            Toast.makeText(context, "Scan failed!", Toast.LENGTH_SHORT).show();
            if (null != listener) {
                listener.onFail();
            }
            return;
        }
        //扫描成功
//        if (((EhangApplication) getApplication()).setWifi(resultString))
//        Toast.makeText(context, "Scan Success!", Toast.LENGTH_SHORT).show();

        if (null != listener) {
            listener.onSuccess(resultString, bitmap);
        }
//		handler.restartPreviewAndDecode();
//		bundle.putParcelable("bitmap", bitmap);
//		resultIntent.putExtras(bundle);
//		this.setResult(RESULT_OK, resultIntent);
//		MipcaActivityCapture.this.finish();
    }

    /**
     * 处理结果
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
//		inactivityTimer.onActivity();
        String resultString = result.getText();
        onResultHandler(resultString, barcode);
    }

    public void release() {
        context = null;
        CameraManager.get().stopPreview();
        CameraManager.get().closeDriver();
    }

    public Handler getHandler() {
        return handler;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    public void restartPreviewAndDecode() {
        handler.restartPreviewAndDecode();
    }

    public void setListener(OnScanQRCodeListener listener) {
        this.listener = listener;
    }
}
