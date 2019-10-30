package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddServiceActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etOverview;
    private EditText etRating;
    private Spinner etCategory;
    private EditText etLocation;
    private EditText etHours;
    private EditText etPhoneNumber;

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
        etRating = findViewById(R.id.et_service_rating);
        etLocation = findViewById(R.id.et_service_location);
        etHours = findViewById(R.id.et_service_hours);
        etPhoneNumber = findViewById(R.id.et_service_phone_number);

        etCategory = (Spinner) findViewById(R.id.et_service_category);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.et_service_category, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        etCategory.setAdapter(adapter);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Service");
    }

    private void saveService() {
        String name = etName.getText().toString();
        String overview = etOverview.getText().toString();
        double rating = 0;
        if(etRating.getText() != null) {
            rating = Double.parseDouble(etRating.getText().toString());
        }
        String category = etCategory.getContext().getText().toString();
        String location = etLocation.getText().toString();
        String hours = etHours.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        if (name.trim().isEmpty() || overview.isEmpty() || rating == 0 || location.isEmpty() || hours.isEmpty() || phoneNumber.isEmpty() ) {
            Toast.makeText(this, "Please insert full", Toast.LENGTH_LONG);
            return;
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_service_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_service:
                saveService();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    }
}
