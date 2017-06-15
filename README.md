# QRScan
使用zxing扫描二维码。我自己封装了一层。使用会更简单喔

全部的功能都被封装在ScanQRUtil里面。新建一个scanQRUtil的对象，传入监听器，然后等着就好了。
```
        ScanQRUtil  scanQRUtil = new ScanQRUtil(this, frameLayout
                , ((ViewfinderView) findViewById(R.id.finder)));
        scanQRUtil.setListener(new OnScanQRCodeListener() {
            @Override
            public void onSuccess(String resultString, Bitmap bitmap) {
                
            }

            @Override
            public void onFail() {

            }
        });
```
 用完记得release
 >scanQRUtil.release();
 
 ---
 
 ViewFinderView:扫描框控件。
