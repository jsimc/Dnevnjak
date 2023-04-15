package com.example.dnevnjak20.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.dnevnjak20.model.DateItem;


public class DateItemDiffCallback extends DiffUtil.ItemCallback<DateItem> {
    @Override
    public boolean areItemsTheSame(@NonNull DateItem oldItem, @NonNull DateItem newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull DateItem oldItem, @NonNull DateItem newItem) {
        return oldItem.getDay() == newItem.getDay()
                && oldItem.getMonth() == newItem.getMonth()
                && oldItem.getYear() == newItem.getYear();
    }
}
