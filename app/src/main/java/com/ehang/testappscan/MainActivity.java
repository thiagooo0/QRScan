package com.ehang.testappscan;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ehang.testappscan.base.BaseActivity;
import com.ehang.testappscan.qrcode.OnScanQRCodeListener;
import com.ehang.testappscan.qrcode.ScanQRUtil;
import com.ehang.testappscan.qrcode.ViewfinderView;

/**
 * welcome
 */
public class MainActivity extends BaseActivity implements OnScanQRCodeListener {
    private FrameLayout frameLayout;
    private ScanQRUtil scanQRUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = ((FrameLayout) findViewById(R.id.framelayout));

        scanQRUtil = new ScanQRUtil(this, frameLayout
                , ((ViewfinderView) findViewById(R.id.finder)));
        scanQRUtil.setListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanQRUtil.release();
    }

    @Override
    public void onSuccess(String resultString, Bitmap bitmap) {
        Toast.makeText(this, resultString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFail() {
    }
}
