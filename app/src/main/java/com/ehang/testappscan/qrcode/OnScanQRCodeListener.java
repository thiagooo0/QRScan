package com.ehang.testappscan.qrcode;

import android.graphics.Bitmap;

/**
 * 扫描二维码的监听器
 * Created by GuoShaoHong on 2017/6/15.
 */

public interface OnScanQRCodeListener {
    /**
     * 扫描成功后
     *
     * @param resultString 二维码里的字符串
     * @param bitmap       图片
     */
    void onSuccess(String resultString, Bitmap bitmap);

    /**
     * 扫描二维码失败
     * 他会失败很多次~。~
     */
    void onFail();
}
