package com.studios.betta.whozaround.objects;

/**
 * Created by Martin on 09/04/2016.
 */
public class Trip {
    public String title;
    public String location;
    public String description;
    public String date;
    public int image;
    public String image_url;
    public boolean isFb;

    public Trip (String title, String location, String description, String date, int image) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.description = description;
        this.image = image;
        this.image_url = "";

    }

    public Trip (String title, String location, String description, String date, String image_url) {
        this.title = title;
        this.location = location;
        this.date = date;
        this.description = description;
        this.image = -1;
        this.image_url = image_url;

    }

    public boolean isFb() {return isFb;}
    public void setIsFb(boolean isFb) {this.isFb = isFb;}
}
