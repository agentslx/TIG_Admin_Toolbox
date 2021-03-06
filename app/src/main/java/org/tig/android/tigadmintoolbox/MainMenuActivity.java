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
    private Button btActivitiesManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_activity);

        // btScan = (Button)findViewById(R.id.btScan);
        // formatTxt = (TextView)findViewById(R.id.scan_format);
        // contentTxt = (TextView)findViewById(R.id.scan_content);

        btMemberManagement = (Button) findViewById(R.id.btMemberManagement);
        btActivitiesManagement = (Button) findViewById(R.id.btActivitiesManagement);

        btMemberManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this,MemberManagementActivity.class);
                startActivity(intent);
            }
        });

        btActivitiesManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this,ActivtiesManagementActivity.class));
            }
        });
    }

//    public void btMainMenuMemberManagementOnclick(View v) {
//        Intent intent = new Intent(MainMenuActivity.this, MemberManagementActivity.class);
//        startActivity(intent);
//    }
}
