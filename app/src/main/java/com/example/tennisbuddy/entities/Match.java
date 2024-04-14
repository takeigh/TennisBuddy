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
            @ForeignKey(entity = Court.class,
                        parentColumns = "userId",
                        childColumns = "player3",
                        onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = Court.class,
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

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "hour")
    private int hour;

    @ColumnInfo(name = "minute")
    private int minute;

    public int getMatchId() {
        return matchId;
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
}
