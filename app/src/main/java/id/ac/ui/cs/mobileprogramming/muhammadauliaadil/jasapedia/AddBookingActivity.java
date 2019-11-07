package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.ServiceAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.databases.ServiceDatabase;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.TimePickerFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

public class AddBookingActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etBookingName;
    private EditText etBookingTime;
    private SharedPreferences sharedpreference;
    private ServiceViewModel serviceViewModel;
    private List<Service> serviceList;
    private ServiceAdapter adapter;

    private EditText txtDate, txtTime;

    private Button btnDatePicker, btnTimePicker, buttonCancelAlarm;

    private int mYear, mMonth, mDay, mHour, mMinute;

    private Service service;

    private TextView mTextView;

    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        btnDatePicker = findViewById(R.id.button_datepicker);
        btnTimePicker = findViewById(R.id.button_timepicker);
        txtDate = findViewById(R.id.et_booking_date);
        txtTime = findViewById(R.id.et_booking_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);


//        btnDatePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//                    }
//                }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            });
//        });

//        btnTimePicker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getSupportFragmentManager(), "time picker");
//            }
//        });

        buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        Intent intent = getIntent();
        ServiceDatabase db;

        int id = Integer.parseInt(intent.getStringExtra("SERVICE_ID"));
        etBookingName.setText(intent.getStringExtra("SERVICE_NAME"));

        sharedpreference= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }


//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        c.set(Calendar.MINUTE, minute);
//        c.set(Calendar.SECOND, 0);
//
//        updateTimeText(c);
//        startAlarm(c);
//    }
//
//    private void updateTimeText(Calendar c) {
//        String timeText = "Alarm set for: ";
//        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
//
//        etBookingTime.setText(timeText);
//    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        mTextView.setText("Alarm canceled");
    }


    public void onClickSaveBooking(View view){
        startAlarm(c);
    }
}
