package org.tig.android.tigadmintoolbox;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    public static final int MAIN_ACTIVITY_DELAY_OVER = 321;

    private static final int MAIN_ACTITITY_DELAY_TIME = 5000;

//    private ServiceManager serviceManager;
//    int dem = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MAIN_ACTIVITY_DELAY_OVER:
                    //Toast.makeText(MainActivity.this, "Welcome to TIG Admin Toolbox", Toast.LENGTH_SHORT).show();
                    Log.i("Main Activity", "receive message");
                    startActivity(new Intent(MainActivity.this, MainMenuActivity.class));
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        serviceManager = new ServiceManager(this, TIGMainService.class, new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                switch (msg.what) {
////                    case 30:
//////                        ArrayList<String> data = (ArrayList<String>) msg.obj;
//////                        String da = null;
//////                        for (int i = 0; i < data.size(); i++) {
//////                            da += data.get(i);
//////                        }
//////                        tvShow.setText(da);
////                        ArrayList<TIGMemberShort> data = (ArrayList<TIGMemberShort>) msg.obj;
////                        tvShow.setText(data.get(data.size()-1).getFistName()+data.get(data.size()-1).getLastName());
//                }
//            }
//        });

//        serviceManager.start();

        Intent intent = new Intent(MainActivity.this,TIGMainService.class);
        startService(intent);

        Message msg = handler.obtainMessage(MAIN_ACTIVITY_DELAY_OVER);
//        handler.sendMessageAtTime(msg, System.currentTimeMillis()+MAIN_ACTITITY_DELAY_TIME);
        handler.sendMessageDelayed(msg, MAIN_ACTITITY_DELAY_TIME);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        serviceManager.stop();
//        try {
//            serviceManager.unbind();
//        } catch (Throwable t) {
//        }
    }
}
