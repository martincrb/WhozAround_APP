package com.studios.betta.whozaround.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.studios.betta.whozaround.R;
import com.studios.betta.whozaround.objects.Trip;

import java.util.List;

/**
 * Created by Martin on 09/04/2016.
 */
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    List<Trip> trips;

    public TripAdapter(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_card_layout, viewGroup, false);
        TripViewHolder tvh = new TripViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TripViewHolder tripViewHolder, int i) {
        tripViewHolder.date.setText(trips.get(i).date);
        tripViewHolder.name.setText(trips.get(i).title);
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class TripViewHolder extends  RecyclerView.ViewHolder {
        CardView cv;
        TextView name;
        TextView date;

        TripViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.trip_name);
            date = (TextView) itemView.findViewById(R.id.trip_date);
        }
    }
}
