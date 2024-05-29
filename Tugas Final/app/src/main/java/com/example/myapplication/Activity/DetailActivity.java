package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Network.ApiService;
import com.example.myapplication.Model.Destination;
import com.example.myapplication.R;
import com.example.myapplication.Network.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    TextView tvName, tvCountry, tvContinent, tvDescription, tvPopulation, tvCurrency, tvLanguage, tvBestTimeToVisit, tvTopAttractions, tvLocalDishes, tvActivities;
    ImageView ivImage;
    ImageButton ib_back, ib_bookmark;
    ApiService apiService;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        progressBar = findViewById(R.id.progressBar);
        ivImage = findViewById(R.id.ivImage);
        tvName = findViewById(R.id.tvName);
        tvCountry = findViewById(R.id.tvCountry);
        tvContinent = findViewById(R.id.tvContinent);
        tvDescription = findViewById(R.id.tvDescription);
        tvPopulation = findViewById(R.id.tvPopulation);
        tvCurrency = findViewById(R.id.tvCurrency);
        tvLanguage = findViewById(R.id.tvLanguage);
        tvBestTimeToVisit = findViewById(R.id.tvBestTimeToVisit);
        tvTopAttractions = findViewById(R.id.tvTopAttractions);
        tvLocalDishes = findViewById(R.id.tvLocalDishes);
        tvActivities = findViewById(R.id.tvActivities);
        ib_back = findViewById(R.id.ib_Back);




        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                        ivImage.setVisibility(View.GONE);
                        tvName.setVisibility(View.GONE);
                        tvContinent.setVisibility(View.GONE);
                        tvCountry.setVisibility(View.GONE);
                        tvDescription.setVisibility(View.GONE);
                        tvPopulation.setVisibility(View.GONE);
                        tvCurrency.setVisibility(View.GONE);
                        tvLanguage.setVisibility(View.GONE);
                        tvBestTimeToVisit.setVisibility(View.GONE);
                        tvTopAttractions.setVisibility(View.GONE);
                        tvLocalDishes.setVisibility(View.GONE);
                        tvActivities.setVisibility(View.GONE);
                        ib_back.setVisibility(View.GONE);
                    }
                });

                // Tunggu selama 1 detik
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Sembunyikan progress bar
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        ivImage.setVisibility(View.VISIBLE);
                        tvName.setVisibility(View.VISIBLE);
                        tvContinent.setVisibility(View.VISIBLE);
                        tvCountry.setVisibility(View.VISIBLE);
                        tvDescription.setVisibility(View.VISIBLE);
                        tvPopulation.setVisibility(View.VISIBLE);
                        tvCurrency.setVisibility(View.VISIBLE);
                        tvLanguage.setVisibility(View.VISIBLE);
                        tvBestTimeToVisit.setVisibility(View.VISIBLE);
                        tvTopAttractions.setVisibility(View.VISIBLE);
                        tvLocalDishes.setVisibility(View.VISIBLE);
                        tvActivities.setVisibility(View.VISIBLE);
                        ib_back.setVisibility(View.VISIBLE);
                        apiService = RetrofitClient.getRetrofitInstance();
                        Intent intent = getIntent();
                        int id = intent.getIntExtra("id", -1);

                        ib_back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });

                        Call<Destination> call = apiService.getDestinationById(id);
                        call.enqueue(new Callback<Destination>() {
                            @Override
                            public void onResponse(Call<Destination> call, Response<Destination> response) {
                                if (response.isSuccessful()){
                                    Destination destination = response.body();
                                    List<String> topAttractions = destination.getTop_attractions();
                                    List<String> localDishes = destination.getLocal_dishes();
                                    List<String> activities = destination.getActivities();
                                    Picasso.get().load(destination.getImage()).into(ivImage);
                                    tvName.setText(destination.getName());
                                    tvCountry.setText( destination.getCountry());
                                    tvContinent.setText(destination.getContinent());
                                    tvDescription.setText(destination.getDescription());
                                    tvPopulation.setText(destination.getPopulation());
                                    tvCurrency.setText(destination.getCurrency());
                                    tvLanguage.setText(destination.getLanguage());
                                    tvBestTimeToVisit.setText(destination.getBest_time_to_visit());
                                    tvTopAttractions.setText(String.join(", ", topAttractions));
                                    tvLocalDishes.setText(String.join(", ", localDishes));
                                    tvActivities.setText(String.join(", ", activities));
                                }
                            }

                            @Override
                            public void onFailure(Call<Destination> call, Throwable t) {
                            }
                        });

                    }
                });
            }
        }).start();

    }

}