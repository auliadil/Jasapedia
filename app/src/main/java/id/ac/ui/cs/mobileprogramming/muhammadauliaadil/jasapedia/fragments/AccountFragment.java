package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;

public class AccountFragment extends Fragment implements View.OnClickListener {

    private TextView name, address, email, phoneNumber;
    private Button buttonStart, buttonStop;
    private MyService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
//        name = getView().findViewById(R.id.user_name);
//        address = getView().findViewById(R.id.user_address);
//        email = getView().findViewById(R.id.user_email);
//        phoneNumber = getView().findViewById(R.id.user_phone_number);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getting buttons from xml
        buttonStart = getActivity().findViewById(R.id.btn_start);
        buttonStop = getActivity().findViewById(R.id.btn_stop);

        //attaching onclicklistener to buttons
        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonStart) {
            //start the service here
            service.startService(new Intent(getActivity(), MyService.class));
        } else if (view == buttonStop) {
            //stop the service here
            service.stopService(new Intent(getActivity(), MyService.class));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

}
