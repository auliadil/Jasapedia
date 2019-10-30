package id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.R;
import id.ac.ui.cs.mobileprogramming.muhammadauliaadil.jasapedia.models.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    private List<Service> services = new ArrayList<>();

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_services, parent, false);
        return new ServiceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        Service currentService = services.get(position);
        holder.tvServiceName.setText(currentService.getName());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void setServices(List<Service> services) {
        this.services = services;
        notifyDataSetChanged();
    }

    class ServiceHolder extends RecyclerView.ViewHolder {
        private TextView tvServiceName;
        private ImageView ivServiceImage;

        public ServiceHolder(View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tv_service_name);
            ivServiceImage = itemView.findViewById(R.id.iv_service_image);

        }
    }
}
