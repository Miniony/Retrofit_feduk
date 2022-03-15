package com.example.smart_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Timepicker extends AppCompatActivity {
    private TextView mInfoTextView;
    private TimePicker mTimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        mInfoTextView = findViewById(R.id.textView);
        mTimePicker = findViewById(R.id.timePicker);

        Calendar now = Calendar.getInstance();

        mTimePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        mTimePicker.setCurrentMinute(now.get(Calendar.MINUTE));

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(getApplicationContext(), "onTimeChanged",
                        Toast.LENGTH_SHORT).show();

                mInfoTextView.setText("Часы: " + hourOfDay + "\n" + "Минуты: "
                        + minute);
            }
        });

        Button timeButton = findViewById(R.id.button);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInfoTextView.setText(new StringBuilder()
                        .append(mTimePicker.getCurrentHour()).append(".")
                        .append(mTimePicker.getCurrentMinute()));
            }
        });
    }
}