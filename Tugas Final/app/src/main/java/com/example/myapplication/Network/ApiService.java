package com.example.myapplication.Network;

import com.example.myapplication.Model.Destination;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("v1/destinations")
    Call<List<Destination>> getDestinations();

    @GET("v1/destinations?sort=name&order=dec")
    Call<List<Destination>> getDestinationsSorted();

    @GET("v1/destinations")
    Call<List<Destination>> getDestinationsLimit(@Query("limit") int limit);


    @GET("v1/destinations/{id}")
    Call<Destination> getDestinationById(@Path("id") int id);

}
