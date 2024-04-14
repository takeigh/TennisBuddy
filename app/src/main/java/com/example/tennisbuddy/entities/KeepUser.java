package com.example.tennisbuddy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "keepUser")
public class KeepUser {
    @PrimaryKey
    private boolean yes;

    @ColumnInfo(name = "user")
    private int userId;

    public boolean getYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }
}
