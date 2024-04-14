package com.example.tennisbuddy.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.tennisbuddy.entities.KeepUser;

@Dao
public interface KeepUserDao {
    @Query("SELECT * FROM keepUser LIMIT 1")
    KeepUser getKeepUser();
}
