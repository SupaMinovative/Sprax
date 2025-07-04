package com.minovative.sprax;

public class Level {
    private String levelName;
    private String levelClassName;
    private String levelDif;

    public Level(String levelName,String levelClassName,String levelDif) {
        this.levelName = levelName;
        this.levelClassName = levelClassName;
        this.levelDif = levelDif;
    }

    public String getLevelName( ) {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getLevelClassName( ) {
        return levelClassName;
    }

    public void setLevelClassName(String levelClassName) {
        this.levelClassName = levelClassName;
    }

    public String getLevelDif( ) {
        return levelDif;
    }

    public void setLevelDif(String levelDif) {
        this.levelDif = levelDif;
    }
}
