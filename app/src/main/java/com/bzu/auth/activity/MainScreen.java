package com.bzu.auth.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bzu.auth.Data.Database;
import com.bzu.auth.MainActivity1;
import com.bzu.auth.R;
import com.bzu.auth.model.AuthInfo;
import com.bzu.auth.views.CardAdapter;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);

        FirebaseUser u = Database.auth.getCurrentUser();

        if(u == null) {
            Intent intent = new Intent(MainScreen.this, MainActivity1.class);
            startActivity(intent);
            finish();
        }

        List<AuthInfo> data = new ArrayList<>();

        data.add(new AuthInfo("test", "", "H4MDVD2CI5TKQI75"));
        data.add(new AuthInfo("test", "", "H4MDVD2CI5TKQI75"));
        data.add(new AuthInfo("test", "", "H4MDVD2CI5TKQI75"));
        data.add(new AuthInfo("test", "", "H4MDVD2CI5TKQI75"));
        data.add(new AuthInfo("test", "", "H4MDVD2CI5TKQI75"));
        data.add(new AuthInfo("test", "", "H4MDVD2CI5TKQI75"));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardAdapter(this, data);
        recyclerView.setAdapter(adapter);

    }
}
