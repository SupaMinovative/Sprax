package com.minovative.sprax;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insert(Word word);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("SELECT * FROM words")
    List<Word> getAll();

    @Query("SELECT * FROM words WHERE isKnown = 1")
    List<Word> getKnownWords();

    @Query("SELECT * FROM words WHERE unlearned = 1 AND activity_name = :activityName")
    List<Word> getUnlearnedWords(String activityName);

    List<Word> getUnlearnedWords();

    @Query("SELECT COUNT(*) FROM words WHERE unlearned = 1")
    int getUnlearnedWordsCount();

    @Query("SELECT * FROM words WHERE id = :wordId")
    Word getWordById(int wordId);
}
