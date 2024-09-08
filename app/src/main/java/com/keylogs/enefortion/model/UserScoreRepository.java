package com.keylogs.enefortion.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserScoreRepository {
    private SQLiteDatabase database;

    public UserScoreRepository(Context context) {
        EnefortionDatabase dbHelper = new EnefortionDatabase(context);
        database = dbHelper.getWritableDatabase();
    }

    // Insert a score into the UserScore table
    public void insertScore(UserScore userScore) {
        database.execSQL("INSERT INTO UserScore (username, score) VALUES (?, ?)",
                new Object[]{userScore.username, userScore.score});
    }

    // Retrieve top scores
    public List<UserScore> getTopScores() {
        List<UserScore> scores = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.query(
                    "UserScore",
                    new String[]{"id", "username", "score"},
                    null,
                    null,
                    null,
                    null,
                    "score DESC",
                    "10" // Limit to top 10 scores
            );

            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int usernameIndex = cursor.getColumnIndex("username");
                int scoreIndex = cursor.getColumnIndex("score");

                if (idIndex >= 0 && usernameIndex >= 0 && scoreIndex >= 0) {
                    do {
                        UserScore userScore = new UserScore();
                        userScore.id = cursor.getInt(idIndex);
                        userScore.username = cursor.getString(usernameIndex);
                        userScore.score = cursor.getInt(scoreIndex);
                        scores.add(userScore);
                    } while (cursor.moveToNext());
                } else {
                    // Handle missing column names here
                    throw new IllegalStateException("Column names do not match the expected schema");
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return scores;
    }
}
