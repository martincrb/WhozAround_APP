package com.studios.betta.whozaround.fragments;

/**
 * Created by Martin on 09/04/2016.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.studios.betta.whozaround.TripDetailActivity;
import com.studios.betta.whozaround.adapters.TripAdapter;
import com.studios.betta.whozaround.network.FacebookUtils;
import com.studios.betta.whozaround.objects.Trip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyTripsFragment extends Fragment {

    //Bind widgets to objects (Butterknife <3)
    @Bind(R.id.triplist)        RecyclerView triplist;
    @Bind(R.id.add_trip_button) Button add_trip_button;
    @Bind(R.id.profile_picture) ImageView profile_image;

    public MyTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_trips, container, false);
        ButterKnife.bind(this, rootView);

        //We use linearmanager as layout
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        triplist.setLayoutManager(llm);

        final ArrayList<Trip> trips = new ArrayList<Trip>();
        trips.add(new Trip("Travel", "La Molina", "Día de snow fantástico", "Mañana", R.drawable.bcn));
        trips.add(new Trip("Travel", "Vallter", "Día de snow fantástico", "Pasado", R.drawable.bcn));


        final TripAdapter adapter = new TripAdapter(trips, getActivity());
        adapter.setOnItemClickListener(new TripAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //Butterknife does not support onItemClick on adapter for RecyclerViews
                String name = trips.get(position).location;
                Intent intent = new Intent(getActivity(), TripDetailActivity.class);
                if (trips.get(position).image == -1) {
                    intent.putExtra("image_type", "ID");
                    intent.putExtra("image", trips.get(position).image_url);
                }
                else {
                    intent.putExtra("image_type", "URL");
                    intent.putExtra("image", trips.get(position).image);
                }
                startActivity(intent);
            }
        });
        triplist.setAdapter(adapter);

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
                        JSONObject data = response.getJSONObject();
                        try {
                            JSONArray events = data.getJSONArray("data");
                            for (int i = 0; i < events.length(); ++i) {
                                JSONObject event = events.getJSONObject(i);
                                Trip t = Trip.fromFBEventJSONObject(event);

                                //TODO: FB EVENTS?
                                //trips.add(t);
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

    @OnClick(R.id.add_trip_button)


    public void startCreateTripActivity() {
        //Move to create trip screen
        Intent intent = new Intent(getActivity(), CreateTripActivity.class);
        startActivity(intent);
    }
}