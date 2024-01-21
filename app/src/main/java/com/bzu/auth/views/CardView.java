package com.bzu.auth.views;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bzu.auth.R;

public class CardView extends RecyclerView.ViewHolder {

    TextView appName;
    TextView code;
    ImageView imgView;

    public CardView(@NonNull View itemView) {
        super(itemView);

        appName = itemView.findViewById(R.id.appNameTextView);
        code = itemView.findViewById(R.id.textViewKey);
        imgView =itemView.findViewById(R.id.imageViewIcon);


    }
}
