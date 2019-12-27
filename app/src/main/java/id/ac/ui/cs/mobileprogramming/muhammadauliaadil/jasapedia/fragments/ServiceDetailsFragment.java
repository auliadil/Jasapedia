package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.activities.AddBookingActivity;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.viewmodels.BookingViewModel;

public class ServiceDetailsFragment extends Fragment {
    private Service service;
    private BookingViewModel bookingViewModel;
    public static final int ADD_BOOKING_REQUEST = 1;
    private TextView nameText, categoryText, ratingNumberText, overviewText, locationText, hoursText, phoneNumberText, costText, unitCostText;
    private RatingBar rating;
    private ImageView image;
    private Button btnOpenMap;

    public ServiceDetailsFragment(Service service) {
        this.service = service;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

        nameText = getView().findViewById(R.id.details_name);
        nameText.setText(service.getName());
        categoryText = getView().findViewById(R.id.details_category);
        categoryText.setText(service.getCategory());
        rating = getView().findViewById(R.id.details_rating);
        rating.setRating((float) service.getRating());
        ratingNumberText = getView().findViewById(R.id.details_rating_number);
        ratingNumberText.setText(String.valueOf(service.getRating()));
        overviewText = getView().findViewById(R.id.details_overview);
        overviewText.setText(service.getOverview());
        locationText = getView().findViewById(R.id.details_location);
        locationText.setText(service.getLocation());
        hoursText = getView().findViewById(R.id.details_working_hours);
        hoursText.setText(service.getServiceHours());
        phoneNumberText = getView().findViewById(R.id.details_phone_number);
        phoneNumberText.setText(service.getPhoneNumber());
        costText = getView().findViewById(R.id.details_cost);
        costText.setText("Rp " + service.getCost());
        unitCostText = getView().findViewById(R.id.details_unit_cost);
        unitCostText.setText("Per " + service.getUnitCost());
        image = getView().findViewById(R.id.details_image);
//        btnOpenMap = getView().findViewById(R.id.open_map);
//        btnOpenMap.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//            Intent intent = new Intent(getContext(), MapActivity.class);
//            startActivity(intent);
//            }
//        });

        String text = service.getImageUrl();

        if(text != null)  Log.d("cloudinaryUrl", text);

        Glide.with(this)
                .asBitmap()
                .load(service.getImageUrl())
                .fitCenter()
                .into(image);

        Log.d("IdDetails", String.valueOf(service.getId()));

        Button btnBookService = getView().findViewById(R.id.btn_book_service);
        btnBookService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddBookingActivity.class);
                String serviceId = String.valueOf(service.getId());
                String serviceName = service.getName();
                int serviceCost = service.getCost();
                String serviceUnitCost = service.getUnitCost();
                intent.putExtra("SERVICE_ID", serviceId);
                intent.putExtra("SERVICE_NAME", serviceName);
                intent.putExtra("SERVICE_COST", serviceCost);
                intent.putExtra("SERVICE_UNIT_COST", serviceUnitCost);
                startActivity(intent);
            }
        });

//        Text btnOpenMap = getView().findViewById(R.id.open_map);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.d("ActivityResult", "muncul");
//
//        if (resultCode == RESULT_OK) {
//            int serviceId = Integer.parseInt(data.getStringExtra(AddBookingActivity.BOOKING_SERVICE_ID));
//            String note = data.getStringExtra(AddBookingActivity.BOOKING_NOTE);
//            String date = data.getStringExtra(AddBookingActivity.BOOKING_DATE);
//            String time = data.getStringExtra(AddBookingActivity.BOOKING_TIME);
//
//            Booking booking = new Booking(serviceId, note, date, time);
//            bookingViewModel.insert(booking);
//
//            Toast.makeText(getContext(), "Booking saved", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "Booking not saved", Toast.LENGTH_SHORT).show();
//        }
//    }

}
