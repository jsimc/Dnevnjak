package com.example.dnevnjak20.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;

import java.util.List;
import java.util.function.Consumer;

public class DateAdapter extends ListAdapter<DateItem, DateAdapter.DateItemViewHolder> {
    private final Consumer<DateItem> onDateItemClicked;

    public DateAdapter(@NonNull DiffUtil.ItemCallback<DateItem> diffCallback, Consumer<DateItem> onDateItemClicked) {
        super(diffCallback);
        this.onDateItemClicked = onDateItemClicked;
    }

    @NonNull
    @Override
    public DateItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        return new DateItemViewHolder(view, parent.getContext(), position -> {
            DateItem dateItem = getItem(position);
            onDateItemClicked.accept(dateItem);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull DateItemViewHolder holder, int position) {
        DateItem dateItem = getItem(position);
        holder.bind(dateItem);
    }

    public static class DateItemViewHolder extends RecyclerView.ViewHolder {
        // Ovo Consumer<> je kao neki funkcionalni interfejs koji uzima funkciju
        public DateItemViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            itemView.setOnClickListener(v -> {
                if (getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(DateItem dateItem) {
            ((TextView)itemView.findViewById(R.id.dateItemTv)).setText(dateItem.getDay()+"");
            switch(dateItem.getHighestPriority()) {
                case LOW_PRIORITY: itemView.findViewById(R.id.dateItemTv).setBackgroundColor(itemView.getResources().getColor(R.color.lowButton, null)); break;
                case MID_PRIORITY: itemView.findViewById(R.id.dateItemTv).setBackgroundColor(itemView.getResources().getColor(R.color.midButton, null)); break;
                case HIGH_PRIORITY: itemView.findViewById(R.id.dateItemTv).setBackgroundColor(itemView.getResources().getColor(R.color.highButton, null)); break;
                default: itemView.findViewById(R.id.dateItemTv).setBackgroundColor(itemView.getResources().getColor(R.color.purple_200)); break;
            }
        }
    }
}
