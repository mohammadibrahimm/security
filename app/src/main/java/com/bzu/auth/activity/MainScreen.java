package com.bzu.auth.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bzu.auth.Data.Database;
import com.bzu.auth.MainActivity1;
import com.bzu.auth.R;
import com.google.firebase.auth.FirebaseUser;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseUser u = Database.auth.getCurrentUser();

        if(u == null) {
            Intent intent = new Intent(MainScreen.this, MainActivity1.class);
            startActivity(intent);
            finish();
        }




    }
}
