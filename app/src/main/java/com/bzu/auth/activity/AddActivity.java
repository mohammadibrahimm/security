package com.bzu.auth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bzu.auth.Data.Database;
import com.bzu.auth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddActivity extends AppCompatActivity {


    EditText tag;
    EditText secret_key;
    Button addB;
    Button scanB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        tag = findViewById(R.id.accountTag);
        secret_key = findViewById(R.id.secretEditText);

        addB = findViewById(R.id.addButton);
        scanB = findViewById(R.id.scanButton);


        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tag.getText().toString().equals("") || secret_key.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(AddActivity.this, "Please enter the data.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Map<String, String> data = new HashMap<>();
                    data.put("email", Objects.requireNonNull(Database.auth.getCurrentUser()).getEmail());
                    data.put("app", tag.getText().toString());
                    data.put("secret", secret_key.getText().toString());

                    Database.database.collection("secretKeys").
                            add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast toast = Toast.makeText(AddActivity.this, "تم اضافة المنتج بنجاح 🥳", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(AddActivity.this, MainScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast toast = Toast.makeText(AddActivity.this, "خطأ في اضافة المنتج 😥", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            });
                }
            }
        });

        scanB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, QrCode.class);
                startActivity(intent);
            }
        });

    }
}
