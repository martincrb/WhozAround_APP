package com.studios.betta.whozaround.fragments;

/**
 * Created by Martin on 09/04/2016.
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studios.betta.whozaround.R;
import com.studios.betta.whozaround.adapters.TripAdapter;
import com.studios.betta.whozaround.objects.Trip;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MyTripsFragment extends Fragment {

    private RecyclerView triplist;
    public MyTripsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_trips, container, false);
        triplist = (RecyclerView) rootView.findViewById(R.id.triplist);
        //We use linearmanager as layout
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        triplist.setLayoutManager(llm);
        ArrayList<Trip> trips = new ArrayList<Trip>();
        trips.add(new Trip("Travel", "La Molina", "Día de snow fantástico", "Mañana", R.drawable.bcn));
        trips.add(new Trip("Travel", "Vallter", "Día de snow fantástico", "Pasado", R.drawable.bcn));
        trips.add(new Trip("Travel", "St Eulalia", "Barbeque con los coleguis <3", "En veranico", R.drawable.bcn));
        trips.add(new Trip("Travel", "La Molina", "Día de snow fantástico", "2/1/17", R.drawable.bcn));
        trips.add(new Trip("Travel", "La Molina", "Día de snow fantástico","2/1/17", R.drawable.bcn));

        TripAdapter adapter = new TripAdapter(trips);
        triplist.setAdapter(adapter);
        return rootView;
    }
}