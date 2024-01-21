package com.bzu.auth.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bzu.auth.Data.Database;
import com.bzu.auth.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                readQr();
            }
        });

    }

    private void readQr() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Vol up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);

        Pattern pattern = Pattern.compile("secret=([^&]+)");
        Matcher matcher = pattern.matcher("otpauth://totp/IT-Tools:demo-user?issuer=IT-Tools&secret=ZTHNT2ATMLYSNBCQ&algorithm=SHA1&digits=6&period=30");

        if (matcher.find()) {
            String secret = matcher.group(1);
            assert secret != null;
            Log.d("Secret: " ,secret);
            pattern =Pattern.compile("issuer=([^&]+)");
            matcher = pattern.matcher("otpauth://totp/IT-Tools:demo-user?issuer=IT-Tools&secret=ZTHNT2ATMLYSNBCQ&algorithm=SHA1&digits=6&period=30");
            if(matcher.find()){
                secret = matcher.group(1);
                assert secret != null;
                Log.d("issuer: ", secret);
            }
        } else {
            System.out.println("No match found.");
        }


        barcodeLauncher.launch(options);
    }

    /*
                    Map<String, String> data = new HashMap<>();
                data.put("email", Objects.requireNonNull(Database.auth.getCurrentUser()).getEmail());
                data.put("app", issuer);
                data.put("secret", secret);
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
     */

    ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() != null) {


            String url = result.getContents();

            Pattern pattern = Pattern.compile("secret=([^&]+)");
            Matcher matcher = pattern.matcher(url);

            if (matcher.find()) {
                Log.d("DDDDDDDDDDDDDDDDDDDD", Objects.requireNonNull(matcher.group(1)));
                Log.d("TTTTTTTTTTTTTTTTT", url);
                String secret = matcher.group(1);
                pattern = Pattern.compile("issuer=([^&]+)");
                matcher = pattern.matcher(url);
                assert secret != null;
                Log.d("DDDDDDDDDDDDDDDDDDDD", secret);
                Log.d("TTTTTTTTTTTTTTTTT", url);
                if(matcher.find()) {
                    Log.d("DDDDDDDDDDDDDDDDDDDD", Objects.requireNonNull(matcher.group(1)));
                    Log.d("TTTTTTTTTTTTTTTTT", url);
                    Map<String, String> data = new HashMap<>();
                    data.put("email", Objects.requireNonNull(Database.auth.getCurrentUser()).getEmail());
                    data.put("app", matcher.group(1));
                    data.put("secret", secret);
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
            } else {
                System.out.println("No match found.");
            }

        }
    });

}
