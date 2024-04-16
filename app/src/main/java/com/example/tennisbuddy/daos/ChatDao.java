package com.example.tennisbuddy.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tennisbuddy.entities.Chat;

import java.util.List;

@Dao
public interface ChatDao {
    @Query("SELECT * FROM 'chat'")
    List<Chat> getChats();

    @Query("SELECT * FROM 'chat' WHERE sender = :senderId AND receiver = :receiverId")
    List<Chat> getChatsByIDs(int senderId, int receiverId);

    @Insert
    void addChat(Chat chat);
}
