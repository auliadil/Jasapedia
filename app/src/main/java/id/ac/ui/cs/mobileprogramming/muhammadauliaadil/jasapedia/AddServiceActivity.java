package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

public class AddServiceActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etOverview;
    private RatingBar rbRating;
    private EditText etCategory;
    private EditText etLocation;
    private EditText etHours;
    private EditText etPhoneNumber;
    private SharedPreferences sharedpreference;

    public static final String EXTRA_NAME = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_NAME";
    public static final String EXTRA_OVERVIEW = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_OVERVIEW";
    public static final String EXTRA_RATING = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_RATING";
    public static final String EXTRA_CATEGORY = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_CATEGORY";
    public static final String EXTRA_LOCATION = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_LOCATION";
    public static final String EXTRA_HOURS = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_HOURS";
    public static final String EXTRA_PHONE_NUMBER = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_PHONE_NUMBER";

    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        etName = findViewById(R.id.et_service_name);
        etOverview = findViewById(R.id.et_service_overview);
        rbRating = findViewById(R.id.rb_service_rating);
        etCategory = findViewById(R.id.et_service_category);
        etLocation = findViewById(R.id.et_service_location);
        etHours = findViewById(R.id.et_service_hours);
        etPhoneNumber = findViewById(R.id.et_service_phone_number);

        sharedpreference= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Add Service");
    }

    private void saveService() {

        if (!validate()) {
            Toast.makeText(this, "Please insert full", Toast.LENGTH_LONG);
            return;
        }

        String name = etName.getText().toString();
        String overview = etOverview.getText().toString();
        double rating = (double) rbRating.getRating();
        String category = etCategory.getText().toString();
        String location = etLocation.getText().toString();
        String hours = etHours.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_OVERVIEW, overview);
        data.putExtra(EXTRA_RATING, rating);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_HOURS, hours);
        data.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);

        setResult(RESULT_OK, data);
        finish();

    }

    public void onClickSaveService(View view){
        saveService();
    }

    public boolean validate() {
        boolean valid = true;

        String name = etName.getText().toString();
        String overview = etOverview.getText().toString();
        String category = etCategory.getText().toString();
        String location = etLocation.getText().toString();
        String hours = etHours.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        if (name.isEmpty()) {
            etName.setError("enter a valid name");
            valid = false;
        }

        if (overview.isEmpty()) {
            etOverview.setError("enter a valid overview");
            valid = false;
        }

        if (category.isEmpty()) {
            etCategory.setError("enter a valid category");
            valid = false;
        }

        if (location.isEmpty()) {
            etLocation.setError("enter a valid location");
            valid = false;
        }

        if (hours.isEmpty()) {
            etHours.setError("enter a valid location");
            valid = false;
        }

        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError("enter a valid name");
            valid = false;
        }

        return valid;
    }
}
