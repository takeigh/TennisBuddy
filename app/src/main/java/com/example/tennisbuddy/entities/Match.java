package com.example.tennisbuddy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "match",
        foreignKeys = {
            @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "host_id",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = Court.class,
                        parentColumns = "courtId",
                        childColumns = "court_id",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "player2",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "player3",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "player4",
                        onDelete = ForeignKey.CASCADE),
        })
public class Match {
    @PrimaryKey(autoGenerate = true)
    private int matchId;

    @ColumnInfo(name = "host_id")
    private int hostId;

    @ColumnInfo(name = "court_id")
    private int courtId;

    @ColumnInfo(name = "player2")
    private int player2Id;

    @ColumnInfo(name = "player3")
    private int player3Id;

    @ColumnInfo(name = "player4")
    private int player4Id;

    @ColumnInfo(name = "month")
    private int month;

    @ColumnInfo(name = "day")
    private int day;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "hour")
    private int hour;

    @ColumnInfo(name = "minute")
    private int minute;

    @ColumnInfo(name = "experience")
    private String experienceLevel;

    @ColumnInfo(name = "type")
    private String matchType;

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int id) {
        this.matchId = id;
    }

    public int getHostId() {
        return hostId;
    }

    public void setHostId(int id) {
        this.hostId = id;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int id) {
        this.courtId = id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(int id) {
        this.player2Id = id;
    }

    public int getPlayer3Id() {
        return player3Id;
    }

    public void setPlayer3Id(int id) {
        this.player3Id = id;
    }

    public int getPlayer4Id() {
        return player4Id;
    }

    public void setPlayer4Id(int id) {
        this.player4Id = id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }
}
