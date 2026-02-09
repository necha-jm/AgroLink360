package com.app.agroli;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Adapter>{

    @NonNull
    @Override
    public Adapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class Adapter extends RecyclerView.ViewHolder{


        public Adapter(@NonNull View itemView) {
            super(itemView);
        }
    }
}
