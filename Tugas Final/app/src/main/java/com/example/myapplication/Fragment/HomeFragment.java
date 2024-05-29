package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.HomeAdapter;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.Database.DatabaseHelperUtils;
import com.example.myapplication.Model.DestinationViewModel;
import com.example.myapplication.R;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private Button inputBtn, btnOriginal, btnDescending, btnLimit;
    private ImageView ivBookmark;
    private EditText et_number;
    private DestinationViewModel viewModel;
    private DatabaseHelper myDB;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv_destination);
        et_number = view.findViewById(R.id.et_number);
        inputBtn = view.findViewById(R.id.inputbtn);
        ivBookmark = view.findViewById(R.id.ivBookmark);
        btnOriginal = view.findViewById(R.id.btn_original);
        btnDescending = view.findViewById(R.id.btn_descending);
        btnLimit = view.findViewById(R.id.btn_limit);
        inputBtn.setVisibility(View.GONE);
        et_number.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myDB = new DatabaseHelper(getContext());
        viewModel = new ViewModelProvider(requireActivity()).get(DestinationViewModel.class);
        viewModel.getDestinations().observe(getViewLifecycleOwner(), destinations -> {
            adapter = new HomeAdapter(getContext(), destinations);
            recyclerView.setAdapter(adapter);
        });
//        deleteDatabase();
        fetchDestinations();

        btnOriginal.setOnClickListener(v -> {
            inputBtn.setVisibility(View.GONE);
            et_number.setVisibility(View.GONE);
            fetchDestinations();
            btnOriginal.setBackgroundColor(getResources().getColor(R.color.pressed_button_color));
            inputBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnDescending.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnLimit.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        });

        btnDescending.setOnClickListener(v -> {
            inputBtn.setVisibility(View.GONE);
            et_number.setVisibility(View.GONE);
            fetchDestinationsSorted();
            btnDescending.setBackgroundColor(getResources().getColor(R.color.pressed_button_color));
            inputBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnOriginal.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnLimit.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        });

        btnLimit.setOnClickListener(v -> {
            inputBtn.setVisibility(View.VISIBLE);
            et_number.setVisibility(View.VISIBLE);
            btnLimit.setBackgroundColor(getResources().getColor(R.color.pressed_button_color));
            inputBtn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnOriginal.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btnDescending.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            inputBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String number = et_number.getText().toString();
                    int limit = Integer.parseInt(number);
                    fetchDestinationsLimit(limit);
                    // Ubah warna background tombol input ketika ditekan
                    inputBtn.setBackgroundColor(getResources().getColor(R.color.pressed_button_color));
                    // Reset warna background tombol lainnya
                    btnOriginal.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    btnDescending.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    btnLimit.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                }
            });
        });
        return view;
    }
    private void fetchDestinations() {
        viewModel.fetchDestinations();
    }

    private void fetchDestinationsSorted() {
        viewModel.fetchDestinationsSorted();
    }

    private void fetchDestinationsLimit(int limit) {
        viewModel.fetchDestinationsLimit(limit);
    }
    private void deleteDatabase() {
        myDB.deleteDatabase();
    }

}
