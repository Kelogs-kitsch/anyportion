package com.keylogs.enefortion.model;


public class UserScore {
    public int id;
    public String username;
    public int score;

    // Default constructor
    public UserScore() {}

    // Parameterized constructor
    public UserScore(int id, String username, int score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }

    // Optionally, getters and setters
}

