package com.keylogs.enefortion.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnefortionDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "enefortion_database.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statements to create tables
    private static final String SQL_CREATE_USER_TABLE =
            "CREATE TABLE User (" +
                    "username TEXT PRIMARY KEY NOT NULL, " +
                    "password TEXT NOT NULL)";

    private static final String SQL_CREATE_USER_SCORE_TABLE =
            "CREATE TABLE UserScore (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "username TEXT NOT NULL, " +
                    "score INTEGER NOT NULL)";

    private static final String SQL_CREATE_USER_PROGRESS_TABLE =
            "CREATE TABLE UserProgress (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "username TEXT NOT NULL, " +
                    "progress INTEGER NOT NULL)";

    // Constructor
    public EnefortionDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(SQL_CREATE_USER_TABLE);
        db.execSQL(SQL_CREATE_USER_SCORE_TABLE);
        db.execSQL(SQL_CREATE_USER_PROGRESS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrade logic here
        if (oldVersion < 2) {
            // Example of how to handle upgrades
            db.execSQL(SQL_CREATE_USER_PROGRESS_TABLE); // Add UserProgress table in version 2
        }
        // You can add further upgrade logic for future versions here
    }
}
