package com.example.cchelper;

public class NoteModel {
    private String title;
    private String time;

    public NoteModel(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String content) {
        this.time = content;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "title='" + title + '\'' +
                ", content='" + time + '\'' +
                '}';
    }
}
