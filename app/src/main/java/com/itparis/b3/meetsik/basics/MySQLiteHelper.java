package com.itparis.b3.meetsik.basics;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bouveti on 06/04/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_ANNONCE = "annonce";
    public static final String COLUMN_ID = "id";
    public static String COLUMN_NOM = "nom";
    public static String COLUMN_PRIX = "prix";
    public static String COLUMN_DATE = "date";
    public static String COLUMN_CATEGORIE = "categorie";
    public static String COLUMN_EMAIL = "email";

    private static final String DATABASE_NAME = "annonces.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_ANNONCE + "(" + COLUMN_ID
            + " integer primary key , "+ COLUMN_NOM
            + " integer not null, " + COLUMN_PRIX
            + " text not null, " + COLUMN_DATE
            + " text not null, " + COLUMN_CATEGORIE
            + " text not null, " + COLUMN_EMAIL
            + " integer);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANNONCE);
        onCreate(db);
    }

}
