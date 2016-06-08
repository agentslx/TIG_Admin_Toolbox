package org.tig.android.tigadmintoolbox;

import android.provider.BaseColumns;

/**
 * Created by nguye on 6/8/2016.
 */
public class DatabasesContracts {



    public DatabasesContracts(){}

    //contents for Member table
    public static abstract class MemberData implements BaseColumns {
        public static final String TABLE_NAME = "Member Data";
        public static final String COLUMN_NAME_ID = "Member ID";
        public static final String COLUMN_NAME_MEMBER_TYPE = "Member Type";
        public static final String COLUMN_NAME_FIRST_NAME = "First Name";
        public static final String COLUMN_NAME_LAST_NAME = "Last Name";
        public static final String COLUMN_NAME_BIRTHDAY = "Birthday";
        public static final String COLUMN_NAME_ORG = "Organization";
        public static final String COLUMN_NAME_TEL = "Tel";
        public static final String COLUMN_NAME_EMAIL = "Email";
        public static final String COLUMN_NAME_ADD = "Address";
        //public static final String COLUMN_NAME_SCHOOL = "School";
       // public static final String COLUMN_NAME_STUDENT_ID = "Stu ID";
        //public static final String COLUMN_NAME_CV = "CV";
        //public static final String COLUMN_NAME_OTHER = "Other";

        public static final String COLUMN_TYPE_ID = " TEXT PRIMARY KEY NOT NULL ";
        public static final String COLUMN_TYPE_MEMBER_TYPE = " TEXT NOT NULL";
        public static final String COLUMN_TYPE_FIRST_NAME = " TEXT NOT NULL ";
        public static final String COLUMN_TYPE_LAST_NAME = " TEXT NOT NULL ";
        public static final String COLUMN_TYPE_BIRTHDAY = "DATETIME NOT NULL ";
        public static final String COLUMN_TYPE_ORG = " TEXT NOT NULL ";
        public static final String COLUMN_TYPE_TEL = " TEXT NOT NULL ";
        public static final String COLUMN_TYPE_EMAIL = " TEXT NOT NULL ";
        public static final String COLUMN_TYPE_ADD = " TEXT NOT NULL ";
       // public static final String COLUMN_TYPE_SCHOOL = " TEXT";
      //  public static final String COLUMN_TYPE_STUDENT_ID = " TEXT";
       // public static final String COLUMN_TYPE_CV = " BLOB";
       // public static final String COLUMN_TYPE_OTHER = " TEXT";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_NAME_ID + COLUMN_TYPE_ID + "," +
                        COLUMN_NAME_MEMBER_TYPE + COLUMN_TYPE_MEMBER_TYPE + "," +
                        COLUMN_NAME_FIRST_NAME + COLUMN_TYPE_FIRST_NAME + "," +
                        COLUMN_NAME_LAST_NAME + COLUMN_TYPE_LAST_NAME + "," +
                        COLUMN_NAME_BIRTHDAY + COLUMN_TYPE_BIRTHDAY + "," +
                        COLUMN_NAME_ORG + COLUMN_TYPE_ORG + "," +
                        COLUMN_NAME_TEL + COLUMN_TYPE_TEL + "," +
                        COLUMN_NAME_EMAIL + COLUMN_TYPE_EMAIL + "," +
                        COLUMN_NAME_ADD + COLUMN_TYPE_ADD + "," +
                        //COLUMN_NAME_SCHOOL + COLUMN_TYPE_SCHOOL + "," +
                        //COLUMN_NAME_STUDENT_ID + COLUMN_TYPE_STUDENT_ID + "," +
                       // COLUMN_NAME_OTHER + COLUMN_TYPE_OTHER +
                " )";

        /*
        CREATE TABLE "Member Data" ("Member_ID" TEXT PRIMARY KEY
        NOT NULL , "First Name" TEXT NOT NULL , "Last Name" TEXT NOT NULL ,
                "Birthday" DATETIME NOT NULL , "Organization" TEXT NOT NULL ,
                "Tel" TEXT NOT NULL , "Email" TEXT NOT NULL , "Address" TEXT NOT NULL ,
                "School" TEXT, "MSSV" TEXT, "CV" BLOB, "Other" TEXT)
                */
    }
}
