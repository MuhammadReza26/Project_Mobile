package com.example.myapplication.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Adapter.BookmarkAdapter;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Model.Destination;
import com.example.myapplication.Network.ApiService;
import com.example.myapplication.Network.RetrofitClient;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookmarkFragment extends Fragment {
    private List<Destination> destinationList;
    private RecyclerView recyclerView;
    private BookmarkAdapter adapter;
    private DatabaseHelper myDB;
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String KEY_USERNAME = "username";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        recyclerView = view.findViewById(R.id.rv_destination3);
        destinationList = new ArrayList<>();
        myDB = new DatabaseHelper(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookmarkAdapter(getContext(), destinationList);
        recyclerView.setAdapter(adapter);
        loadBookmarks();
        return view;
    }
    private void loadBookmarks() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME, null);
        int id  = myDB.getUserId(username);
        destinationList.clear();
        ApiService apiService = RetrofitClient.getRetrofitInstance();
        for (int destinationId : myDB.getDestinationByUserIdBookmark(id)) {
            Call<Destination> call = apiService.getDestinationById(destinationId);
            call.enqueue(new Callback<Destination>() {
                @Override
                public void onResponse(Call<Destination> call, Response<Destination> response) {
                    if (response.isSuccessful()) {
                        Destination destination = response.body();
                        destinationList.add(0, destination);
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<Destination> call, Throwable t) {
                }
            });
        }
    }
}