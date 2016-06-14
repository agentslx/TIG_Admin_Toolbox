package org.tig.android.tigadmintoolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import java.util.ArrayList;
import java.util.List;

/**
 * This sample performs continuous scanning, displaying the barcode and source image whenever
 * a barcode is scanned.
 */
public class ContinuousCaptureActivity extends Activity {
    private static final String TAG = ContinuousCaptureActivity.class.getSimpleName();

    private CompoundBarcodeView barcodeView;

    private TextView tvScanStatus, tvScanResult;

    private String actID;
    private ArrayList<String> memID;

//    private ServiceManager serviceManager;

    private ArrayList<TIGMemberShort> allMem;

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                String res = result.getText().toString();

                String show = "Scan: " + res;

                for (TIGMemberShort m :
                        allMem) {
                    if(m.getID().matches(res)){
                        show = show + "\n" + "Member: " + m.getLastName() + " " +m.getFistName();
                        boolean check = true;
                        for (String i :
                                memID) {
                            if(i.matches(m.getID())){
                                check = false;
                                show = show + "\n" + "This member was already checked in!";
                                break;
                            }
                        }
                        if(check) {
                            show = show + "\n" + "checked in!";
                            ArrayList<String> a = new ArrayList();
                            a.add(actID);
                            a.add(m.getID());
                            memID.add(m.getID());
//                            try {
//                                serviceManager.send(Message.obtain(null,ActivtiesManagementActivity.ACTIVITIES_MANAGER_ADD_NEW_MEMBER_CHECKIN,a));
//                            } catch (RemoteException e) {
//                                e.printStackTrace();
//                            }
                            Intent intent = new Intent(ActivtiesManagementActivity.KEY_PASS_RESULT_SCAN_CODE);
                            intent.putStringArrayListExtra(ActivtiesManagementActivity.KEY_REI_RESULT_SCAN_CODE,a);
                            LocalBroadcastManager.getInstance(ContinuousCaptureActivity.this).sendBroadcast(intent);

                        }
                        break;
                    }
                }
//                Intent intent = new Intent()
                barcodeView.setStatusText(show);

            }
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

        Intent intent = getIntent();
        actID = intent.getStringExtra(ActivtiesManagementActivity.KEY_PASS_ACT_ID);
        memID = intent.getStringArrayListExtra(ActivtiesManagementActivity.KEY_PASS_ACT_MEMBER);
        allMem = (ArrayList<TIGMemberShort>) intent.getSerializableExtra(ActivtiesManagementActivity.KEY_PASS_ACT_MEMBER_ID_LIST);

//        serviceManager = intent.getParcelableExtra(ActivtiesManagementActivity.KEY_PASS_SERVICE_MANAGER);

        for (int i = 0; i < allMem.size(); i++) {
            Log.i("Continuous Capture","Received allMemList: "+allMem.get(i).getID() + " " + allMem.get(i).getLastName() + " " + allMem.get(i).getFistName());
        }

//        serviceManager = new ServiceManager(ContinuousCaptureActivity.this,TIGMainService.class,new Handler(){
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//            }
//        });
//        serviceManager.start();

//        serviceManager = new ServiceManager(ContinuousCaptureActivity.this,TIGMainService.class,handler);
//        serviceManager.start();
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

    @Override
    public void onBackPressed() {

//        if(newMemID.size()>0){
//            Intent intent = new Intent();
//            intent.putStringArrayListExtra(ActivtiesManagementActivity.KEY_REI_ACT_MEMBER,newMemID);
//            intent.putExtra(ActivtiesManagementActivity.KEY_REI_ACT_ID,actID);
//            setResult(ActivtiesManagementActivity.SCAN_RESULT_CODE,intent);
//        }

        finish();
    }

    @Override
    protected void onDestroy() {

//        Intent intent = new Intent();
//        intent.putStringArrayListExtra(ActivtiesManagementActivity.KEY_REI_ACT_MEMBER,newMemID);
//        intent.putExtra(ActivtiesManagementActivity.KEY_REI_ACT_ID,actID);
//        setResult(ActivtiesManagementActivity.SCAN_RESULT_CODE,intent);
//
        super.onDestroy();
//        try {
//            serviceManager.unbind();
//        } catch (Throwable t) {
//        }
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