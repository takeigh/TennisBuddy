package com.example.tennisbuddy.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tennisbuddy.daos.KeepUserDao;
import com.example.tennisbuddy.entities.KeepUser;

@Database(entities = {KeepUser.class}, version = 1, exportSchema = false)
public abstract class KeepUserDatabase extends RoomDatabase {
    public static KeepUserDatabase instance;
    public abstract KeepUserDao keepUserDao();

    private static final RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static KeepUserDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (KeepUserDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), KeepUserDatabase.class, "tbl_keepUser").
                        addCallback(sOnOpenCallback)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return instance;
    }
}
