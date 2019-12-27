package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.ListCategory;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.YelpApi;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Category;
import me.abhinay.input.CurrencyEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static me.abhinay.input.CurrencySymbols.INDONESIA;


public class AddServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etOverview, etLocation, etHours, etUnitCost;
//    private @BindView(etPhoneNumber)
    @BindView(R.id.et_service_phone_number) EditText etPhoneNumber;
    private CurrencyEditText etCost;
    private RatingBar rbRating;
    private SharedPreferences sharedpreference;
    private SearchableSpinner categorySpinner, phoneNumberTypeSpinner, unitCostSpinner;
    private Button btnUploadImage, btnSave;
    private ImageView holderImage;
    final int REQUEST_CODE_GALLERY = 999;
    final int REQUEST_CODE_CONTACT = 888;
    private Uri selectedUri;
    private Intent data;
    private TextView textViewResult;
    private String[] cloudinaryUrl;

    public static final String EXTRA_NAME = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_NAME";
    public static final String EXTRA_OVERVIEW = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_OVERVIEW";
    public static final String EXTRA_RATING = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_RATING";
    public static final String EXTRA_CATEGORY = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_CATEGORY";
    public static final String EXTRA_LOCATION = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_LOCATION";
    public static final String EXTRA_HOURS = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_HOURS";
    public static final String EXTRA_PHONE_NUMBER = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_PHONE_NUMBER";
    public static final String EXTRA_IMAGE_URL = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_IMAGE_URL";
    public static final String EXTRA_COST = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_COST";
    public static final String EXTRA_UNIT_COST = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_UNIT_COST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        ButterKnife.bind(this);
        init();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Add Service");
    }

    public void init() {
        etName = findViewById(R.id.et_service_name);
        etOverview = findViewById(R.id.et_service_overview);
        rbRating = findViewById(R.id.rb_service_rating);
//        etCategory = findViewById(R.id.et_service_category);
        etLocation = findViewById(R.id.et_service_location);
        etHours = findViewById(R.id.et_service_hours);
//        etPhoneNumber = findViewById(R.id.et_service_phone_number);
        etCost = findViewById(R.id.et_cost);

        etCost.setCurrency(INDONESIA);
        etCost.setDelimiter(false);
        etCost.setSpacing(false);
        etCost.setDecimals(false);
        //Make sure that Decimals is set as false if a custom Separator is used
        etCost.setSeparator(".");
        btnSave = findViewById(R.id.save_service);

        categorySpinner = findViewById(R.id.spinner_category);
        String title = getResources().getString(R.string.category_title);
        categorySpinner.setTitle(title);
        categorySpinner.setPositiveButton("OK");

        phoneNumberTypeSpinner = findViewById(R.id.spinner_type);
        String titlePhone = getResources().getString(R.string.phone_title);
        phoneNumberTypeSpinner.setTitle(titlePhone);
        phoneNumberTypeSpinner.setPositiveButton("OK");

        unitCostSpinner = findViewById(R.id.spinner_unit_cost);
        String titleUnit = getResources().getString(R.string.unit_cost_title);
        unitCostSpinner.setTitle(titleUnit);
        unitCostSpinner.setPositiveButton("OK");

        sharedpreference= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

        //  Upload Image via Cloudenary
        holderImage = findViewById(R.id.image_uploaded);

        btnUploadImage = findViewById(R.id.upload_image);

        btnUploadImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        // Initialize Retrofit
        textViewResult = findViewById(R.id.text_view_result);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YelpApi yelpAPI = retrofit.create(YelpApi.class);
        Call<ListCategory> call = yelpAPI.getCategories();
        call.enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(Call<ListCategory> call, Response<ListCategory> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                ListCategory listCategory = response.body();
                List<String> categories = new ArrayList<String>();
                for (Category category : listCategory.getCategories()) {
                    categories.add(category.getTitle());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ListCategory> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == btnUploadImage) {
            Log.d("UploadImage", "button upload clicked");
            ActivityCompat.requestPermissions(
                    AddServiceActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );
        }
        if (v == btnSave) {
            saveService();
        }

        if (v == etPhoneNumber) {
            ActivityCompat.requestPermissions(
                    AddServiceActivity.this,
                    new String[]{Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CODE_CONTACT
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
                Toast.makeText(getApplicationContext(), "Permission has been granted!", Toast.LENGTH_SHORT).show();
            }
            else {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("You need to allow access to permission");
                builder.setIcon(R.drawable.ic_error_blue);
                builder.setMessage("By giving permission, you can select and upload image on your device to be saved as the image of the service you are currently creating")
                        .setPositiveButton("OK", dialogClickListener).show();
            }
            return;
        } else if (requestCode == REQUEST_CODE_CONTACT) {
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Permission has been granted!", Toast.LENGTH_SHORT).show();
            }
            else {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("You need to allow access to permission");
                builder.setIcon(R.drawable.ic_error_blue);
                builder.setMessage("By giving permission, you can add this service phone number to your contacts")
                        .setPositiveButton("OK", dialogClickListener).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            selectedUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holderImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveService() {

        if (!validate()) {
            Toast.makeText(this, "Please insert full", Toast.LENGTH_LONG);
            return;
        }

        String name = etName.getText().toString();
        String overview = etOverview.getText().toString();
        double rating = (double) rbRating.getRating();
        String category = (String) categorySpinner.getSelectedItem();
        String location = etLocation.getText().toString();
        String hours = etHours.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        int cost = etCost.getCleanIntValue();
        String unitCost = (String) unitCostSpinner.getSelectedItem();
        cloudinaryUrl = new String[1];

        data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_OVERVIEW, overview);
        data.putExtra(EXTRA_RATING, rating);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_HOURS, hours);
        data.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
        data.putExtra(EXTRA_COST, cost);
        data.putExtra(EXTRA_UNIT_COST, unitCost);

        MediaManager.get().upload(selectedUri)
            .unsigned("ph8w3u5h")
            .option("resource_type", "image")
            .callback(new UploadCallback() {

                @Override
                public void onStart(String requestId) {

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    cloudinaryUrl[0] = resultData.get("secure_url").toString();
                    Log.d("CLOUDINARY", "onSuccess: "+ cloudinaryUrl[0]);
                    data.putExtra(EXTRA_IMAGE_URL, cloudinaryUrl[0]);
                    Log.d("CLOUDINARY EXTRA", "onSuccess: "+ cloudinaryUrl[0]);
                    setResult(RESULT_OK, data);
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Toast.makeText(AddServiceActivity.this,"Upload Error", Toast.LENGTH_SHORT).show();
                    Log.v("ERROR!!", error.getDescription());

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                }
            }).dispatch();

        saveContact();
        finish();
    }

    public void saveContact() {
        Uri addContactsUri = ContactsContract.Data.CONTENT_URI;
        long rowContactId = getRawContactId();
        String displayName = etName.getText().toString();
        insertContactDisplayName(addContactsUri, rowContactId, displayName);
        String phoneNumber = etPhoneNumber.getText().toString();
        String phoneTypeStr = (String) phoneNumberTypeSpinner.getSelectedItem();
        insertContactPhoneNumber(addContactsUri, rowContactId, phoneNumber, phoneTypeStr);
    }

    private long getRawContactId() {
        ContentValues contentValues = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        long ret = ContentUris.parseId(rawContactUri);
        return ret;
    }

    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);
        getContentResolver().insert(addContactsUri, contentValues);
    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber, String phoneTypeStr) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        int phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;

        if("home".equalsIgnoreCase(phoneTypeStr)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
        } else if("mobile".equalsIgnoreCase(phoneTypeStr)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        } else if("work".equalsIgnoreCase(phoneTypeStr)) {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
        }
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType);
        getContentResolver().insert(addContactsUri, contentValues);

    }

    public boolean validate() {
        boolean valid = true;

        String name = etName.getText().toString();
        String overview = etOverview.getText().toString();
        String location = etLocation.getText().toString();
        String hours = etHours.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();
        String cost = etCost.getText().toString();

        if (name.isEmpty()) {
            etName.setError("enter a valid name");
            valid = false;
        }

        if (overview.isEmpty()) {
            etOverview.setError("enter a valid overview");
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

        if (cost.isEmpty()) {
            etPhoneNumber.setError("enter a valid cost");
            valid = false;
        }

        return valid;
    }
}
