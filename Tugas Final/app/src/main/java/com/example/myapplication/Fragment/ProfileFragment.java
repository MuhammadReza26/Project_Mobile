package com.example.myapplication.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activity.LoginActivity;
import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.Model.User;

public class ProfileFragment extends Fragment {
    TextView tv_name, tv_username;
    ImageView iv_profile;
    DatabaseHelper myDB;
    Button btn_logout;

    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String KEY_USERNAME = "username";
    public static final String LOGIN_PREFERENCES = "CheckLogin";
    public static final String KEY_LOGIN = "login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        tv_name = view.findViewById(R.id.tv_name);
        tv_username = view.findViewById(R.id.tv_username);
        iv_profile = view.findViewById(R.id.profile_image);
        btn_logout = view.findViewById(R.id.btn_logout);
        myDB = new DatabaseHelper(getActivity());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_USERNAME, null);

        if (username != null) {
            User user = myDB.getUserInfo(username);
            if (user != null) {
                tv_name.setText(user.getName());
                tv_username.setText(user.getUsername());
            }
        }

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                SharedPreferences preferencesLogin = getActivity().getSharedPreferences(LOGIN_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencesLogin.edit();
                editor.putBoolean(KEY_LOGIN, false);
                editor.apply();
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }
}