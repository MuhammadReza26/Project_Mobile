package com.example.myapplication.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.DestinationViewModel;
import com.example.myapplication.R;
import com.example.myapplication.Adapter.SearchAdapter;
import com.example.myapplication.Model.Destination;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<Destination> destinationList;
    private DestinationViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.rv_destination2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(requireActivity()).get(DestinationViewModel.class);

        viewModel.getDestinations().observe(getViewLifecycleOwner(), destinations -> {
            destinationList = destinations;
            adapter = new SearchAdapter(getContext(), destinationList);
            recyclerView.setAdapter(adapter);
        });
        recyclerView.setVisibility(View.GONE);
        if (isNetworkAvailable()){
            SearchView searchView = view.findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filter(newText);
                    if (newText.isEmpty()) {
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });
        } else {
            Toast.makeText(getContext(), "Tidak bisa search karena tidak ada jaringan , nyalakan jaringan dulu", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
    private void filter(String query) {
        List<Destination> filteredList = new ArrayList<>();
        for (Destination destination : destinationList) {
            if (destination.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(destination);
            }
        }
        adapter.filterList(filteredList);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}
