package com.example.tennisbuddy.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tennisbuddy.daos.CourtDao;

public abstract class CourtDatabase extends RoomDatabase {
    public static UserDatabase instance;
    public abstract CourtDao courtDao();

    private static final RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static UserDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (UserDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "tbl_courts").
                        addCallback(sOnOpenCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }

        return instance;
    }
}
