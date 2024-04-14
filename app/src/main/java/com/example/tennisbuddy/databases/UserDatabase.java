package com.example.tennisbuddy.databases;

import android.content.Context;
import android.service.autofill.UserData;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tennisbuddy.daos.UserDao;
import com.example.tennisbuddy.entities.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public static UserDatabase instance;
    public abstract UserDao userDao();

    private static final RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static UserDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "tbl_users").
                        addCallback(sOnOpenCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return instance;
    }
}
