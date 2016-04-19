package com.studios.betta.whozaround.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.studios.betta.whozaround.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateTripActivityFragment extends Fragment {
    @Bind(R.id.from_date) TextView date_from_text;
    @Bind(R.id.to_date) TextView date_to_text;
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
        //Define the function when changing date
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
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
}
