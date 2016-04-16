package com.studios.betta.whozaround.objects;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Martin on 09/04/2016.
 */
public class Trip {
    public Long _id; //For cupboard model

    public String title;
    public String location;
    public String description;
    public String date;
    public String date2;
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

    public static Trip fromFBEventJSONObject(JSONObject event) throws JSONException {
        String category, name, place, city, image_url, description;
        category = name = place = image_url = description = city = "Not available";
        if (event.has("category")) {
            category = event.getString("category");
        }
        if (event.has("name")) {
            name = event.getString("name");
        }
        if (event.has("place")) {
            place = event.getJSONObject("place").getString("name");
            if (event.getJSONObject("place").has("location")) {
                city = event.getJSONObject("place").getJSONObject("location").getString("city");
            }
        }
        if (event.has("cover")) {
            image_url = event.getJSONObject("cover").getString("source");
        }
        if (event.has("category")) {
            category = event.getString("category");
        }
        if (event.has("description")) {
            description = event.getString("description");
        }

        Log.d("Event details", name + " " + city);
        //getFacebookEvent(event.getString("id"));
        Trip t = new Trip(category, city, description, "Never", image_url);
        t.setIsFb(true);
        return t;
    }

    public boolean isFb() {return isFb;}
    public void setIsFb(boolean isFb) {this.isFb = isFb;}
}
