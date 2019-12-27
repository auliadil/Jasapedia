package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;

public class AccountFragment extends Fragment {

    TextView name, address, email, phoneNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        name = getView().findViewById(R.id.user_name);
        address = getView().findViewById(R.id.user_address);
        email = getView().findViewById(R.id.user_email);
        phoneNumber = getView().findViewById(R.id.user_phone_number);

        return inflater.inflate(R.layout.fragment_account, container, false);
    }

}
