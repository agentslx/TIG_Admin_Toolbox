package org.tig.android.tigadmintoolbox;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.List;

/**
 * This sample performs continuous scanning, displaying the barcode and source image whenever
 * a barcode is scanned.
 */
public class ContinuousCaptureActivity extends Activity {
    private static final String TAG = ContinuousCaptureActivity.class.getSimpleName();

    private CompoundBarcodeView barcodeView;

    private TextView tvScanStatus, tvScanResult;


    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                barcodeView.setStatusText(result.getText());

                //get the code, check the form. If valid, send to main service in order to find the infomations.
                //then, main service will return some basic infomation to this class, as well as the MemberManagementActivity to list them out.



//                String res= result.getText();
//                //manage the result string
//                int start_index = res.indexOf(VCARD_BEGIN);
//                if(start_index>=0){
//                    start_index += VCARD_BEGIN.length();
//                    if(res.indexOf(VCARD_VERSION)>=0){
//                        start_index += res.indexOf('\n',res.indexOf(VCARD_VERSION)+VCARD_VERSION.length());
//                    }
//                    if(res.indexOf(FNAME_PLASH, start_index)>=0){
//                        start_index = res.indexOf(NAME_PLASH,start_index)+NAME_PLASH.length();
//                    } else {
//                        start_index = res.indexOf(NAME_PLASH,start_index)+NAME_PLASH.length();
//                    }
//                    String name = res.substring(res.indexOf(NAME_PLASH,start_index)+NAME_PLASH.length(),
//                                     res.indexOf('\n',res.indexOf(NAME_PLASH,vcard_index+VCARD_BEGIN.length())+NAME_PLASH.length()));
//                    tvScanResult.setText("Name:"+name);
//                }
                //String name = res.substring(res.indexOf(NAME_PLASH,res.indexOf(VCARD_BEGIN))+VCARD_BEGIN.length()+NAME_PLASH.length(),
               //         res.indexOf('\n',res.indexOf(NAME_PLASH)+NAME_PLASH.length()));
               // String name = res.substring(res.indexOf(NAME_PLASH,res.indexOf(VCARD_BEGIN))+VCARD_BEGIN.length()+NAME_PLASH.length(),
                //        res.indexOf('\n',res.indexOf(NAME_PLASH)+NAME_PLASH.length()));
                //print out result

            }
            //Added preview of scanned barcode
            //ImageView imageView = (ImageView) findViewById(R.id.barcodePreview);
            //imageView.setImageBitmap(result.getBitmapWithResultPoints(Color.YELLOW));
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.continuous_scan);

        barcodeView = (CompoundBarcodeView) findViewById(R.id.barcode_scanner);
        barcodeView.decodeContinuous(callback);

        tvScanStatus = (TextView) findViewById(R.id.tvScanStatus);
        tvScanResult = (TextView) findViewById(R.id.tvScanResult);
    }

    @Override
    protected void onResume() {
        super.onResume();

        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        barcodeView.pause();
    }

    public void pause(View view) {
        barcodeView.pause();
        tvScanStatus.setText("Paused");
    }

    public void resume(View view) {
        barcodeView.resume();
        tvScanStatus.setText("Scanning...");
    }

    public void triggerScan(View view) {
        barcodeView.decodeSingle(callback);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}