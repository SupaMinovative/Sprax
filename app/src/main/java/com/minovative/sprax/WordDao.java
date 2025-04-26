package com.minovative.sprax;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Word> words);
    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("SELECT * FROM words")
    List<Word> getAll();

    @Query("SELECT * FROM words WHERE isKnown = 1 AND activity_name = :activityName")
    List<Word> getKnownWords(String activityName);

    @Query("SELECT * FROM words WHERE activity_name = :activityName")
    List<Word> getAllWords(String activityName);

    @Query("SELECT * FROM words WHERE isKnown = 1")
    List<Word> getAllKnownWords();

    @Query("SELECT * FROM words WHERE unlearned = 1 AND activity_name = :activityName")
    List<Word> getUnlearnedWords(String activityName);

    @Query("DELETE FROM words WHERE isKnown = 1 AND activity_name = :activityName")
    void clearWords(String activityName);

}
