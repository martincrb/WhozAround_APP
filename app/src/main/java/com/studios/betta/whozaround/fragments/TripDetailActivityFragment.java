package com.studios.betta.whozaround.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.studios.betta.whozaround.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class TripDetailActivityFragment extends Fragment {

    @Bind(R.id.detail_bg) ImageView bg_image;
    public TripDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_trip_detail, container, false);
        ButterKnife.bind(this, rootView);
        String type = getActivity().getIntent().getExtras().getString("image_type");
        Picasso.with(getActivity()).load(getActivity().getIntent().getExtras().getString("image")).into(bg_image);
        return rootView;
    }
}
