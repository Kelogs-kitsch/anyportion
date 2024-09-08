package com.keylogs.enefortion.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UserProgressRepository {
    private SQLiteDatabase database;
    private EnefortionDatabase dbHelper;

    public UserProgressRepository(Context context) {
        dbHelper = new EnefortionDatabase(context);
        database = dbHelper.getWritableDatabase();
    }

    public void upsertProgress(String username, int progress) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("progress", progress);

        // Use replace to insert or update the record
        database.replace("UserProgress", null, values);
        database.close(); // Close the database after operation
    }

    // Get current progress for a user
    public int getCurrentProgress(String username) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = database.query(
                    "UserProgress",
                    new String[]{"progress"},
                    "username = ?",
                    new String[]{username},
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int progressColumnIndex = cursor.getColumnIndex("progress");
                if (progressColumnIndex >= 0) {
                    return cursor.getInt(progressColumnIndex);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close(); // Close the database after operation
        }
        return 0; // Default progress if no record is found
    }
}
