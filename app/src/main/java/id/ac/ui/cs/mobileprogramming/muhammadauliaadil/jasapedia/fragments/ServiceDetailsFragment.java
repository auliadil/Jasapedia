package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.AddBookingActivity;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

public class ServiceDetailsFragment extends Fragment {
    private Service service;

    public ServiceDetailsFragment(Service service) {
        this.service = service;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Service Details");
        return inflater.inflate(R.layout.fragment_service_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setService(service);
    }

    private void setService(final Service service){
        TextView nameText = getView().findViewById(R.id.details_name);
        nameText.setText(service.getName());
        TextView categoryText = getView().findViewById(R.id.details_category);
        categoryText.setText(service.getCategory());
        RatingBar rating = getView().findViewById(R.id.details_rating);
        rating.setRating((float) service.getRating());
        TextView ratingNumberText = getView().findViewById(R.id.details_rating_number);
        ratingNumberText.setText(String.valueOf(service.getRating()));
        TextView overviewText = getView().findViewById(R.id.details_overview);
        overviewText.setText(service.getOverview());
        TextView locationText = getView().findViewById(R.id.details_location);
        locationText.setText(service.getLocation());
        TextView hoursText = getView().findViewById(R.id.details_working_hours);
        hoursText.setText(service.getHours());
        TextView phoneNumberText = getView().findViewById(R.id.details_phone_number);
        phoneNumberText.setText(service.getPhoneNumber());

        ImageView image = getView().findViewById(R.id.details_image);

        Button btnBookService = getView().findViewById(R.id.btn_book_service);
        btnBookService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddBookingActivity.class);
                String serviceId = String.valueOf(service.getId());
                String serviceName = service.getName();
                intent.putExtra("SERVICE_ID", serviceId);
                intent.putExtra("SERVICE_NAME", serviceName);
                startActivity(intent);
            }
        });
//        Glide.with(this)
//                .asBitmap()
//                .load(service.getImageUrl())
//                .into(image);
    }
}
