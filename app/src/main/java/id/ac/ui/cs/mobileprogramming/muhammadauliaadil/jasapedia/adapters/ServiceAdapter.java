package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.fragments.ServiceDetailsFragment;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    private List<Service> services = new ArrayList<>();
    private Context context;

    public ServiceAdapter(Context context) {
        this.context = context;
    }

    public ServiceAdapter(Context context, List<Service> services) {
        this.services = services;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_services, parent, false);
        return new ServiceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, final int position) {
        Service currentService = services.get(position);
        holder.tvServiceName.setText(currentService.getName());
        holder.tvServiceOverview.setText(currentService.getOverview());
        holder.serviceRating.setRating((float) currentService.getRating());
        Glide.with(context)
                .asBitmap()
                .load(currentService.getImageUrl())
                .fitCenter()
                .into(holder.ivServiceImage);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailsFragment fragment = new ServiceDetailsFragment(services.get(position));
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fl_container, fragment)
                        .addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    class ServiceHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceName;
        private TextView tvServiceOverview;
        private RatingBar serviceRating;
        private ImageView ivServiceImage;
        private RelativeLayout parentLayout;

        public ServiceHolder(View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
            ivServiceImage = itemView.findViewById(R.id.iv_service_image);
            tvServiceOverview = itemView.findViewById(R.id.tv_service_overview);
            serviceRating = itemView.findViewById(R.id.service_rating);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
