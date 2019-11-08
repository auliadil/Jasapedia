package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
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

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

public class AddServiceActivity extends AppCompatActivity {

    private EditText etName, etOverview, etCategory, etLocation, etHours, etPhoneNumber;
    private RatingBar rbRating;
    private SharedPreferences sharedpreference;
    private Spinner phoneTypeSpinner;

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

        // Initialize phone type dropdown spinner.
        phoneTypeSpinner = (MaterialSpinner)findViewById(R.id.spinner_type);
        String phoneTypeArr[] = {"Mobile", "Home", "Work"};
        ArrayAdapter<String> phoneTypeSpinnerAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, phoneTypeArr);
        phoneTypeSpinner.setAdapter(phoneTypeSpinnerAdaptor);

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

        saveContact();
        finish();

    }

    public void onClickSaveService(View view){
        saveService();
    }

    public void saveContact() {
//        // Get query phone contacts cursor object.
//        Uri readContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        Cursor cursor = getContentResolver().query(readContactsUri, null, null, null, null);
//
//        // Inser an empty contact.
//        ContentValues contentValues = new ContentValues();
//        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
//        // Get the newly created contact raw id.
//        long ret = ContentUris.parseId(rawContactUri);

        // Get android phone contact content provider uri.
        //Uri addContactsUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
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
    private long getRawContactId()
    {
        // Inser an empty contact.
        ContentValues contentValues = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);
        // Get the newly created contact raw id.
        long ret = ContentUris.parseId(rawContactUri);
        return ret;
    }


    // Insert newly created contact display name.
    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Each contact must has an mime type to avoid java.lang.IllegalArgumentException: mimetype is required error.
        contentValues.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);

        // Put contact display name value.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);

        getContentResolver().insert(addContactsUri, contentValues);

    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber, String phoneTypeStr)
    {
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

//    public void saveContact() {
//        //    Contact
//        ArrayList ops = new ArrayList();
//        int rawContactInsertIndex = ops.size();
//
//        ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                .build());
//        ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID,rawContactInsertIndex)
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, etName.getText().toString()) // Name of the person
//                .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, "One of services in Jasapedia") // Name of the person
//                .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, etName.getText().toString()) // Name of the person
//                .build());
//
//        ContentProviderResult[] res = new ContentProviderResult[0];
//        try {
//            res = getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
//        } catch (OperationApplicationException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//
//        if (res!=null && res[0]!=null) {
//            newContactUri = res[0].uri;
//            //02-20 22:21:09 URI added contact:content://com.android.contacts/raw_contacts/612
//            Log.d("URIStatus: ", "URI added contact:"+ newContactUri);
//        }
//        else Log.e("URIStatus: ", "Contact not added.");
//    }

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
