package com.example.tennisbuddy.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tennisbuddy.daos.CourtDao;
import com.example.tennisbuddy.entities.Court;

@Database(entities = {Court.class}, version = 1, exportSchema = false)
public abstract class CourtDatabase extends RoomDatabase {
    public static CourtDatabase instance;
    public abstract CourtDao courtDao();

    private static final RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static CourtDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (CourtDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), CourtDatabase.class, "tbl_courts").
                        addCallback(sOnOpenCallback)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return instance;
    }
}
