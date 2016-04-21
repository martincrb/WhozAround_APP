package com.studios.betta.whozaround;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.Window;

public class TripDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getActionBar().setTitle("");
        getActionBar().setElevation(0);
        setContentView(R.layout.activity_trip_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
