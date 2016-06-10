package org.tig.android.tigadmintoolbox;


import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by nguye on 6/8/2016.
 */
public class SQLiteManager extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TIG_database.db";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabasesContracts.MemberData.SQL_CREATE_ENTRIES);
        Log.i("SQLManager","excecSQL "+ DatabasesContracts.MemberData.SQL_CREATE_ENTRIES);
        db.execSQL(DatabasesContracts.ActivityData.SQL_CREATE_ENTRIES);
        db.execSQL(DatabasesContracts.ActivityCheckInData.SQL_CREATE_ENTRIES);
//        db.execSQL("CREATE TABLE " + "Temp" + " (" +
//                "id" + " INTEGER PRIMARY KEY," +
//                "value" + " INTEGER" +
//        " )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabasesContracts.MemberData.SQL_DELETE_ENTRIES);
        db.execSQL(DatabasesContracts.ActivityData.SQL_DELETE_ENTRIES);
        db.execSQL(DatabasesContracts.ActivityCheckInData.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

}
