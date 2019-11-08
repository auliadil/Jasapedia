package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.ServiceAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.DatePickerFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.TimePickerFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.BookingViewModel;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

public class AddBookingActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private TextView txtName;
    private SharedPreferences sharedpreference;
    private ServiceViewModel serviceViewModel;
    private List<Service> serviceList;
    private ServiceAdapter adapter;
    private EditText txtNote, txtDate, txtTime;
    private Button btnDatePicker, btnTimePicker, btnSave, btnCancelAlarm;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar c;
    private String serviceName;
    private BookingViewModel bookingViewModel;

    public static final String BOOKING_SERVICE_ID = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.BOOKING_NOTE";
    public static final String BOOKING_NOTE = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.BOOKING_NOTE";
    public static final String BOOKING_DATE = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_OVERVIEW";
    public static final String BOOKING_TIME = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_RATING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);


        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


        btnDatePicker = findViewById(R.id.btn_datepicker);
        btnTimePicker = findViewById(R.id.btn_timepicker);
        btnCancelAlarm = findViewById(R.id.button_cancel);
        btnSave = findViewById(R.id.save_booking);
        txtName = findViewById(R.id.et_booking_name);
        txtNote = findViewById(R.id.et_booking_note);
        txtDate = findViewById(R.id.et_booking_date);
        txtTime = findViewById(R.id.et_booking_time);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });

        Intent intent = getIntent();

        serviceName = intent.getStringExtra("SERVICE_NAME");
        txtName.setText(serviceName);

        sharedpreference= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {
            DialogFragment datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        }
        if (v == btnTimePicker) {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        }

        if (v == btnSave) {
            saveBooking();
        }
    }

    private void saveBooking() {
        if (!validate()) {
            Toast.makeText(this, "Please insert full", Toast.LENGTH_LONG);
            return;
        }
        String note = txtNote.getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        Log.d("TimeMillis","time in millis: " + c.getTimeInMillis() + " and time in timezone: " + c.getTimeZone());
        startAlarm(c);
        finish();
        Log.d("ActivityResult", "muncul");
        Booking booking = new Booking(serviceName, note, date, time);
        bookingViewModel.insert(booking);
        Toast.makeText(this, "Booking saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        txtTime.setText(hourOfDay + ":" + minute);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        txtDate.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
    }

    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    public boolean validate() {
        boolean valid = true;

        String name = txtName.getText().toString();
        String note = txtNote.getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();

        if (name.isEmpty()) {
            txtName.setError("enter a valid name");
            valid = false;
        }

        if (note.isEmpty()) {
            txtNote.setError("enter a valid note");
            valid = false;
        }

        if (date.isEmpty()) {
            txtDate.setError("enter a valid category");
            valid = false;
        }

        if (time.isEmpty()) {
            txtTime.setError("enter a valid location");
            valid = false;
        }
        return valid;
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        txtNote.setText("Alarm canceled");
    }
}
