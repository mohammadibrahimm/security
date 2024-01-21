package com.bzu.auth.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bzu.auth.R;
import com.bzu.auth.model.AuthInfo;

import org.json.JSONException;
import org.json.JSONObject;

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

    private void updateData(int position) { // TODO: update the code using volley
        // Update the data (You can replace this with your logic to update the data)
        String url = "http://127.0.0.1:5001/auth/totp";

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    data.get(position).setCode(respObj.getString("code"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("secret",data.get(position).getSecretKey());
                params.put("method","MD5");
                return params;
            }
        };

        queue.add(request);

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
        holder.appName.setText(String.valueOf(data.get(position).getCode()));

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
