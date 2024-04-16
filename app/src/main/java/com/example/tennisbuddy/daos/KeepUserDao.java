package com.example.tennisbuddy.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tennisbuddy.entities.KeepUser;
import com.example.tennisbuddy.entities.User;

@Dao
public interface KeepUserDao {
    @Query("SELECT * FROM keepUser LIMIT 1")
    KeepUser getKeepUser();

    @Insert
    void addUser(KeepUser user);

    @Query("DELETE FROM keepUser")
    void deleteUsers();
}
