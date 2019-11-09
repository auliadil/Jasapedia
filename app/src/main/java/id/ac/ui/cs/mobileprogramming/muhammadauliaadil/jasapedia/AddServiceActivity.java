package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;


public class AddServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName, etOverview, etCategory, etLocation, etHours, etPhoneNumber;
    private RatingBar rbRating;
    private SharedPreferences sharedpreference;
    private Spinner phoneTypeSpinner;
    private ProgressBar progressBar;
    private Button btnUploadImage, btnSave;
    private ImageView holderImage;
    final int REQUEST_CODE_GALLERY = 999;
    private Uri selectedUri;
    private String cloudinaryUrl;
    private Intent data;

    private ServiceViewModel serviceViewModel;

    public static final String EXTRA_NAME = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_NAME";
    public static final String EXTRA_OVERVIEW = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_OVERVIEW";
    public static final String EXTRA_RATING = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_RATING";
    public static final String EXTRA_CATEGORY = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_CATEGORY";
    public static final String EXTRA_LOCATION = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_LOCATION";
    public static final String EXTRA_HOURS = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_HOURS";
    public static final String EXTRA_PHONE_NUMBER = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_PHONE_NUMBER";
    public static final String EXTRA_IMAGE_URL = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.EXTRA_IMAGE_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        MediaManager.init(this);

        init();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        setTitle("Add Service");
    }

    public void init() {
        etName = findViewById(R.id.et_service_name);
        etOverview = findViewById(R.id.et_service_overview);
        rbRating = findViewById(R.id.rb_service_rating);
        etCategory = findViewById(R.id.et_service_category);
        etLocation = findViewById(R.id.et_service_location);
        etHours = findViewById(R.id.et_service_hours);
        etPhoneNumber = findViewById(R.id.et_service_phone_number);
        btnSave = findViewById(R.id.save_service);

        // Initialize phone type dropdown spinner.
        phoneTypeSpinner = findViewById(R.id.spinner_type);

        sharedpreference= PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

        //  Upload Image via Cloudenary
        holderImage = findViewById(R.id.image_uploaded);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        btnUploadImage = findViewById(R.id.upload_image);

        btnUploadImage.setOnClickListener(this);
        btnSave.setOnClickListener(this);
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
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
        String category = etCategory.getText().toString();
        String location = etLocation.getText().toString();
        String hours = etHours.getText().toString();
        String phoneNumber = etPhoneNumber.getText().toString();

        data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_OVERVIEW, overview);
        data.putExtra(EXTRA_RATING, rating);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_LOCATION, location);
        data.putExtra(EXTRA_HOURS, hours);
        data.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);

        setResult(RESULT_OK, data);

        MediaManager.get().upload(selectedUri)
                .unsigned("ph8w3u5h")
                .option("resource_type", "image")
                .callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        progressBar.setVisibility(View.VISIBLE);
                        Toast.makeText(AddServiceActivity.this,"Upload has started...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        Toast.makeText(AddServiceActivity.this, "Uploaded Succesfully", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        cloudinaryUrl = resultData.get("secure_url").toString();
                        Log.d("CLOUDINARY", "onSuccess: "+ cloudinaryUrl);

                        data.putExtra(EXTRA_IMAGE_URL, cloudinaryUrl);
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
        // Get android phone contact content provider uri.
        // Uri addContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        // Below uri can avoid java.lang.UnsupportedOperationException: URI: content://com.android.contacts/data/phones error.
        Uri addContactsUri = ContactsContract.Data.CONTENT_URI;

        // Add an empty contact and get the generated id.
        long rowContactId = getRawContactId();

        // Add contact name data.
        String displayName = etName.getText().toString();
        insertContactDisplayName(addContactsUri, rowContactId, displayName);

        // Add contact phone data.
        String phoneNumber = etPhoneNumber.getText().toString();
        String phoneTypeStr = (String)phoneTypeSpinner.getSelectedItem();
        insertContactPhoneNumber(addContactsUri, rowContactId, phoneNumber, phoneTypeStr);

    }

    // This method will only insert an empty data to RawContacts.CONTENT_URI
    // The purpose is to get a system generated raw contact id.
    private long getRawContactId() {
        // Inser an empty contact.
        ContentValues contentValues = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        // Get the newly created contact raw id.
        long ret = ContentUris.parseId(rawContactUri);
        return ret;
    }


    // Insert newly created contact display name.
    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);

        getContentResolver().insert(addContactsUri, contentValues);

    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber, String phoneTypeStr) {
        // Create a ContentValues object.
        ContentValues contentValues = new ContentValues();

        // Each contact must has an id to avoid java.lang.IllegalArgumentException: raw_contact_id is required error.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);

        // Put phone number value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);

        // Calculate phone type by user selection.
        int phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;

        if("home".equalsIgnoreCase(phoneTypeStr))
        {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
        }else if("mobile".equalsIgnoreCase(phoneTypeStr))
        {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        }else if("work".equalsIgnoreCase(phoneTypeStr))
        {
            phoneContactType = ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
        }
        // Put phone type value.
        contentValues.put(ContactsContract.CommonDataKinds.Phone.TYPE, phoneContactType);

        // Insert new contact data into phone contact list.
        getContentResolver().insert(addContactsUri, contentValues);

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
