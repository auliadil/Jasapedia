package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Booking;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder> {

    private List<Booking> bookings = new ArrayList<>();
    private Context context;

    public BookingAdapter(Context context) {
        this.context = context;
    }

    public BookingAdapter(Context context, List<Booking> bookings) {
        this.bookings = bookings;
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bookings, parent, false);
        return new BookingHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingHolder holder, int position) {
        Booking currentBooking = bookings.get(position);
        holder.tvServiceName.setText(currentBooking.getServiceName());
        holder.tvBookingNote.setText(currentBooking.getNote());
        holder.tvBookingDate.setText(currentBooking.getDate());
        holder.tvBookingTime.setText(currentBooking.getTime());
        holder.tvBookingFee.setText(currentBooking.getBookingFee());
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
        notifyDataSetChanged();
    }

    class BookingHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceName, tvBookingNote, tvBookingDate, tvBookingTime, tvBookingFee;
        private ImageView ivBookingImage;
        private RelativeLayout parentLayout;

        public BookingHolder(View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
            tvBookingNote = itemView.findViewById(R.id.tv_booking_note);
            ivBookingImage = itemView.findViewById(R.id.iv_booking_image);
            tvBookingDate = itemView.findViewById(R.id.tv_booking_date);
            tvBookingTime = itemView.findViewById(R.id.tv_booking_time);
            tvBookingFee = itemView.findViewById(R.id.tv_booking_fee);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
