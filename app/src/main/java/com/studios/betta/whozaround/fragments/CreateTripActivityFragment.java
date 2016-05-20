package com.studios.betta.whozaround.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.studios.betta.whozaround.R;
import com.studios.betta.whozaround.network.WhozAroundEndpointInterface;
import com.studios.betta.whozaround.objects.Trip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateTripActivityFragment extends Fragment {
    private final String LOG_TAG = getClass().getSimpleName();
    @Bind(R.id.from_date) TextView date_from_text;
    @Bind(R.id.to_date) TextView date_to_text;
    @Bind(R.id.city_trip) EditText city_edit;
    @Bind(R.id.description_trip) EditText description_edit;

    private int edit_text_edited;

    public CreateTripActivityFragment() {
        edit_text_edited = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_trip, container, false);
        ButterKnife.bind(this, rootView);
        //First get calendar
        final Calendar myCalendar = Calendar.getInstance();
        //Define the function when changing date_from
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if (edit_text_edited == 0) {
                    //edit from
                    date_from_text.setText("From: "+sdf.format(myCalendar.getTime()));
                }
                else if (edit_text_edited == 1) {
                    //edit to
                    date_to_text.setText("To: "+sdf.format(myCalendar.getTime()));
                }
            }

        };

        date_from_text.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_text_edited = 0;
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        date_to_text.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_text_edited = 1;
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;
    }

    @OnClick(R.id.create_trip_button)
    public void createTrip() {
        String from = date_from_text.getText().toString().split(" ")[1];
        String to = date_to_text.getText().toString().split(" ")[1];
        String city = city_edit.getText().toString();
        String desc = description_edit.getText().toString();

        //String title, String location, String description, String date_from, String image_url
        Trip newT = new Trip(from, to, city, desc, "url", 0, Profile.getCurrentProfile().getName(), "WzArnd BETA", false);
        sendTripToServer(newT);
    }

    public void sendTripToServer(Trip t) {
        String BASE_URL = "http://52.38.181.114/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WhozAroundEndpointInterface apiService = retrofit.create(WhozAroundEndpointInterface.class);


        Call<Trip> call = apiService.addTrip(Profile.getCurrentProfile().getName(), t);
        call.enqueue(new Callback<Trip>() {
            @Override
            public void onResponse(Call<Trip> call, Response<Trip> response) {
                Log.d(LOG_TAG, "RESPONSE SEND TRIP");
                getActivity().finish();
            }

            @Override
            public void onFailure(Call<Trip> call, Throwable t) {
                Log.d(LOG_TAG, "FAILURE SEND TRIP" + t.getLocalizedMessage());
                Toast.makeText(getActivity(), "Failure sending trip to server", Toast.LENGTH_LONG).show();
            }
        });
    }


}
