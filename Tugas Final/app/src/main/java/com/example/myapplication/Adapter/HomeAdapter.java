package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.Destination;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private static List<Destination> destinationList;
    private Context context;
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String KEY_USERNAME = "username";
    private DatabaseHelper myDB;

    public HomeAdapter(Context context, List<Destination> destinationList) {
        this.context = context;
        this.destinationList = destinationList;
    }
    public HomeAdapter(Context context, List<Destination> destinationList, DatabaseHelper myDB) {
        this.context = context;
        this.destinationList = destinationList;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        holder.ivBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        myDB = new DatabaseHelper(context);
                        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
                        String username = sharedPreferences.getString(KEY_USERNAME, null);
                        Destination destination = destinationList.get(position);
                        int id = myDB.getUserId(username);
                        Toast.makeText(context, "id" + username + "desid" + destination.getId(), Toast.LENGTH_SHORT).show();
                        myDB.insertBookmark(destination.getId(), id);
                    }
                } else {
                    Toast.makeText(context, "Tidak bisa tambah bookmark karena jaringan tidak ada ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    @Override
    public int getItemCount() {
        return destinationList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage, ivBookmark;
        TextView tvName, tvCountry, tvContinent;
        LinearLayout ll_item, ll_progress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_destination);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            ll_item = itemView.findViewById(R.id.ll_item);
            ivBookmark = itemView.findViewById(R.id.ivBookmark);
            ll_progress = itemView.findViewById(R.id.ll_progress);
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
