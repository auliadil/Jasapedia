package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.activities;

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
import android.os.Build;
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

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.ServiceAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.broadcastreceivers.AlertReceiver;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.DatePickerFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.TimePickerFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.BookingViewModel;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

public class AddBookingActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

    private TextView txtName;
    private SharedPreferences sharedpreference;
    private ServiceViewModel serviceViewModel;
    private List<Service> serviceList;
    private ServiceAdapter adapter;
    private EditText txtNote, txtDate, txtTime, txtAmount;
    private Button btnDatePicker, btnTimePicker, btnSave;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar c;
    private String serviceName;
    private int serviceCost;
    private BookingViewModel bookingViewModel;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        init();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    public void init() {
        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);

        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        btnDatePicker = findViewById(R.id.btn_datepicker);
        btnTimePicker = findViewById(R.id.btn_timepicker);
        btnSave = findViewById(R.id.save_booking);
        txtName = findViewById(R.id.et_booking_name);
        txtNote = findViewById(R.id.et_booking_note);
        txtDate = findViewById(R.id.et_booking_date);
        txtTime = findViewById(R.id.et_booking_time);
        txtAmount = findViewById(R.id.et_booking_amount);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        Intent intent = getIntent();

        serviceName = intent.getStringExtra("SERVICE_NAME");
        serviceCost = intent.getIntExtra("SERVICE_COST", 0);
        txtName.setText(serviceName);

        sharedpreference= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
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
        String serviceName = txtName.getText().toString();
        String note = txtNote.getText().toString();
        String date = txtDate.getText().toString();
        String time = txtTime.getText().toString();
        int amount = Integer.parseInt(txtAmount.getText().toString());
        int bookingFee = calculateBookingFee(amount, serviceCost);

        Log.d("TimeMillis","time in millis: " + c.getTimeInMillis() + " and time in timezone: " + c.getTimeZone());
        startAlarm(serviceName, c);
        finish();
        Log.d("ActivityResult", "muncul");
        Booking booking = new Booking(serviceName, note, date, time, bookingFee);
        bookingViewModel.insert(booking);
        Toast.makeText(this, "Booking saved", Toast.LENGTH_SHORT).show();
    }

    public native int calculateBookingFee(int amount, int cost);

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

    private void startAlarm(String serviceName, Calendar cal){
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        intent.putExtra("serviceName", serviceName);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
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
}
