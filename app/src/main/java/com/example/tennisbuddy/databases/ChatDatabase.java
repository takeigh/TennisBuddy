package com.example.tennisbuddy.databases;

import androidx.room.Database;

import com.example.tennisbuddy.entities.Chat;
import com.example.tennisbuddy.entities.User;

@Database(entities = {User.class, Chat.class}, version = 1, exportSchema = false)
public class ChatDatabase {

}
