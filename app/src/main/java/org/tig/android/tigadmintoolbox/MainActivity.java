package org.tig.android.tigadmintoolbox;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    //private boolean permissionCameraAccepted = false;

  //  private Button btScan;
   // private TextView formatTxt, contentTxt;

    private Button btMemberManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // btScan = (Button)findViewById(R.id.btScan);
       // formatTxt = (TextView)findViewById(R.id.scan_format);
       // contentTxt = (TextView)findViewById(R.id.scan_content);

        btMemberManagement = (Button) findViewById(R.id.btMemberManagement);

        btMemberManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MemberManagementActivity.class);
                startActivity(intent);
            }
        });

    }

}
