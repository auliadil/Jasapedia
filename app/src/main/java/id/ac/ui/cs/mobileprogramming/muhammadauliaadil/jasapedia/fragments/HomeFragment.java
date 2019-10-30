package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.AddServiceActivity;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.MainActivity;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.ServiceAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment {

    private ServiceViewModel serviceViewModel;
    private RecyclerView recyclerView;
    public static final int ADD_SERVICE_REQUEST = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        FloatingActionButton btnAddService = getView().findViewById(R.id.btn_add_service);
        btnAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddServiceActivity.class);
                startActivityForResult(intent, ADD_SERVICE_REQUEST);
            }
        });

        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        final ServiceAdapter adapter = new ServiceAdapter();
        recyclerView.setAdapter(adapter);

        serviceViewModel = ViewModelProviders.of(this).get(ServiceViewModel.class);
        serviceViewModel.getAllServices().observe(this, new Observer<List<Service>>() {
            @Override
            public void onChanged(List<Service> services) {
                adapter.setServices(services);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_SERVICE_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddServiceActivity.EXTRA_NAME);
            String overview = data.getStringExtra(AddServiceActivity.EXTRA_OVERVIEW);
            double rating = 0;
            String category = data.getStringExtra(AddServiceActivity.EXTRA_CATEGORY);
            String location = data.getStringExtra(AddServiceActivity.EXTRA_LOCATION);
            String hours = data.getStringExtra(AddServiceActivity.EXTRA_HOURS);
            String phoneNumber = data.getStringExtra(AddServiceActivity.EXTRA_PHONE_NUMBER);

            Service service = new Service(name, overview, rating, category, location, hours, phoneNumber);
            serviceViewModel.insert(service);

            Toast.makeText(getContext(), "Service saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Service not saved", Toast.LENGTH_SHORT).show();
        }
    }

}
