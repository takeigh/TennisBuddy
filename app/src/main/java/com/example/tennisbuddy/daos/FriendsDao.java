package com.example.tennisbuddy.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tennisbuddy.entities.Friends;

import java.util.List;

@Dao
public interface FriendsDao {
    @Query("SELECT * FROM friends")
    List<Friends> getFriends();

    @Query("SELECT * FROM friends where user1 = :id OR user2 = :id")
    List<Friends> getFriendsById(int id);

    @Insert
    void addFriends(Friends friends);

    @Delete
    void deleteFriends(Friends friends);
}
