package com.studios.betta.whozaround.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studios.betta.whozaround.R;
import com.studios.betta.whozaround.objects.Trip;

import java.util.List;

/**
 * Created by Martin on 09/04/2016.
 */
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    List<Trip> trips;
    Context context;
    public TripAdapter(List<Trip> trips, Context context) {
        this.trips = trips; this.context = context;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trip_card_layout, viewGroup, false);
        TripViewHolder tvh = new TripViewHolder(v);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TripViewHolder tripViewHolder, int i) {
        tripViewHolder.type.setText(trips.get(i).title);

        tripViewHolder.location.setText(trips.get(i).location);

        tripViewHolder.date.setText(trips.get(i).date);

        tripViewHolder.description.setText(trips.get(i).description);

        if (!trips.get(i).isFb()) {
            tripViewHolder.fb_marker.setText("");
        }
        if (trips.get(i).image == -1) {
            Picasso.with(context).load(trips.get(i).image_url).into(tripViewHolder.image);
            //tripViewHolder.image.setImageResource(trips.get(i).image);
        }
        else {
            tripViewHolder.image.setImageResource(trips.get(i).image);
        }
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
        TextView type;
        TextView location;
        TextView description;
        TextView date;
        TextView fb_marker;
        ImageView image;

        TripViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            type = (TextView) itemView.findViewById(R.id.plan_type);
            location = (TextView) itemView.findViewById(R.id.trip_location);
            description = (TextView) itemView.findViewById(R.id.description_text);
            date = (TextView) itemView.findViewById(R.id.date_text);
            image = (ImageView) itemView.findViewById(R.id.location_image);
            fb_marker = (TextView) itemView.findViewById(R.id.fb_event_mark);
        }
    }
}
