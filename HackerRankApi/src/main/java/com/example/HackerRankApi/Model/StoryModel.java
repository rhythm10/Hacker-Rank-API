package com.example.HackerRankApi.Model;

public class StoryModel {
    private String title;
    private String url;
    private int score;
    private long time;
    private String by;
    private long id;

    public StoryModel() {
    }
    public StoryModel(String title, String url, int score, long time, String by, long id) {
        this.title = title;
        this.url = url;
        this.score = score;
        this.time = time;
        this.by = by;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
