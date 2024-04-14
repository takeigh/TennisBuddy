package com.example.tennisbuddy.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tennisbuddy.entities.Match;

import java.util.List;

@Dao
public interface MatchDao {
    @Query("SELECT * FROM 'match'")
    List<Match> getMatches();

    @Query("SELECT * FROM 'match' WHERE matchId = :matchId LIMIT 1")
    Match getMatchById(int matchId);

    @Insert
    void addMatch(Match match);
}
