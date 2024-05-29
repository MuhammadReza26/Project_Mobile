package com.example.myapplication.Adapter;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.Model.Destination;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private static List<Destination> destinationList;
    private Context context;

    public SearchAdapter(Context context, List<Destination> destinationList) {
        this.context = context;
        this.destinationList = destinationList;
    }


    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.tvName.setText(destination.getName());
        holder.tvCountry.setText(destination.getCountry());
        Picasso.get().load(destination.getImage()).into(holder.ivImage);
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (isNetworkAvailable()) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("id", destination.getId());
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Nyalakan Jaringan internet untuk ke detail ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }

    public void filterList(List<Destination> filteredList) {
        destinationList = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvName, tvCountry, tvContinent;
        LinearLayout ll_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_destination);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            ll_item = itemView.findViewById(R.id.ll_item);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
