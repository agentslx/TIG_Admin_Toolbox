package org.tig.android.tigadmintoolbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by nguye on 6/8/2016.
 */
public class MainMenuActivity extends Activity {

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
                Intent intent = new Intent(MainMenuActivity.this,MemberManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}
