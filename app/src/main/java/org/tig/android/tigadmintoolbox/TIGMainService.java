package org.tig.android.tigadmintoolbox;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by nguye on 6/8/2016.
 */
public class TIGMainService extends AbstractService {

    private SQLiteManager sqLiteManager;

    @Override
    public void onCreateService() {
        sqLiteManager = new SQLiteManager(TIGMainService.this);
    }

    @Override
    public void onStartService() {

    }

    @Override
    public void onStopService() {

    }

    @Override
    public void onReceiveMessage(Message msg) {
        switch (msg.what) {
            case MemberManagementActivity.MESSEGE_TO_SERVICE_ADD_NEW_MEMBER:
                TIGMember member = (TIGMember) msg.obj;
                addNewMember(member);
                Log.i("Main Service","Add new member "+member.getLastName() + member.getFistName());
                break;

            case MemberManagementActivity.MESSEGE_TO_SERVICE_REFESH_MEMBER_LIST:
//                ArrayList<TIGMemberShort> list = readAllMemberShort();
//                Toast.makeText(TIGMainService.this,list.get(0).getFistName()+list.get(0).getID(),Toast.LENGTH_SHORT).show();
//                Message ms = new Message();
//                ms.obj = list;
//                ms.what = MemberManagementActivity.MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST;
//                send(ms);
                send(Message.obtain(null,MemberManagementActivity.MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST,readAllMemberShort()));
                break;
        }
    }

    private void addNewMember(TIGMember member) {
        ContentValues values = new ContentValues();
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_ID, member.getID());
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_FIRST_NAME, member.getFistName());
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_LAST_NAME, member.getLastName());
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_BIRTHDAY, member.getBirthDay());
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_GENDER, member.isGender() ? 1 : 0);
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_ORG, member.getOrg());
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_MEMBER_TYPE, member.getType());
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_TEL, member.getTel());
        values.put(DatabasesContracts.MemberData.COLUMN_NAME_EMAIL, member.getEmail());

        addNewRow(DatabasesContracts.MemberData.TABLE_NAME, values);

        //send(Message.obtain(null,MemberManagementActivity.MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST,readAllMemberShort()));
    }

    private void addNewRow(String tableName, ContentValues contentValues) {
        // Gets the data repository in write mode
        SQLiteDatabase db = sqLiteManager.getWritableDatabase();

// Insert the new row, returning the primary key value of the new row
        db.insert(tableName, null, contentValues);
    }

    private ArrayList<String> readAllMemberID() {
        ArrayList<String> allID = new ArrayList<>();
        Cursor c = readDataFromDatabase(DatabasesContracts.MemberData.TABLE_NAME, new String[]{DatabasesContracts.MemberData.COLUMN_NAME_ID}, null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            allID.add(c.getString(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_ID)));
            c.moveToNext();
        }

        c.close();
        return allID;
    }

    private ArrayList<TIGMemberShort> readAllMemberShort() {
        ArrayList<TIGMemberShort> allMem = new ArrayList<>();
        Cursor c = readDataFromDatabase(DatabasesContracts.MemberData.TABLE_NAME, new String[]{DatabasesContracts.MemberData.COLUMN_NAME_ID,
                DatabasesContracts.MemberData.COLUMN_NAME_FIRST_NAME,
                DatabasesContracts.MemberData.COLUMN_NAME_LAST_NAME,
                DatabasesContracts.MemberData.COLUMN_NAME_GENDER,
                DatabasesContracts.MemberData.COLUMN_NAME_ORG,
                DatabasesContracts.MemberData.COLUMN_NAME_MEMBER_TYPE}, null, null, null, null, DatabasesContracts.MemberData.COLUMN_NAME_FIRST_NAME, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            allMem.add(new TIGMemberShort(c.getString(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_ID)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_FIRST_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_LAST_NAME)),
                    c.getInt(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_GENDER)) == 1,
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_ORG)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_MEMBER_TYPE))));
            c.moveToNext();
        }

        c.close();
        return allMem;
    }

    private Cursor readDataFromDatabase(String tableName, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase db = sqLiteManager.getReadableDatabase();

        return db.query(
                tableName, // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                groupBy,                                     // don't group the rows
                having,                                     // don't filter by row groups
                orderBy,
                limit// The sort order
        );
    }

}
