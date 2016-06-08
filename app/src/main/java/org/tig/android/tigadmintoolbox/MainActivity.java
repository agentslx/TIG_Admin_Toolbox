package org.tig.android.tigadmintoolbox;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ServiceManager serviceManager;

    TextView tvTest;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                   tvTest.setText("Get 1 from Service");
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceManager = new ServiceManager(this, TIGMainService.class, handler);

        serviceManager.start();

        tvTest = (TextView) findViewById(R.id.tvtest);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //clientServiceCommunicator.sendMessageToService(Message.obtain(handler,2));
    }

    @Override
    protected void onDestroy() {
//        try {
//            clientServiceCommunicator.doUnbindService();
//        }
//        catch (Throwable t) {
//           // Log.e("MainActivity", "Failed to unbind from the service", t);
//        }
        super.onDestroy();
        try {
            serviceManager.unbind();
        } catch (Throwable t) {
        }
    }
}
