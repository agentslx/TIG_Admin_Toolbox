package org.tig.android.tigadmintoolbox;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ServiceManager serviceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceManager = new ServiceManager(this, TIGMainService.class, new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){

                }
            }
        });

        serviceManager.start();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(MainActivity.this,MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            serviceManager.unbind();
        } catch (Throwable t) {
        }
    }
}
