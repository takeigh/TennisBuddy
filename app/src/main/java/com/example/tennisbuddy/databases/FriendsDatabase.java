package com.example.tennisbuddy.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tennisbuddy.daos.FriendsDao;
import com.example.tennisbuddy.entities.Friends;

@Database(entities = {Friends.class}, version = 1, exportSchema = false)
public abstract class FriendsDatabase extends RoomDatabase {
    public static FriendsDatabase instance;
    public abstract FriendsDao friendsDao();

    private static final RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static FriendsDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (FriendsDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), FriendsDatabase.class, "tbl_friends").
                        addCallback(sOnOpenCallback)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return instance;
    }
}
