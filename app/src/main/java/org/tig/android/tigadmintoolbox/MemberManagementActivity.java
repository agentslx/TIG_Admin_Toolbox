package org.tig.android.tigadmintoolbox;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nguye on 6/7/2016.
 */
public class MemberManagementActivity extends Activity {
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1010;

    public static final int MESSEGE_TO_SERVICE_ADD_NEW_MEMBER = 3030;
    public static final int MESSEGE_TO_SERVICE_REFESH_MEMBER_LIST = 3040;
    public static final int MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST = 102;

    private final int MEMBER_MANAGEMENT_TIME_STOP = 101601;

    private ServiceManager serviceManager;

    private LinearLayout listMemberParent;

    private ArrayList<TIGMemberShort> memberList;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST:
                    memberList.clear();
                    memberList.addAll((ArrayList<TIGMemberShort>) msg.obj);
                    refreshMemberList();
                    break;
                case MEMBER_MANAGEMENT_TIME_STOP:
                    try {
                        serviceManager.send(Message.obtain(null, MESSEGE_TO_SERVICE_REFESH_MEMBER_LIST));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_management_activity);

        serviceManager = new ServiceManager(MemberManagementActivity.this, TIGMainService.class, handler);

        serviceManager.start();

        memberList = new ArrayList<>();
        listMemberParent = (LinearLayout) findViewById(R.id.parentMemManagerMemberList);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message msg = handler.obtainMessage(MEMBER_MANAGEMENT_TIME_STOP);
//        handler.sendMessageAtTime(msg, System.currentTimeMillis()+MAIN_ACTITITY_DELAY_TIME);
        handler.sendMessageDelayed(msg, 200);
//        try {
//            serviceManager.send(Message.obtain(null, MESSEGE_TO_SERVICE_REFESH_MEMBER_LIST));
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        serviceManager.stop();
        try {
            serviceManager.unbind();
        } catch (Throwable t) {
        }
    }

    public void onClickBtScan(View v) {
        if (ContextCompat.checkSelfPermission(MemberManagementActivity.this,
                Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            scanContinuous();
        } else {
            ActivityCompat.requestPermissions(MemberManagementActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }

    public void scanContinuous() {
        Intent intent = new Intent(this, ContinuousCaptureActivity.class);
        startActivity(intent);
    }

    public void btMemberManagementAddNewMemberOnClick(View v) {
        // show a dialog to add new member info to database
        final Dialog dialog = new Dialog(MemberManagementActivity.this);
        dialog.setTitle("Add new member!");
        dialog.setContentView(R.layout.add_new_member_dialog);
        dialog.setCancelable(false);
        dialog.show();

        Button btDone = (Button) dialog.findViewById(R.id.btAddNewMemberDialogDone);
        Button btCancel = (Button) dialog.findViewById(R.id.btAddNewMemberDiaglogCancel);

        final EditText edtMemberID = (EditText) dialog.findViewById(R.id.edtAddNeMeMemberID);
        final EditText edtFirstName = (EditText) dialog.findViewById(R.id.edtAddNeMeFirstName);
        final EditText edtLastName = (EditText) dialog.findViewById(R.id.edtAddNeMeLastName);
        final EditText edtBirthday = (EditText) dialog.findViewById(R.id.edtAddNeMeBirthday);
        final EditText edtOrg = (EditText) dialog.findViewById(R.id.edtAddNeMeORG);
        final EditText edtMemberType = (EditText) dialog.findViewById(R.id.edtAddNeMeMemberType);
        final EditText edtMemberTel = (EditText) dialog.findViewById(R.id.edtAddNeMeTel);
        final EditText edtMemberEmail = (EditText) dialog.findViewById(R.id.edtAddNeMeEmail);
        //final EditText edtMemberAddress = (EditText) dialog.findViewById(R.id.edtAddNeMeAddress);

        final Button btMemberGender = (Button) dialog.findViewById(R.id.btAddNeMeMemberGender);

        btMemberGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btMemberGender.getText().toString().matches("MALE")) {
                    btMemberGender.setText("FEMALE");
                } else {
                    btMemberGender.setText("MALE");
                }

            }
        });

        btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check the data if all are valid
                if (edtMemberID.getText().toString().matches("") || edtFirstName.getText().toString().matches("") || edtLastName.
                        getText().toString().matches("") ||
                        edtBirthday.getText().toString().matches("") || edtOrg.getText().toString().matches("") || edtMemberType.getText().toString().matches("") || edtMemberTel.getText().toString().matches("") ||
                        edtMemberEmail.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Some required feilds are empty", Toast.LENGTH_SHORT).show();
                } else {
                    //create new member to send
                    boolean gender = TIGMember.MALE;
                    if (btMemberGender.getText().toString().matches("FEMALE"))
                        gender = TIGMember.FEMALE;
                    TIGMember member = new TIGMember(edtMemberID.getText().toString(), edtFirstName.getText().toString(), edtLastName.getText().toString(),
                            edtBirthday.getText().toString(), gender, edtOrg.getText().toString(), edtMemberType.getText().toString(),
                            edtMemberTel.getText().toString(), edtMemberEmail.getText().toString());
                    try {
                        serviceManager.send(Message.obtain(null, MESSEGE_TO_SERVICE_ADD_NEW_MEMBER, member));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    dialog.dismiss();
                }
                //put the info to database by send command to MainService

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
                    serviceManager.send(Message.obtain(null, MESSEGE_TO_SERVICE_REFESH_MEMBER_LIST));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void refreshMemberList() {
        listMemberParent.removeAllViews();
        AddlistItem addlistItem = new AddlistItem(MemberManagementActivity.this);
        for (TIGMemberShort m :
                memberList) {
            View v = addlistItem.addMemberListItem(m.getFistName() + m.getLastName(), m.getID());
            listMemberParent.addView(v);
        }
    }

    /*
    private void askForCameraPermission(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
*/

    /*
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    */
}
