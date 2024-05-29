package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText et_name, et_username, et_password;
    Button btn_register;
    TextView tv_Login;
    private DatabaseHelper myDB;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_name = findViewById(R.id.et_name);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_register = findViewById(R.id.btn_register);
        tv_Login = findViewById(R.id.tvLogin);
        myDB = new DatabaseHelper(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void registerUser(){
        String name = et_name.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "isi semua data", Toast.LENGTH_SHORT).show();
        } else if (myDB.isUsernameExists(username)) {
            Toast.makeText(this, "Username sudah terdaftar", Toast.LENGTH_SHORT).show();
        } else {
            myDB.insertRecord(name, username, password);
            Toast.makeText(this, "Berhasil registrasi", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
