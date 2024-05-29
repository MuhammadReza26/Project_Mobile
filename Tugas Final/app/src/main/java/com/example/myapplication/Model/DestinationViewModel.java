package com.example.myapplication.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.DatabaseHelperUtils;
import com.example.myapplication.Network.ApiService;
import com.example.myapplication.Network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DestinationViewModel extends ViewModel {
    private final MutableLiveData<List<Destination>> destinations = new MutableLiveData<>();
    private final MutableLiveData<List<Destination>> bookmarkedDestinations = new MutableLiveData<>();
    private DatabaseHelper databaseHelper;

    public LiveData<List<Destination>> getDestinations() {
        return destinations;
    }
    public LiveData<List<Destination>> getBookmarkedDestinations() {
        return bookmarkedDestinations;
    }

    public void fetchDestinations() {
        ApiService apiService = RetrofitClient.getRetrofitInstance();
        Call<List<Destination>> call = apiService.getDestinations();
        call.enqueue(new Callback<List<Destination>>() {
            @Override
            public void onResponse(Call<List<Destination>> call, Response<List<Destination>> response) {
                if (response.isSuccessful()) {
                    destinations.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Destination>> call, Throwable t) {
            }
        });
    }
    public void fetchDestinationsSorted() {
        ApiService apiService = RetrofitClient.getRetrofitInstance();
        Call<List<Destination>> call = apiService.getDestinationsSorted();
        call.enqueue(new Callback<List<Destination>>() {
            @Override
            public void onResponse(Call<List<Destination>> call, Response<List<Destination>> response) {
                if (response.isSuccessful()) {
                    destinations.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Destination>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    public void fetchDestinationsLimit(int limit) {
        ApiService apiService = RetrofitClient.getRetrofitInstance();
        Call<List<Destination>> call = apiService.getDestinationsLimit(limit);
        call.enqueue(new Callback<List<Destination>>() {
            @Override
            public void onResponse(Call<List<Destination>> call, Response<List<Destination>> response) {
                if (response.isSuccessful()) {
                    destinations.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Destination>> call, Throwable t) {
                // Handle failure
            }
        });
    }
}