package com.rezazali.metro.model;

public class Lines {
    private int id;
    private String title;
    private String englishTitle;


    public Lines() {

    }

    public Lines(int id, String title, String englishTitle) {
        this.id = id;
        this.title = title;
        this.englishTitle = englishTitle;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnglishTitle() {
        return englishTitle;
    }

    public void setEnglishTitle(String englishTitle) {
        this.englishTitle = englishTitle;
    }
}


