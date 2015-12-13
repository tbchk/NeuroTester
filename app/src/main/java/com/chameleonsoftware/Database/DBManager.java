package com.chameleonsoftware.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chameleonsoftware.neurotester.MocaUser;

import java.util.ArrayList;
import java.util.List;

import finalw.robotica.sena.proyecto.qr.late.myapplication.Database.DBAlumnData;

/**
 * Created by camaleon2 on 17/10/15.
 */
public class DBManager extends SQLiteOpenHelper {

    /******* if debug is set true then it will show all Logcat message ***/
    public static final boolean DEBUG = true;

    /********** Logcat TAG ************/
    public static final String LOG_TAG = "DBManager";

    /************* Database Name ************/
    public static final String DATABASE_NAME = "DB_asist.db";

    /**** Database Version (Increase one if want to also upgrade your database) ****/
    public static final int DATABASE_VERSION = 1;// started at 1



    /** Table names */
    public static final String USER_TABLE = "user";


    /************ Table Fields ************/
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_NAME = "name";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_STUDY = "study";


    /**** Set all table with comma seperated like USER_TABLE,ABC_TABLE ******/
    private static final String[ ] ALL_TABLES = { USER_TABLE };

    /** Create table syntax */
    private static final String TABLE_CREATE = "create table "
            + USER_TABLE + "(" + KEY_ID
            + " integer primary key, "+ KEY_DATE
            + " text not null, " + KEY_NAME
            + " text not null, "+ KEY_LASTNAME
            + " text not null, " + KEY_EMAIL
            + " text not null);";

    public DBManager(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (DEBUG)
            Log.i(LOG_TAG, "new create");


        try {
            db.execSQL(TABLE_CREATE);


        } catch (Exception exception) {
            if (DEBUG)
                Log.i(LOG_TAG, "Exception onCreate() exception");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        if (DEBUG)
            Log.w(LOG_TAG, "Upgrading database from version " + oldVersion
                    + " to " + newVersion + "...");

        for (String table : ALL_TABLES) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }

        onCreate(db);
    }

    public void insertContact  (MocaUser uData)
    {
        if (DEBUG)
            Log.i(LOG_TAG, "new insertContact");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cVal = new ContentValues();

        int id = new Integer(uData.getId());
        String date = uData.getDate();
        String name = uData.getName();
        String lastname = uData.getName();
        String email = uData.getName();
        String gender = uData.getName();
        String phone = uData.getName();
        String study = uData.getName();

        cVal.put(KEY_ID,id);
        cVal.put(KEY_DATE, date);
        cVal.put(KEY_NAME, name);
        cVal.put(KEY_LASTNAME, lastname);
        cVal.put(KEY_EMAIL, email);
        cVal.put(KEY_GENDER, gender);
        cVal.put(KEY_PHONE, phone);
        cVal.put(KEY_STUDY, study);

        db.insert(USER_TABLE, null, cVal);
    }

    public void insertMedia(MocaUser uData){

    }

    /*CONSULTA DE LLEGADAS TARDE POR FECHA*/
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
    }

    /******************* NUNCA OLVIDAR CERRAR LA DB ****************************/
    public  void closeDB(){this.getWritableDatabase().close();}

}
