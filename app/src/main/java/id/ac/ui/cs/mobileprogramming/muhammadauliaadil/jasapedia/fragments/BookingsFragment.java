package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.AddBookingActivity;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters.BookingAdapter;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.BookingViewModel;


public class BookingsFragment extends Fragment {

    private BookingViewModel bookingViewModel;
    private RecyclerView recyclerView;
    private BookingAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new BookingAdapter(getContext());
        recyclerView.setAdapter(adapter);

        bookingViewModel = ViewModelProviders.of(this).get(BookingViewModel.class);
        bookingViewModel.getAllBookings().observe(this, new Observer<List<Booking>>() {
            @Override
            public void onChanged(List<Booking> bookings) {
                adapter.setBookings(bookings);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
