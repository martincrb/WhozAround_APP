package com.studios.betta.whozaround.network;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.squareup.picasso.Picasso;
import com.studios.betta.whozaround.ImageTransforms.CircleTransform;
import com.studios.betta.whozaround.objects.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Martin on 15/04/2016.
 */
public class FacebookUtils {

    private static ArrayList<Trip> fb_events;
    static public void setUserProfilePicture(ImageView view, Context context) {
        final Context context_ = context;
        final ImageView view_ = view;
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
                                    // set profile image to imageview using Picasso or Native methods
                                    Picasso.with(context_).load(profilePicUrl)
                                            .transform(new CircleTransform())
                                            .into(view_);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
    }

    static public void getUserFriends() {
        Bundle params = new Bundle();
        params.putString("fields", "id,email,gender,cover,picture.type(large)");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/friends",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        JSONObject data = response.getJSONObject();
                        try {
                            JSONArray friends = data.getJSONArray("data");
                            Log.d("Friendlist", friends.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }

    static public void getFacebookEvent(String event_id) {
        Log.d("Event fetching", "/"+event_id);
        Bundle params = new Bundle();
        params.putString("fields", "description,name,place,category,cover");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+event_id,
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */

                        JSONObject data = response.getJSONObject();
                        Log.d("Event fetch completed", data.toString());
                        try {
                            String category, name, place, image_url, description;
                            category = name = place = image_url = description = "Not available";
                            if (data.has("category")) {
                                category = data.getString("category");
                            }
                            if (data.has("name")) {
                                name = data.getString("name");
                            }
                            if (data.has("place")) {
                                place = data.getJSONObject("place").getJSONObject("location").getString("city");
                            }
                            if (data.has("image_url")) {
                                image_url = data.getJSONObject("cover").getString("source");
                            }
                            if (data.has("category")) {
                                category = data.getString("category");
                            }
                            if (data.has("description")) {
                                description = data.getString("description");
                            }

                            Log.d("Event details", category + " " + name);
                           // Trip event = new Trip(category, place, description, "lala", image_url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();
    }
    static public void getUserEvents() {

        Bundle params = new Bundle();
        params.putString("fields", "type=attending,name,type,category,description,place");
        GraphRequestAsyncTask graphRequestAsyncTask = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/events",
                params,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        JSONObject data = response.getJSONObject();
                        try {
                            JSONArray events = data.getJSONArray("data");
                            for (int i = 0; i < events.length(); ++i) {

                                JSONObject event = events.getJSONObject(i);
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
                                if (event.has("image_url")) {
                                    image_url = event.getJSONObject("cover").getString("source");
                                }
                                if (event.has("category")) {
                                    category = event.getString("category");
                                }
                                if (event.has("description")) {
                                    description = event.getString("description");
                                }

                                Log.d("Event details",  name + " " + city);
                                //getFacebookEvent(event.getString("id"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();
    }
}
