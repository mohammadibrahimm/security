package com.bzu.auth.views;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bzu.auth.R;

public class CardView extends RecyclerView.ViewHolder {

    TextView appName;
    TextView code;
    TextView nextCode;
    TextView curr_timer;
    TextView next_timer;
    CountDownTimer countDownTimer;

    public CardView(@NonNull View itemView) {
        super(itemView);
        appName = itemView.findViewById(R.id.app_name);
        code = itemView.findViewById(R.id.Current_code);
        nextCode = itemView.findViewById(R.id.next_code);
        curr_timer =itemView.findViewById(R.id.curr_code_timer);
        next_timer =itemView.findViewById(R.id.next_code_timer);
    }
}
