package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.BindView;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private SharedPreferences sharedpreference;

    public static final String USER_NAME = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.USER_NAME";
    public static final String USER_ADDRESS = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.USER_ADDRESS";
    public static final String USER_EMAIL = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.USER_EMAIL";
    public static final String USER_MOBILE = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.USER_MOBILE";
    public static final String USER_PASSWORD = "id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.USER_PASSWORD";

    @BindView(R.id.input_name) EditText nameText;
    @BindView(R.id.input_address) EditText addressText;
    @BindView(R.id.input_email) EditText emailText;
    @BindView(R.id.input_mobile) EditText mobileText;
    @BindView(R.id.input_password) EditText passwordText;
    @BindView(R.id.input_reEnterPassword) EditText reEnterPasswordText;
    @BindView(R.id.btn_signup) Button signupButton;
    @BindView(R.id.link_login) TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        Intent data = new Intent();
        data.putExtra(USER_NAME, name);
        data.putExtra(USER_ADDRESS, address);
        data.putExtra(USER_EMAIL, email);
        data.putExtra(USER_MOBILE, mobile);
        data.putExtra(USER_PASSWORD, password);

        setResult(RESULT_OK, data);

        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    onSignupSuccess();
                    progressDialog.dismiss();
                }
            }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (address.isEmpty()) {
            addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty()) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
}
