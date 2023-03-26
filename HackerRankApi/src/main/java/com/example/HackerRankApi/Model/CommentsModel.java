package com.example.HackerRankApi.Model;

import java.util.List;

public class CommentsModel {
    private String text;
    private String by;
    private List<Integer> kids;

    public CommentsModel() {
    }

    public CommentsModel(String text, String by, List<Integer> kids) {
        this.text = text;
        this.by = by;
        this.kids = kids;
    }

    public CommentsModel(String text, String by) {
        this.text = text;
        this.by = by;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public Integer getChildCommentsCount() {
        return kids != null ? kids.size() : 0;
    }

    @Override
    public String toString() {
        return "Text: " + text + ", By: " + by;
    }
}
