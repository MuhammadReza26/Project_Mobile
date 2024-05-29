package com.example.myapplication.Adapter;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.Destination;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder>{

    private static List<Destination> destinationList;
    private Context context;
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String KEY_USERNAME = "username";
    private DatabaseHelper myDB;
    private int userId;

    public BookmarkAdapter(Context context, List<Destination> destinationList) {
        this.context = context;
        this.destinationList = destinationList;
    }
    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BookmarkAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        Destination destination = destinationList.get(position);
        holder.tvName.setText(destination.getName());
        holder.tvCountry.setText(destination.getCountry());
        Picasso.get().load(destination.getImage()).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return destinationList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage, ivBookmark;
        TextView tvName, tvCountry, tvContinent;
        LinearLayout ll_item;
        DatabaseHelper myDB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_destination);
            tvName = itemView.findViewById(R.id.tv_name);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            ll_item = itemView.findViewById(R.id.ll_item);
            ivBookmark = itemView.findViewById(R.id.ivBookmark);
            myDB = new DatabaseHelper(itemView.getContext());

            ivBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkAvailable()) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            new AlertDialog.Builder(itemView.getContext())
                                    .setTitle("Hapus Bookmark")
                                    .setMessage("Apakah Anda yakin ingin menghapus bookmark ini?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Delete the bookmark
                                            Destination destination = destinationList.get(position);
                                            SharedPreferences sharedPreferences = itemView.getContext().getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                                            String username = sharedPreferences.getString(KEY_USERNAME, null);
                                            int userId = myDB.getUserId(username);
                                            int destinationId = destination.getId();
                                            myDB.deleteBookmark(destinationId, userId);
                                            destinationList.remove(position);
                                            notifyDataSetChanged();
                                            Toast.makeText(itemView.getContext(), "Bookmark berhasil dihapus", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("No", null).show();
                        }
                    } else {
                        Toast.makeText(itemView.getContext(), "Tidak bisa dihapus karena jaringan tidak ada", Toast.LENGTH_SHORT).show();
                    }
                }
            });

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
