package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewBookingFragment extends Fragment {

    Service service;
    EditText etBookingName;

    public NewBookingFragment(Service service) {
        // Required empty public constructor
        this.service = service;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        etBookingName = getView().findViewById(R.id.et_booking_name);
        etBookingName.setText(service.getName());
        return inflater.inflate(R.layout.fragment_add_booking, container, false);
    }

}
