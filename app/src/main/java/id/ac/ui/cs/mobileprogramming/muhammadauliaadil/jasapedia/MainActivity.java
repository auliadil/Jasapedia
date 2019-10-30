package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.ServiceAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.AccountFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.AddFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.BookingsFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.HomeFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;
    private ServiceViewModel serviceViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new HomeFragment());
        navigationView = findViewById(R.id.bn_main);
//        navigationView.setSelectedItemId(R.id.home_menu);
        navigationView.setOnNavigationItemSelectedListener(this);

    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit();
            return true;
        }
        return false;
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        Intent intent = null;
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.home_menu:
                fragment = new HomeFragment();
                break;
            case R.id.add_menu:
//                intent = new Intent(MainActivity.this, AddServiceActivity.class);
                fragment = new AddFragment();
                break;
            case R.id.bookings_menu:
//                intent = new Intent(MainActivity.this, BookingsActivity.class);
                fragment = new BookingsFragment();
                break;
            case R.id.account_menu:
//                intent = new Intent(MainActivity.this, AccountActivity.class);
                fragment = new AccountFragment();
                break;
        }
        return loadFragment(fragment);
//        if(intent != null) {
//            startActivity(intent);
//            finish();
//            return true;
//        }
//        else return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
