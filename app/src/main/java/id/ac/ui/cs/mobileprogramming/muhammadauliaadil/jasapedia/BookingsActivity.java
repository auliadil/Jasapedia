package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookingsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        navigationView = findViewById(R.id.bn_main);
        navigationView.setSelectedItemId(R.id.bookings_menu);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()){
            case R.id.home_menu:
                intent = new Intent(BookingsActivity.this, MainActivity.class);
                break;
            case R.id.add_menu:
                intent = new Intent(BookingsActivity.this, AddServiceActivity.class);
                break;
            case R.id.bookings_menu:
                break;
            case R.id.account_menu:
                intent = new Intent(BookingsActivity.this, AccountActivity.class);
                break;
        }
        if(intent != null) {
            startActivity(intent);
            finish();
            return true;
        }
        else return false;
    }
}
