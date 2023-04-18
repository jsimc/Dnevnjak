package com.example.dnevnjak20.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.differ.PlanDiffCallback;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

import java.util.function.Consumer;

public class PlanAdapter extends ListAdapter<Plan, PlanAdapter.PlanViewHolder> {
    private final Consumer<Plan> onPlanClicked;
    private ViewModel dateItemViewModel;

    public PlanAdapter(PlanDiffCallback planDiffCallback, ViewModel dateItemViewModel, Consumer<Plan> onPlanClicked) {
        super(planDiffCallback);
        this.onPlanClicked = onPlanClicked;
        this.dateItemViewModel = dateItemViewModel;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item, parent, false);
        return new PlanViewHolder(view, parent.getContext(), dateItemViewModel, position -> {
            Plan plan = getItem(position);
            onPlanClicked.accept(plan);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull PlanViewHolder holder, int position) {
        Plan plan = getItem(position);
        holder.bind(plan);
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private Consumer<Integer> onPlanClicked;
        private ViewModel dateItemViewModel;
        private Context context;
        public PlanViewHolder(@NonNull View itemView, Context context, ViewModel dateItemViewModel, Consumer<Integer> onPlanClicked) {
            super(itemView);
            this.view = itemView;
            this.context = context;
            this.dateItemViewModel = dateItemViewModel;
            this.onPlanClicked = onPlanClicked;
            initListeners();
        }

        private void initListeners() {
            itemView.setOnClickListener(v -> {
                if(getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    onPlanClicked.accept(getBindingAdapterPosition());
                }
            });
            itemView.findViewById(R.id.deleteIv).setOnClickListener(v -> {
                Plan planToDelete = ((DateItemsViewModel)dateItemViewModel).getPlansForTheDay().getValue().get(getBindingAdapterPosition());
                ((DateItemsViewModel)dateItemViewModel).removePlanForCurrDay(planToDelete);
                Toast.makeText(context, "DELETED: " + planToDelete, Toast.LENGTH_SHORT).show();
            });
            itemView.findViewById(R.id.editIv).setOnClickListener(v -> {
                // TODO OPEN EDIT WINDOW
            });
        }
        @SuppressLint("SetTextI18n")
        public void bind(Plan plan) {
            StringBuilder sb = new StringBuilder();
            sb.append(plan.getPlanTimeFrom())
                    .append(" - ").append(plan.getPlanTimeTo());
            ((TextView)itemView.findViewById(R.id.timeTv)).setText(sb+"");
            ((TextView)itemView.findViewById(R.id.nameTv)).setText(plan.getName());
            switch(plan.getPriority()) {
                case LOW_PRIORITY: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.GREEN); break;
                case MID_PRIORITY: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.YELLOW); break;
                case HIGH_PRIORITY: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.RED); break;
                default: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.GREEN); break;
            }
        }

    }
}
