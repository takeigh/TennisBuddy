package com.example.tennisbuddy.databases;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tennisbuddy.daos.MatchDao;
import com.example.tennisbuddy.entities.Court;
import com.example.tennisbuddy.entities.Match;
import com.example.tennisbuddy.entities.User;

@Database(entities = {Match.class}, version = 3, exportSchema = false)
public abstract class MatchDatabase extends RoomDatabase {
    public static MatchDatabase instance;
    public abstract MatchDao matchDao();

    private static final RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    public static MatchDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (MatchDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), MatchDatabase.class, "tbl_matches").
                        addCallback(sOnOpenCallback)
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return instance;
    }
}
