package com.example.cchelper;

public class ContestsModel {
    String name;
    String start_time;
    String end_time;
    String duration;
    String url;
    String site;

    public ContestsModel(String name, String start_time, String end_time, String duration, String url, String site) {
        this.name = name;
        this.start_time = start_time;
        this.end_time = end_time;
        this.duration = duration;
        this.url = url;
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}