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

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardView> {

    Context context;
    List<AuthInfo> data;

    public CardAdapter(Context context, List<AuthInfo> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CardView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardView(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardView holder, @SuppressLint("RecyclerView") int position) {
        holder.appName.setText(data.get(position).getAppName());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int code = getTOTP(data.get(position).getSecretKey());
                            
                        }
                    }, 3000);
                }
            }
        }, 1);

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

    @Override
    public int getItemCount() {
        return 0;
    }
}
