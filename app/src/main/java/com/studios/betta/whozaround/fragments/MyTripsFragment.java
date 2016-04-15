package com.studios.betta.whozaround.fragments;

/**
 * Created by Martin on 09/04/2016.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.studios.betta.whozaround.CreateTripActivity;
import com.studios.betta.whozaround.R;
import com.studios.betta.whozaround.adapters.TripAdapter;
import com.studios.betta.whozaround.network.FacebookUtils;
import com.studios.betta.whozaround.objects.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyTripsFragment extends Fragment {

    private RecyclerView triplist;
    private Button add_trip_button;
    private ImageView profile_image;
    public MyTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_trips, container, false);
        triplist = (RecyclerView) rootView.findViewById(R.id.triplist);
        profile_image = (ImageView) rootView.findViewById(R.id.profile_picture);
        //We use linearmanager as layout
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        triplist.setLayoutManager(llm);
        final ArrayList<Trip> trips = new ArrayList<Trip>();
        trips.add(new Trip("Travel", "La Molina", "Día de snow fantástico", "Mañana", R.drawable.bcn));
        trips.add(new Trip("Travel", "Vallter", "Día de snow fantástico", "Pasado", R.drawable.bcn));
        //trips.add(new Trip("Travel", "St Eulalia", "Barbeque con los coleguis <3", "En veranico", R.drawable.bcn));
        //trips.add(new Trip("Travel", "La Molina", "Día de snow fantástico", "2/1/17", R.drawable.bcn));
        //trips.add(new Trip("Travel", "La Molina", "Día de snow fantástico","2/1/17", R.drawable.bcn));

        final TripAdapter adapter = new TripAdapter(trips, getActivity());
        triplist.setAdapter(adapter);

        //Add Trip Button
        add_trip_button = (Button) rootView.findViewById(R.id.add_trip_button);
        add_trip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Move to create trip screen
                Intent intent = new Intent(getActivity(), CreateTripActivity.class);
                startActivity(intent);
                //getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
            }
        });

        FacebookUtils.setUserProfilePicture(profile_image, getActivity());
        FacebookUtils.getUserFriends();
        //FacebookUtils.getUserEvents();
        Bundle params = new Bundle();
        params.putString("fields", "type=attending,name,type,category,description,place,cover");
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
                                trips.add(t);

                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();



        return rootView;
    }
}