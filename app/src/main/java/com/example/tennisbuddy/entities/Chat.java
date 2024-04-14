package com.example.tennisbuddy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "chat",
        foreignKeys = {
            @ForeignKey(
                    entity = User.class,
                    parentColumns = "userId",
                    childColumns = "sender",
                    onDelete = ForeignKey.CASCADE
            ),
            @ForeignKey(
                    entity = User.class,
                    parentColumns = "userId",
                    childColumns = "receiver",
                    onDelete = ForeignKey.CASCADE
            )
        })
public class Chat {
    @PrimaryKey(autoGenerate = true)
    private int messageId;

    @ColumnInfo(name = "sender")
    private int senderId;

    @ColumnInfo(name = "receiver")
    private int receiverId;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "message")
    private String message;

    public int getMessageId() {
        return messageId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int id) {
        this.senderId = id;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int id) {
        this.receiverId = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
