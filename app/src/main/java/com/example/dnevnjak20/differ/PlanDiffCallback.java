package com.example.dnevnjak20.differ;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;

public class PlanDiffCallback extends DiffUtil.ItemCallback<Plan>{
    @Override
    public boolean areItemsTheSame(@NonNull Plan oldItem, @NonNull Plan newItem) {
        return oldItem.getId()== newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Plan oldItem, @NonNull Plan newItem) {
        return oldItem.getPlanDate().equals(newItem.getPlanDate())
                && oldItem.getPlanTimeFrom().equals(newItem.getPlanTimeFrom())
                && oldItem.getPlanTimeTo().equals(newItem.getPlanTimeTo())
                && oldItem.getPriority().equals(newItem.getPriority())
                && oldItem.getName().equals(newItem.getName())
                && oldItem.getLongInfo().equals(newItem.getLongInfo());
    }
}
