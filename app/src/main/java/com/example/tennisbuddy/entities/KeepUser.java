package com.example.tennisbuddy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "keepUser")
public class KeepUser {
    @PrimaryKey
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int id) {
        this.userId = id;
    }
}
