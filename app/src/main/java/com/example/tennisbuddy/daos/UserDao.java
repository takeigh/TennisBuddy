package com.example.tennisbuddy.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tennisbuddy.entities.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getUsers();

    @Query("SELECT * FROM user WHERE userId = :userId LIMIT 1")
    User getUserByID(int userId);

    @Query("SELECT * FROM user WHERE email = :userEmail LIMIT 1")
    User getUserByEmail(String userEmail);

    @Insert
    void addUser(User user);
}
