package com.example.tennisbuddy.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tennisbuddy.daos.ChatDao;
import com.example.tennisbuddy.entities.Chat;
import com.example.tennisbuddy.entities.User;

@Database(entities = {Chat.class}, version = 3, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {
    public static ChatDatabase instance;
    public abstract ChatDao chatDao();

    private static final RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static ChatDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (ChatDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), ChatDatabase.class, "tbl_chats").
                        addCallback(sOnOpenCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return instance;
    }
}
