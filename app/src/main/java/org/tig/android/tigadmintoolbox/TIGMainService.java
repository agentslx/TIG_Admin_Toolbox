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

    public static final int SERVICE_IS_READY = 99954;

    private SQLiteManager sqLiteManager;

    private SQLiteDatabase dbReadable, dbWritable;


    @Override
    public void onCreateService() {
        sqLiteManager = new SQLiteManager(TIGMainService.this);
        dbReadable = sqLiteManager.getReadableDatabase();
        dbWritable = sqLiteManager.getReadableDatabase();
    }

    @Override
    public void onStartService() {

    }

    @Override
    public void onStopService() {

    }

    @Override
    public void onServiceReady() {
        send(Message.obtain(null, SERVICE_IS_READY));
    }

    @Override
    public void onReceiveMessage(Message msg) {
        switch (msg.what) {
            case MemberManagementActivity.MESSEGE_TO_SERVICE_ADD_NEW_MEMBER:
                TIGMember member = (TIGMember) msg.obj;
                addNewMember(member);
                Log.i("Main Service", "Add new member " + member.getLastName() + member.getFistName());
                break;

            case MemberManagementActivity.MESSEGE_TO_SERVICE_REFESH_MEMBER_LIST:
                send(Message.obtain(null, MemberManagementActivity.MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST, readAllMemberShort()));
                break;

            case ActivtiesManagementActivity.ACTIVITIES_MANAGER_ADD_NEW_ACTIVITY:
                addNewActivity((TIGActivity) msg.obj);
                break;
            case ActivtiesManagementActivity.ACTIVITIES_MANAGER_REFRESH_LIST:
                send(Message.obtain(null, ActivtiesManagementActivity.ACTIVITIES_MANAGER_FROM_SERVICE_ACTIVITY_LIST, readAllActivityShort()));
                break;
            case ActivtiesManagementActivity.ACTIVITIES_MANAGER_REFRESH_MEMBER_LIST:
                send(Message.obtain(null, ActivtiesManagementActivity.ACTIVITIES_MANAGER_FROM_SERVICE_MEMBERLIST, readAllMemberShort()));
                break;
            case ActivtiesManagementActivity.ACTIVITIES_MANAGER_REFRESH_CHECKIN_LIST:
                //send(Message.obtain(null, ActivtiesManagementActivity.ACTIVITIES_MANAGER_FROM_SERVICE_ACTIVITY_LIST, readAllActivityShort()));
                readActivityCheckin((TIGActivityShort) msg.obj);
                break;
            case ActivtiesManagementActivity.ACTIVITIES_MANAGER_ADD_NEW_MEMBER_CHECKIN:
                ArrayList<String> a = (ArrayList<String>) msg.obj;
                addArrCheckInMember(a);
                Log.i("Main Service", "Adding " + a.get(1) + " to " + a.get(0));

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

    private void addNewActivity(TIGActivity activity) {
        ContentValues values = new ContentValues();
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_ID, activity.getID());
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_NAME, activity.getName());
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_TYPE, activity.getType());
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_START_TIME, activity.getStartTime());
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_END_TIME, activity.getEndTime());
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_LOCATION, activity.getLocation());
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_SHORT_INTRO, activity.getShortIntro());
        values.put(DatabasesContracts.ActivityData.COLUMN_NAME_FULL_INTRO, activity.getFullIntro());

        addNewRow(DatabasesContracts.ActivityData.TABLE_NAME, values);

        //send(Message.obtain(null,MemberManagementActivity.MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST,readAllMemberShort()));
    }

    private void addArrCheckInMember(ArrayList<String> info) {
        addNewCheckinMember(info.get(0), info.get(1));
    }

    private void addNewCheckinMember(String activityID, String memberID) {
        ContentValues values = new ContentValues();
        values.put(DatabasesContracts.ActivityCheckInData.COLUMN_NAME_ACTIVITY_ID, activityID);
        values.put(DatabasesContracts.ActivityCheckInData.COLUMN_NAME_MEMBER_ID, memberID);

        addNewRow(DatabasesContracts.ActivityCheckInData.TABLE_NAME, values);

        //send(Message.obtain(null,MemberManagementActivity.MESSAGE_FROM_SERVICE_REFRESH_MEMBER_LIST,readAllMemberShort()));
    }

    private void addNewRow(String tableName, ContentValues contentValues) {
        // Gets the data repository in write mode


// Insert the new row, returning the primary key value of the new row
        dbWritable.insert(tableName, null, contentValues);
    }

//    private ArrayList<String> readAllMemberID() {
//        ArrayList<String> allID = new ArrayList<>();
//        Cursor c = readDataFromDatabase(DatabasesContracts.MemberData.TABLE_NAME, new String[]{DatabasesContracts.MemberData.COLUMN_NAME_ID}, null, null, null, null, null, null);
//        c.moveToFirst();
//        while (!c.isAfterLast()) {
//            allID.add(c.getString(c.getColumnIndexOrThrow(DatabasesContracts.MemberData.COLUMN_NAME_ID)));
//            c.moveToNext();
//        }
//
//        c.close();
//        return allID;
//    }

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

    private ArrayList<TIGActivityShort> readAllActivityShort() {
        ArrayList<TIGActivityShort> allAct = new ArrayList<>();
        Cursor c = readDataFromDatabase(DatabasesContracts.ActivityData.TABLE_NAME, new String[]{DatabasesContracts.ActivityData.COLUMN_NAME_ID,
                DatabasesContracts.ActivityData.COLUMN_NAME_NAME,
                DatabasesContracts.ActivityData.COLUMN_NAME_TYPE,
                DatabasesContracts.ActivityData.COLUMN_NAME_START_TIME,
                DatabasesContracts.ActivityData.COLUMN_NAME_END_TIME,
                DatabasesContracts.ActivityData.COLUMN_NAME_LOCATION,
                DatabasesContracts.ActivityData.COLUMN_NAME_SHORT_INTRO}, null, null, null, null, DatabasesContracts.ActivityData.COLUMN_NAME_ID, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            allAct.add(new TIGActivityShort(c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityData.COLUMN_NAME_ID)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityData.COLUMN_NAME_NAME)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityData.COLUMN_NAME_TYPE)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityData.COLUMN_NAME_LOCATION)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityData.COLUMN_NAME_START_TIME)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityData.COLUMN_NAME_END_TIME)),
                    c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityData.COLUMN_NAME_SHORT_INTRO))));
            c.moveToNext();
        }

        c.close();
        return allAct;
    }

    private void readActivityCheckin(TIGActivityShort activity) {
        Cursor c = readDataFromDatabase(DatabasesContracts.ActivityCheckInData.TABLE_NAME, new String[]{DatabasesContracts.ActivityCheckInData.COLUMN_NAME_MEMBER_ID},
                DatabasesContracts.ActivityCheckInData.COLUMN_NAME_ACTIVITY_ID + " = '" + activity.getID() + "'", null, null, null, null, null);
        activity.getMemberIDCheckin().clear();
        c.moveToFirst();
        while (!c.isAfterLast()) {
            activity.getMemberIDCheckin().add(c.getString(c.getColumnIndexOrThrow(DatabasesContracts.ActivityCheckInData.COLUMN_NAME_MEMBER_ID)));
            c.moveToNext();
        }
        send(Message.obtain(null,ActivtiesManagementActivity.ACTIVITIES_MANAGER_FROM_SERVICE_CHECKIN_LIST,activity));
        c.close();
    }

    private Cursor readDataFromDatabase(String tableName, String[] projection, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        return dbReadable.query(
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
