package com.studios.betta.whozaround;

import android.os.Bundle;
import android.app.Activity;

public class TripDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
