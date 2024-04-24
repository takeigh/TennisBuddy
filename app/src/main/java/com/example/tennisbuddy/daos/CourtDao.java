package com.example.tennisbuddy.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tennisbuddy.entities.Court;

import java.util.List;

@Dao
public interface CourtDao {
    @Query("SELECT * FROM court")
    List<Court> getCourts();

    @Query("SELECT * FROM court WHERE courtId = :courtId LIMIT 1")
    Court getCourtById(int courtId);

    @Query("SELECT * FROM court WHERE court_name = :courtName LIMIT 1")
    Court getCourtByName(String courtName);

    @Insert
    void addCourt(Court court);

    @Delete
    void deleteCourt(Court court);
}
