package org.tig.android.tigadmintoolbox;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nguye on 6/11/2016.
 */
public class ActivtiesManagementActivity extends Activity {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1010;

    public static final int ACTIVITIES_MANAGER_ADD_NEW_ACTIVITY = 12135;
    public static final int ACTIVITIES_MANAGER_REFRESH_LIST = 12239;
    public static final int ACTIVITIES_MANAGER_REFRESH_MEMBER_LIST = 129;
    public static final int ACTIVITIES_MANAGER_REFRESH_CHECKIN_LIST = 1299;
    public static final int ACTIVITIES_MANAGER_FROM_SERVICE_ACTIVITY_LIST = 12155;
    public static final int ACTIVITIES_MANAGER_FROM_SERVICE_MEMBERLIST= 15;
    public static final int ACTIVITIES_MANAGER_FROM_SERVICE_CHECKIN_LIST= 17;

    public static final int ACTIVITIES_MANAGER_ADD_NEW_MEMBER_CHECKIN= 197;

//    public static final int SCAN_RESULT_CODE = 67345;

//    private final int ACTIVITIES_TIME_STOP = 1016601;

    public static final String KEY_PASS_ACT_ID = "Activity ID to pass";
    public static final String KEY_PASS_ACT_MEMBER = "Checked in Members ID to pass";
    public static final String KEY_PASS_ACT_MEMBER_ID_LIST = "All Members ID to pass";
    public static final String KEY_PASS_RESULT_SCAN_CODE = "Scan Member ID";
    public static final String KEY_REI_RESULT_SCAN_CODE = "Reiceived Member ID";

    private ServiceManager serviceManager;

    private Button btAddNewAct;
    private LinearLayout parActivitiesList;

