package com.example.tennisbuddy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "friends")
public class Friends {
    @PrimaryKey(autoGenerate = true)
    private int friendId;

    @ColumnInfo(name = "user1")
    private int user1Id;

    @ColumnInfo(name = "user2")
    private int user2Id;

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int id) {
        this.friendId = id;
    }

    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int id) {
        this.user1Id = id;
    }

    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int id) {
        this.user2Id = id;
    }
}
