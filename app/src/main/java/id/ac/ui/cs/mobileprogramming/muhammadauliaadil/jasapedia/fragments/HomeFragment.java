package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.activities.AddServiceActivity;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.ServiceAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

import static android.app.Activity.RESULT_OK;
import static id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.activities.AddServiceActivity.*;


public class HomeFragment extends Fragment {

    private ServiceViewModel serviceViewModel;
    private RecyclerView recyclerView;
    private ServiceAdapter adapter;
    public static final int ADD_SERVICE_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Jasapedia");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        FloatingActionButton btnAddService = getView().findViewById(R.id.btn_add_service);
        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(checkNetwork() == false) {
                showAlertNetwork();
            } else {
                Intent intent = new Intent(getContext(), AddServiceActivity.class);
                startActivityForResult(intent, ADD_SERVICE_REQUEST);
            }
            }
        });

        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ServiceAdapter(getContext());
        recyclerView.setAdapter(adapter);

        serviceViewModel = ViewModelProviders.of(this).get(ServiceViewModel.class);
        serviceViewModel.getAllServices().observe(this, new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                adapter.setServices(services);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void showAlertNetwork() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        // Enable button clicked
                        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(true);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        // Cancel button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("You need to enable WIFI to create a service");
        builder.setIcon(R.drawable.ic_error_blue);
        builder.setMessage("By enabling WiFi, you will be able to create a service and save image to cloudinary")
                .setPositiveButton("Enable", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SERVICE_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(EXTRA_NAME);
            String overview = data.getStringExtra(EXTRA_OVERVIEW);
            double rating = data.getDoubleExtra(EXTRA_RATING, 0);
            String category = data.getStringExtra(EXTRA_CATEGORY);
            String location = data.getStringExtra(EXTRA_LOCATION);
            String hours = data.getStringExtra(EXTRA_HOURS);
            String phoneNumber = data.getStringExtra(EXTRA_PHONE_NUMBER);
            String imageUrl = data.getStringExtra(EXTRA_IMAGE_URL);
            Log.d("CLOUDINARY GET EXTRA", "onSuccess: "+ imageUrl);
            int cost = data.getIntExtra(EXTRA_COST, 0);
            String unitCost = data.getStringExtra(EXTRA_UNIT_COST);

            Service service = new Service(name, overview, rating, category, location, hours, phoneNumber, imageUrl, cost, unitCost);
            serviceViewModel.insert(service);

            Toast.makeText(getContext(), "Service saved and contact added to your device", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getContext(), "Service not saved", Toast.LENGTH_SHORT).show();
        }
    }

}
