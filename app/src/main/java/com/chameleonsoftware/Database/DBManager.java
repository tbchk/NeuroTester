package com.chameleonsoftware.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chameleonsoftware.neurotester.MocaUser;

/**
 * Created by camaleon2 on 17/10/15.
 */
public class DBManager extends SQLiteOpenHelper {

    /******* if debug is set true then it will show all Logcat message ***/
    private boolean DEBUG = true;

    /********** Logcat TAG ************/
    public static final String LOG_TAG = "DBManager";

    /************* Database Name ************/
    public static final String DATABASE_NAME = "DB_TESTER.db";

    /**** Database Version (Increase one if want to also upgrade your database) ****/
    public static final int DATABASE_VERSION = 1;// started at 1



    /** Table names */
    public static final String USER_TABLE = "user";
    public static final String MEDIA_TABLE = "media";

    /************ USER Table Fields ************/
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_NAME = "name";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_STUDY = "study";
    private static final String KEY_REVISED = "revised";


    /************ MEDIA Table Fields ************/
    private static final String KEY_MOCA1_IMAGE = "moca1image";
    private static final String KEY_MOCA2_IMAGE = "moca2image";
    private static final String KEY_MOCA3_IMAGE = "moca3image";
    private static final String KEY_MOCA1_TIME = "moca1time";
    private static final String KEY_MOCA2_TIME = "moca2time";
    private static final String KEY_MOCA3_TIME = "moca3time";

    /**** Set all table with comma seperated like USER_TABLE,ABC_TABLE ******/
    private static final String[ ] ALL_TABLES = { USER_TABLE , MEDIA_TABLE};

    /** Create table syntax */
    private static final String USER_TABLE_CREATE = "create table "
            + USER_TABLE + "(" + KEY_ID
            + " integer primary key, "+ KEY_DATE
            + " text not null, " + KEY_NAME
            + " text not null, "+ KEY_LASTNAME
            + " text not null, " + KEY_EMAIL
            + " text not null, " + KEY_REVISED
            + " integer not null);";

    private static final String MEDIA_TABLE_CREATE = "create table "
            + MEDIA_TABLE + "(" + KEY_ID
            + " integer primary key, "+ KEY_MOCA1_IMAGE
            + " blob not null, " + KEY_MOCA2_IMAGE
            + " blob not null, "+ KEY_MOCA3_IMAGE
            + " blob not null);";

    public DBManager(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        if (DEBUG) Log.i(LOG_TAG, "new create");

        try {
            db.execSQL(USER_TABLE_CREATE);
        } catch (Exception exception) {
            if (DEBUG)
                Log.i(LOG_TAG, "Exception onCreate() exception");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (DEBUG)
            Log.w(LOG_TAG, "Upgrading database from version " + oldVersion
                    + " to " + newVersion + "...");

        for (String table : ALL_TABLES) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }

        onCreate(db);
    }

    public void insertUserData  (MocaUser uData)
    {
        if (DEBUG) Log.i(LOG_TAG, "new insertContact");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();

        int id = Integer.valueOf(uData.getId());
        String date =       uData.getDate();
        String name =       uData.getName();
        String lastname =   uData.getLastname();
        String email =      uData.getEmail();
        String gender =     uData.getGender();
        String phone =      uData.getPhone();
        String study =      uData.getStudy();

        cVal.put(KEY_ID,id);
        cVal.put(KEY_DATE, date);
        cVal.put(KEY_NAME, name);
        cVal.put(KEY_LASTNAME, lastname);
        cVal.put(KEY_EMAIL, email);
        cVal.put(KEY_GENDER, gender);
        cVal.put(KEY_PHONE, phone);
        cVal.put(KEY_STUDY, study);
        cVal.put(KEY_REVISED, 0); //No Revisado

        db.insert(USER_TABLE, null, cVal);
    }

    public void insertMedia(MocaUser uData){

        if (DEBUG) Log.i(LOG_TAG, "new insertContact");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();

        int id = Integer.valueOf(uData.getId());
        byte[] moca1Bitmap = uData.getBitmapByteArray(uData.getMoca1Bitmap());
        byte[] moca2Bitmap = uData.getBitmapByteArray(uData.getMoca2Bitmap());
        byte[] moca3Bitmap = uData.getBitmapByteArray(uData.getMoca3Bitmap());

        int moca1Time = uData.getMoca1Time();
        int moca2Time = uData.getMoca2Time();
        int moca3Time = uData.getMoca3Time();

        cVal.put(KEY_ID,id );
        cVal.put(KEY_MOCA1_TIME,moca1Time);
        cVal.put(KEY_MOCA2_TIME,moca2Time);
        cVal.put(KEY_MOCA3_TIME,moca3Time);
        cVal.put(KEY_MOCA1_IMAGE,moca1Bitmap);
        cVal.put(KEY_MOCA2_IMAGE,moca2Bitmap);
        cVal.put(KEY_MOCA3_IMAGE,moca3Bitmap);

        db.insert(MEDIA_TABLE,null,cVal);
    }

    /*
    public List<MocaUser> getAllUserbyDate(String R_date) {

        SQLiteDatabase db = this.getWritableDatabase();
        List<DBAlumnData> alumnList = new ArrayList<DBAlumnData>();
        // Select All Query

        String selectQuery = "SELECT * FROM " + USER_TABLE +" WHERE " +KEY_DATE
                + "='"+R_date+"'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (DEBUG) {
            Log.i(LOG_TAG, "Nueva Consulta");
            Log.i(LOG_TAG, "KEY: " + R_date);
            Log.i(LOG_TAG, "QUERY: " + selectQuery);
            Log.i(LOG_TAG, "#MATCHS: " + cursor.getCount());
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DBAlumnData data = new DBAlumnData();
                data.setDate(cursor.getString(0));
                data.setName(cursor.getString(1));
                data.setGrade(cursor.getString(2));
                data.setYear(cursor.getString(3));

                // Adding contact to list
                alumnList.add(data);
            } while (cursor.moveToNext());
        }

        // return user list
        return alumnList;
    }*/

    /******************* NUNCA OLVIDAR CERRAR LA DB ****************************/
    public  void closeDB(){this.getWritableDatabase().close();}

    //Variable MANAGEMENT
    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }
    public boolean getDEBUG(){
        return this.DEBUG;
    }
}
