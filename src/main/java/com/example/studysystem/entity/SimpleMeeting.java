package com.example.studysystem.entity;

public class SimpleMeeting {
    private int id;
    private String Publication_title;
    private String Publication_year;

    public String getPublication_title() {
        return Publication_title;
    }

    public void setPublication_title(String publication_title) {
        Publication_title = publication_title;
    }

    public String getPublication_year() {
        return Publication_year;
    }

    public void setPublication_year(String publication_year) {
        Publication_year = publication_year;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
