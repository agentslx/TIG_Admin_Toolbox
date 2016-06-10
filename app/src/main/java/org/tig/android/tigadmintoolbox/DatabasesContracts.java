package org.tig.android.tigadmintoolbox;

import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by nguye on 6/8/2016.
 */
public class DatabasesContracts {


    public DatabasesContracts() {
    }

    //contents for Member table
    public static abstract class MemberData implements BaseColumns {
        public static final String TABLE_NAME = "MemberData";
        public static final String COLUMN_NAME_ID = "MemberID";
        public static final String COLUMN_NAME_MEMBER_TYPE = "MemberType";
        public static final String COLUMN_NAME_FIRST_NAME = "FirstName";
        public static final String COLUMN_NAME_LAST_NAME = "LastName";
        public static final String COLUMN_NAME_BIRTHDAY = "Birthday";
        public static final String COLUMN_NAME_GENDER = "Gender";
        public static final String COLUMN_NAME_ORG = "Organization";
        public static final String COLUMN_NAME_TEL = "Tel";
        public static final String COLUMN_NAME_EMAIL = "Email";
        //public static final String COLUMN_NAME_ADDRESS = "Address";
        //public static final String COLUMN_NAME_SCHOOL = "School";
        // public static final String COLUMN_NAME_STUDENT_ID = "Stu ID";
        //public static final String COLUMN_NAME_CV = "CV";
        //public static final String COLUMN_NAME_OTHER = "Other";

        public static final String COLUMN_TYPE_ID = " TEXT PRIMARY KEY";
        public static final String COLUMN_TYPE_MEMBER_TYPE = " TEXT";
        public static final String COLUMN_TYPE_FIRST_NAME = " TEXT";
        public static final String COLUMN_TYPE_LAST_NAME = " TEXT";
        public static final String COLUMN_TYPE_BIRTHDAY = " TEXT";
        public static final String COLUMN_TYPE_GENDER = " INTEGER";
        public static final String COLUMN_TYPE_ORG = " TEXT";
        public static final String COLUMN_TYPE_TEL = " TEXT";
        public static final String COLUMN_TYPE_EMAIL = " TEXT";
        //public static final String COLUMN_TYPE_ADDRESS = " TEXT NOT NULL ";
        // public static final String COLUMN_TYPE_SCHOOL = " TEXT";
        //  public static final String COLUMN_TYPE_STUDENT_ID = " TEXT";
        // public static final String COLUMN_TYPE_CV = " BLOB";
        // public static final String COLUMN_TYPE_OTHER = " TEXT";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + COLUMN_TYPE_ID + "," +
                        COLUMN_NAME_MEMBER_TYPE + COLUMN_TYPE_MEMBER_TYPE + ", " +
                        COLUMN_NAME_FIRST_NAME + COLUMN_TYPE_FIRST_NAME + ", " +
                        COLUMN_NAME_LAST_NAME + COLUMN_TYPE_LAST_NAME + ", " +
                        COLUMN_NAME_BIRTHDAY + COLUMN_TYPE_BIRTHDAY + ", " +
                        COLUMN_NAME_GENDER + COLUMN_TYPE_GENDER + ", " +
                        COLUMN_NAME_ORG + COLUMN_TYPE_ORG + ", " +
                        COLUMN_NAME_TEL + COLUMN_TYPE_TEL + ", " +
                        COLUMN_NAME_EMAIL + COLUMN_TYPE_EMAIL +
                        // COLUMN_NAME_ADDRESS + COLUMN_TYPE_ADDRESS +
                        //COLUMN_NAME_SCHOOL + COLUMN_TYPE_SCHOOL + "," +
                        //COLUMN_NAME_STUDENT_ID + COLUMN_TYPE_STUDENT_ID + "," +
                        // COLUMN_NAME_OTHER + COLUMN_TYPE_OTHER +
                        " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
        /*
        CREATE TABLE "Member Data" ("Member_ID" TEXT PRIMARY KEY
        NOT NULL , "First Name" TEXT NOT NULL , "Last Name" TEXT NOT NULL ,
                "Birthday" DATETIME NOT NULL , "Organization" TEXT NOT NULL ,
                "Tel" TEXT NOT NULL , "Email" TEXT NOT NULL , "Address" TEXT NOT NULL ,
                "School" TEXT, "MSSV" TEXT, "CV" BLOB, "Other" TEXT)
                */
    }

