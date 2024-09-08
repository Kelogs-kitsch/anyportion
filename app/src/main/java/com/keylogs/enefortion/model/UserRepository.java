package com.keylogs.enefortion.model;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UserRepository {
    private SQLiteDatabase database;
    private EnefortionDatabase dbHelper;

    public UserRepository(Context context) {
        dbHelper = new EnefortionDatabase(context);
        database = dbHelper.getWritableDatabase();
    }

    public User authenticateUser(String username, String password) {
        Cursor cursor = null;
        try {
            cursor = database.query(
                    "User",
                    new String[] { "username", "password" },
                    "username = ? AND password = ?",
                    new String[] { username, password },
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int usernameIndex = cursor.getColumnIndex("username");
                int passwordIndex = cursor.getColumnIndex("password");

                if (usernameIndex >= 0 && passwordIndex >= 0) {
                    String user = cursor.getString(usernameIndex);
                    String pass = cursor.getString(passwordIndex);

                    if (user != null && pass != null) {
                        User authenticatedUser = new User();
                        authenticatedUser.username = user;
                        authenticatedUser.password = pass;
                        return authenticatedUser;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
