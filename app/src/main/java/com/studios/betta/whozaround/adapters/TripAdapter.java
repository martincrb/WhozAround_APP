package com.studios.betta.whozaround.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;
import com.studios.betta.whozaround.R;
import com.studios.betta.whozaround.objects.Trip;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Martin on 09/04/2016.
 */
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    //Define listeners for touch events
    private static OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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
        YoYo.with(Techniques.FadeIn).duration(500).playOn(tripViewHolder.cv);

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
        @Bind(R.id.cv)CardView cv;
        @Bind(R.id.plan_type)TextView type;
        @Bind(R.id.trip_location)TextView location;
        @Bind(R.id.description_text)TextView description;
        @Bind(R.id.date_text)TextView date;
        @Bind(R.id.fb_event_mark)TextView fb_marker;
        @Bind(R.id.location_image)ImageView image;

        TripViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getPosition());
                }
            });

        }
    }
}
