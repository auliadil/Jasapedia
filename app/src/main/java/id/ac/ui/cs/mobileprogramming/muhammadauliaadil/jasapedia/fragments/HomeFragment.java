package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.content.Context;
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

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.MainActivity;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.ServiceAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.ServiceViewModel;


public class HomeFragment extends Fragment {

    private ServiceViewModel serviceViewModel;
    private RecyclerView recyclerView;

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

}
