package com.example.tennisbuddy.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Court {
    @PrimaryKey(autoGenerate = true)
    private int courtId;

    @ColumnInfo(name = "court_name")
    private String courtName;

    @ColumnInfo(name = "address")
    private String address;

    public int getCourtId() {
        return courtId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
