package com.bzu.auth.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bzu.auth.R;
import com.bzu.auth.model.AuthInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CardAdapter extends RecyclerView.Adapter<CardView> {

    Context context;
    List<AuthInfo> data;

    private Map<Integer, Long> updateTimes; // Store last update time for each item

    int x = 1;
    public Handler handler = new Handler();
    private Runnable updateRunnable;

    public CardAdapter(Context context, List<AuthInfo> data) {
        this.context = context;
        this.data = data;
        this.updateTimes = new HashMap<>();

        updateRunnable = new Runnable() {
            @Override
            public void run() {
                // Update the data and notify the adapter of the changes
                for (int i = 0; i < data.size(); i++) {
                    updateData(i);
                }
                notifyDataSetChanged();
                // Schedule the next update after 30 seconds
                handler.postDelayed(this, 30000); // 30 seconds in milliseconds
            }
        };

        // Start the initial update
        handler.post(updateRunnable);

    }

    private void updateData(int position) {
        // Update the data (You can replace this with your logic to update the data)
        int newValue = data.get(position).getNumber() + 1; // Replace this with your update logic
        data.get(position).setNumber(newValue);

        // Update the last update time for this item
        updateTimes.put(position, System.currentTimeMillis());
    }

    @NonNull
    @Override
    public CardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardView(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardView holder, @SuppressLint("RecyclerView") int position) {
        // Bind data to the ViewHolder
        holder.appName.setText(String.valueOf(data.get(position)));

        // Update the timer
        long timeElapsed = System.currentTimeMillis() - updateTimes.get(position);
        long timeRemaining = 30000 - timeElapsed; // 30 seconds in milliseconds

        if (timeRemaining <= 0) {
            holder.code.setText("Updating...");
        } else {
            String formattedTime = formatTime(timeRemaining);
            holder.code.setText(formattedTime);
        }

    }

    private int getTOTP(String secret) {
        String url = "";
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        })

        return 0;
    }

    private String formatTime(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return sdf.format(new Date(milliseconds));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