    private ArrayList<TIGActivityShort> activities;
    private ArrayList<TIGMemberShort> members;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TIGMainService.SERVICE_IS_READY:
                    try {
                        serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_REFRESH_LIST));
                        serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_REFRESH_MEMBER_LIST));
                        //serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_REFRESH_CHECKIN_LIST));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;

                case ACTIVITIES_MANAGER_FROM_SERVICE_ACTIVITY_LIST:
                    activities = (ArrayList<TIGActivityShort>) msg.obj;
                    Log.i("Activity Manager", "Get new all activity list");
                    refreshList();
                    break;
                case ACTIVITIES_MANAGER_FROM_SERVICE_MEMBERLIST:

                    Log.i("Activity Manager", "Get new all Member list");
                    members = (ArrayList<TIGMemberShort>) msg.obj;
                    for (int i = 0; i < members.size(); i++) {
                        Log.i("Activity Manager","Found member: "+members.get(i).getID()+" - " + members.get(i).getLastName()+" "+members.get(i).getFistName());
                    }
                    break;
                case ACTIVITIES_MANAGER_FROM_SERVICE_CHECKIN_LIST:
                    //checkins = (ArrayList<TIGActivityCheckin>) msg.obj;
                    TIGActivityShort t = (TIGActivityShort) msg.obj;
                    for (int i = 0; i < t.getMemberIDCheckin().size(); i++) {
                        Log.i("Activity Manager","Get member from data list: " + t.getMemberIDCheckin().get(i));
                    }
                    showOptionalDialog(t);
                    break;

            }
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> a = intent.getStringArrayListExtra(KEY_REI_RESULT_SCAN_CODE);
            try {
                serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_ADD_NEW_MEMBER_CHECKIN,a));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_layout);

        serviceManager = new ServiceManager(ActivtiesManagementActivity.this, TIGMainService.class, handler);
        serviceManager.start();

        btAddNewAct = (Button) findViewById(R.id.btActManAddNewActivity);
        parActivitiesList = (LinearLayout) findViewById(R.id.parActManActivityList);

        activities = new ArrayList<>();
        members = new ArrayList<>();

        btAddNewAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewActDialog();
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter(KEY_PASS_RESULT_SCAN_CODE));

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Message msg = handler.obtainMessage(ACTIVITIES_TIME_STOP);
////        handler.sendMessageAtTime(msg, System.currentTimeMillis()+MAIN_ACTITITY_DELAY_TIME);
//        handler.sendMessageDelayed(msg, 200);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
        try {
            serviceManager.unbind();
        } catch (Throwable t) {
        }
    }

    private void showAddNewActDialog() {
        final Dialog dialog = new Dialog(ActivtiesManagementActivity.this);
        dialog.setTitle("Add new Activity");
        dialog.setContentView(R.layout.add_new_activity_dialog);

        dialog.show();

        Button btOk = (Button) dialog.findViewById(R.id.btAddActDone);
        Button btCancel = (Button) dialog.findViewById(R.id.btAddActCancel);

        final EditText edtID = (EditText) dialog.findViewById(R.id.edtAddActId);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtAddActName);
        final EditText edtType = (EditText) dialog.findViewById(R.id.edtAddActType);
        final EditText edtStartTime = (EditText) dialog.findViewById(R.id.edtAddActStartTime);
        final EditText edtEndTime = (EditText) dialog.findViewById(R.id.edtAddActEndtime);
        final EditText edtLocation = (EditText) dialog.findViewById(R.id.edtAddActLocation);
        final EditText edtShortIntro = (EditText) dialog.findViewById(R.id.edtAddActShortIntro);
        final EditText edtFullIntro = (EditText) dialog.findViewById(R.id.edtAddActFullIntro);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtID.getText().toString().matches("") || edtName.getText().toString().matches("") || edtType.getText().toString().matches("") || edtStartTime.getText().toString().matches("") ||
                        edtEndTime.getText().toString().matches("") || edtLocation.getText().toString().matches("") || edtShortIntro.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Some required feilds are empty", Toast.LENGTH_SHORT).show();
                } else {
                    TIGActivity activity = new TIGActivity(edtID.getText().toString(), edtName.getText().toString(),
                            edtType.getText().toString(), edtLocation.getText().toString(), edtStartTime.getText().toString(),
                            edtEndTime.getText().toString(), edtShortIntro.getText().toString(), edtFullIntro.getText().toString());
                    try {
                        serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_ADD_NEW_ACTIVITY,activity));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                try {
                    serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_REFRESH_LIST));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshList(){
        parActivitiesList.removeAllViews();
        AddlistItem addlistItem = new AddlistItem(ActivtiesManagementActivity.this);
        for (final TIGActivityShort t :
                activities) {
            View v = addlistItem.addActListItem(t.getName(),t.getShortIntro());
            parActivitiesList.addView(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_REFRESH_CHECKIN_LIST,t));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
//                    showOptionalDialog(t);
                }
            });
        }
    }

    private void showOptionalDialog(final TIGActivityShort act){
        final Dialog dialog = new Dialog(ActivtiesManagementActivity.this);
        dialog.setContentView(R.layout.activities_list_option_dialog);

        dialog.show();

        Button checkIn = (Button) dialog.findViewById(R.id.btActLisOptDiaCheckIn);
        Button show = (Button) dialog.findViewById(R.id.btActLisOptDiaShow);

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCameraAndScan(act);
                dialog.dismiss();
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showCheckInListDialog(act);
            }
        });
    }

    private void showCheckInListDialog(TIGActivityShort t){
        Dialog dialog = new Dialog(ActivtiesManagementActivity.this);
        dialog.setContentView(R.layout.checkin_list);
        dialog.show();

        TextView tvActName = (TextView) dialog.findViewById(R.id.tvCheckinListActivityName);
        LinearLayout par = (LinearLayout) dialog.findViewById(R.id.parCheckInListMemberView);

        par.removeAllViews();

        tvActName.setText(t.getName() + " checked in members");
        AddlistItem addlistItem = new AddlistItem(ActivtiesManagementActivity.this);

        for (TIGMemberShort m :
                members) {
            for (int i = 0; i < t.getMemberIDCheckin().size(); i++) {
                if(m.getID().matches(t.getMemberIDCheckin().get(i))){
                    View v = addlistItem.addMemberListItem(m.getLastName() + " " +m.getFistName(), m.getID());
                    par.addView(v);
                    break;
                }
            }
        }
    }

    private void checkCameraAndScan(TIGActivityShort t){
        if (ContextCompat.checkSelfPermission(ActivtiesManagementActivity.this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            scanContinuous(t);
        } else {
            ActivityCompat.requestPermissions(ActivtiesManagementActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    public void scanContinuous(TIGActivityShort t) {
        Intent intent = new Intent(this, ContinuousCaptureActivity.class);
        intent.putExtra(KEY_PASS_ACT_ID,t.getID());
//        intent.putExtra(KEY_PASS_ACT_MEMBER,t.getMemberIDCheckin());
        intent.putStringArrayListExtra(KEY_PASS_ACT_MEMBER,t.getMemberIDCheckin());
//        ArrayList<String> allMemberID = new ArrayList<>();
//        for (TIGMemberShort m :
//                members) {
//            allMemberID.add(m.getID());
//            Log.i("Activity Manager","sending ID: "+allMemberID.get(allMemberID.size()-1));
//        }
        intent.putExtra(KEY_PASS_ACT_MEMBER_ID_LIST,members);
//        intent.putStringArrayListExtra(KEY_PASS_ACT_MEMBER_ID_LIST,allMemberID);
//        startActivityForResult(intent,SCAN_RESULT_CODE);

//        intent.putExtra(KEY_PASS_SERVICE_MANAGER, serviceManager);
        startActivity(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode){
//            case SCAN_RESULT_CODE:
//
//                if(data!=null){
//                    ArrayList<String> newCheckIn = data.getStringArrayListExtra(KEY_REI_ACT_MEMBER);
//                    String actID = data.getStringExtra(KEY_REI_ACT_ID);
//                    try {
//                        serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_ADD_NEW_MEMBER_CHECKIN,1,0,actID));
//                        serviceManager.send(Message.obtain(null,ACTIVITIES_MANAGER_ADD_NEW_MEMBER_CHECKIN,newCheckIn));
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                    Toast.makeText(getApplicationContext(),"Checked in "+newCheckIn.size()+ " new members",Toast.LENGTH_SHORT).show();
//                }
//
//                break;
//        }
//    }
}
