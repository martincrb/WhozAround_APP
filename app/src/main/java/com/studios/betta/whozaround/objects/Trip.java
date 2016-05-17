package com.studios.betta.whozaround.objects;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;


public class Trip {

    @SerializedName("date_from")
    @Expose
    private String dateFrom;
    @SerializedName("date_until")
    @Expose
    private String dateUntil;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("image")
    @Expose
    private Integer image;
    @SerializedName("creator")
    @Expose
    private String creator;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("isFb")
    @Expose
    private Boolean isFb;
    @SerializedName("_id")
    @Expose
    private String Id;
    @SerializedName("__v")
    @Expose
    private Integer V;

    /**
     * No args constructor for use in serialization
     *
     */
    public Trip() {
    }

    /**
     *
     * @param dateUntil
     * @param title
     * @param imageUrl
     * @param description
     * @param image
     * @param isFb
     * @param dateFrom
     * @param creator
     * @param city
     */
    public Trip(String dateFrom, String dateUntil, String city, String description, String imageUrl, Integer image, String creator, String title, Boolean isFb) {
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.city = city;
        this.description = description;
        this.imageUrl = imageUrl;
        this.image = image;
        this.creator = creator;
        this.title = title;
        this.isFb = isFb;
    }

    /**
     *
     * @param dateUntil
     * @param title
     * @param V
     * @param imageUrl
     * @param description
     * @param image
     * @param Id
     * @param isFb
     * @param dateFrom
     * @param creator
     * @param city
     */
    public Trip(String dateFrom, String dateUntil, String city, String description, String imageUrl, Integer image, String creator, String title, Boolean isFb, String Id, Integer V) {
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.city = city;
        this.description = description;
        this.imageUrl = imageUrl;
        this.image = image;
        this.creator = creator;
        this.title = title;
        this.isFb = isFb;
        this.Id = Id;
        this.V = V;
    }

    /**
     *
     * @return
     * The dateFrom
     */
    public String getDateFrom() {
        return dateFrom;
    }

    /**
     *
     * @param dateFrom
     * The date_from
     */
    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     *
     * @return
     * The dateUntil
     */
    public String getDateUntil() {
        return dateUntil;
    }

    /**
     *
     * @param dateUntil
     * The date_until
     */
    public void setDateUntil(String dateUntil) {
        this.dateUntil = dateUntil;
    }

    /**
     *
     * @return
     * The city
     */
    public String getCity() {
        return city;
    }

    /**
     *
     * @param city
     * The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image_url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The image
     */
    public Integer getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(Integer image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @param creator
     * The creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The isFb
     */
    public Boolean getIsFb() {
        return isFb;
    }

    /**
     *
     * @param isFb
     * The isFb
     */
    public void setIsFb(Boolean isFb) {
        this.isFb = isFb;
    }

    /**
     *
     * @return
     * The Id
     */
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The V
     */
    public Integer getV() {
        return V;
    }

    /**
     *
     * @param V
     * The __v
     */
    public void setV(Integer V) {
        this.V = V;
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
        //TODO: REPAIR THIS
        // Trip t = new Trip(category, city, description, "Never", "Never", image_url);
        // t.setIsFb(true);
        return null;
    }

}