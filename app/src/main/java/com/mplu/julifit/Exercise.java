package com.mplu.julifit;

public class Exercise {

    private String title;
    private String shortDescription;
    private String secondaryType;

    public Exercise(){

    }

    public Exercise(String title, String shortDescription, String secondaryType) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.secondaryType = secondaryType;
    }


    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }


    public String getSecondaryType() {
        return secondaryType;
    }

}