    public static abstract class ActivityData implements BaseColumns {
        public static final String TABLE_NAME = "ActivityData";
        public static final String COLUMN_NAME_ID = "ID";
        public static final String COLUMN_NAME_TYPE = "Type";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_LOCATION = "Location";
        public static final String COLUMN_NAME_START_TIME = "StartTime";
        public static final String COLUMN_NAME_END_TIME = "EndTime";
        public static final String COLUMN_NAME_SHORT_INTRO = "ShortIntro";
        public static final String COLUMN_NAME_FULL_INTRO = "FullIntro";
        //public static final String COLUMN_NAME_ADDRESS = "Address";
        //public static final String COLUMN_NAME_SCHOOL = "School";
        // public static final String COLUMN_NAME_STUDENT_ID = "Stu ID";
        //public static final String COLUMN_NAME_CV = "CV";
        //public static final String COLUMN_NAME_OTHER = "Other";
        public static final String COLUMN_TYPE_ID = " TEXT PRIMARY KEY";
        public static final String COLUMN_TYPE_TYPE = " TEXT";
        public static final String COLUMN_TYPE_NAME = " TEXT";
        public static final String COLUMN_TYPE_LOCATION = " TEXT";
        public static final String COLUMN_TYPE_START_TIME = " TEXT";
        public static final String COLUMN_TYPE_END_TIME = " TEXT";
        public static final String COLUMN_TYPE_SHORT_INTRO = " TEXT";
        public static final String COLUMN_TYPE_FULL_INTRO = " TEXT";


        //public static final String COLUMN_TYPE_ADDRESS = " TEXT NOT NULL ";
        // public static final String COLUMN_TYPE_SCHOOL = " TEXT";
        //  public static final String COLUMN_TYPE_STUDENT_ID = " TEXT";
        // public static final String COLUMN_TYPE_CV = " BLOB";
        // public static final String COLUMN_TYPE_OTHER = " TEXT";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + COLUMN_TYPE_ID + "," +
                        COLUMN_NAME_NAME + COLUMN_TYPE_NAME + ", " +
                        COLUMN_NAME_TYPE + COLUMN_TYPE_NAME + ", " +
                        COLUMN_NAME_LOCATION + COLUMN_TYPE_LOCATION + ", " +
                        COLUMN_NAME_START_TIME + COLUMN_TYPE_START_TIME + ", " +
                        COLUMN_NAME_END_TIME + COLUMN_TYPE_END_TIME + ", " +
                        COLUMN_NAME_SHORT_INTRO + COLUMN_TYPE_SHORT_INTRO + ", " +
                        COLUMN_NAME_FULL_INTRO + COLUMN_TYPE_FULL_INTRO  +
                        //COLUMN_NAME_EMAIL + COLUMN_TYPE_EMAIL +
                        // COLUMN_NAME_ADDRESS + COLUMN_TYPE_ADDRESS +
                        //COLUMN_NAME_SCHOOL + COLUMN_TYPE_SCHOOL + "," +
                        //COLUMN_NAME_STUDENT_ID + COLUMN_TYPE_STUDENT_ID + "," +
                        // COLUMN_NAME_OTHER + COLUMN_TYPE_OTHER +
                        " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
        /*
        CREATE TABLE "Member Data" ("Member_ID" TEXT PRIMARY KEY
        NOT NULL , "First Name" TEXT NOT NULL , "Last Name" TEXT NOT NULL ,
                "Birthday" DATETIME NOT NULL , "Organization" TEXT NOT NULL ,
                "Tel" TEXT NOT NULL , "Email" TEXT NOT NULL , "Address" TEXT NOT NULL ,
                "School" TEXT, "MSSV" TEXT, "CV" BLOB, "Other" TEXT)
                */
    }

    public static abstract class ActivityCheckInData implements BaseColumns {
        public static final String TABLE_NAME = "ActivityCheckInData";
        public static final String COLUMN_NAME_ACTIVITY_ID = "ActivityID";
        public static final String COLUMN_NAME_MEMBER_ID = "MemberID";

        public static final String COLUMN_TYPE_ACTIVITY_ID = " TEXT";
        public static final String COLUMN_TYPE_MEMBER_ID = " TEXT";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ACTIVITY_ID + COLUMN_TYPE_ACTIVITY_ID + "," +
                        COLUMN_NAME_MEMBER_ID + COLUMN_TYPE_MEMBER_ID +
                        " )";
        public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
        /*
        CREATE TABLE "Member Data" ("Member_ID" TEXT PRIMARY KEY
        NOT NULL , "First Name" TEXT NOT NULL , "Last Name" TEXT NOT NULL ,
                "Birthday" DATETIME NOT NULL , "Organization" TEXT NOT NULL ,
                "Tel" TEXT NOT NULL , "Email" TEXT NOT NULL , "Address" TEXT NOT NULL ,
                "School" TEXT, "MSSV" TEXT, "CV" BLOB, "Other" TEXT)
                */
    }
}
