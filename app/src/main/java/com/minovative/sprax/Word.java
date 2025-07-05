package com.minovative.sprax;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "words", indices = {@Index(value = {"german_word"}, unique = true)})
public class Word  {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "german_word")
    private String germanWord;
    @ColumnInfo(name = "meaning")
    private String meaning;
    @ColumnInfo(name = "example_sentence")
    private String exampleSentence;
    @ColumnInfo(name = "example_meaning")
    private String exampleMeaning;
    private boolean isKnown;
    private boolean unlearned;
    @ColumnInfo(name = "activity_name")
    private String activityName;

    private String imgPath;
    private String audioPath;

    public Word(String germanWord, String meaning, String exampleSentence, String exampleMeaning,
                String activityName, String imgPath, String audioPath) {

        this.germanWord = germanWord;
        this.meaning = meaning;
        this.exampleSentence = exampleSentence;
        this.exampleMeaning = exampleMeaning;
        this.activityName = activityName;
        this.imgPath = imgPath;
        this.audioPath = audioPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGermanWord() {
        return germanWord;
    }

    public void setGermanWord(String germanWord) {
        this.germanWord = germanWord;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getExampleSentence() {
        return exampleSentence;
    }

    public void setExampleSentence(String exampleSentence) {
        this.exampleSentence = exampleSentence;
    }

    public String getExampleMeaning() {
        return exampleMeaning;
    }

    public void setExampleMeaning(String exampleMeaning) {
        this.exampleMeaning = exampleMeaning;
    }

    public boolean isKnown() {
        return isKnown;
    }

    public void setKnown(boolean known) {
        isKnown = known;
    }

    public boolean isUnlearned() {
        return unlearned;
    }

    public void setUnlearned(boolean unlearned) {
        this.unlearned = unlearned;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getImgPath( ) {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getAudioPath( ) {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }
}
