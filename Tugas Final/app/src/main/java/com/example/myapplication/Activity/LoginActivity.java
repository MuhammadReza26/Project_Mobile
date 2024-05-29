package com.example.myapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.Database.DatabaseHelper;
import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText et_username, et_password;
    TextView tvRegister;
    Button btn_login;
    private DatabaseHelper myDB;
    public static final String SHARED_PREFERENCES = "sharedPreferences";
    public static final String KEY_USERNAME = "username";
    public static final String LOGIN_PREFERENCES = "CheckLogin";
    public static final String KEY_LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tvRegister);
        myDB = new DatabaseHelper(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    Toast.makeText(LoginActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Isi username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Isi password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!myDB.checkUser(username, password)) {
                    Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Jika semuanya valid, lanjutkan ke MainActivity
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_USERNAME, username);
                editor.apply();
                SharedPreferences preferencesLogin = getSharedPreferences(LOGIN_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editorLogin = preferencesLogin.edit();
                editorLogin.putBoolean(KEY_LOGIN, true);
                editorLogin.apply();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}