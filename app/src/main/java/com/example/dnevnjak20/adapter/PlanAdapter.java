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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.differ.PlanDiffCallback;
import com.example.dnevnjak20.fragments.EditPlanFragment;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.function.Consumer;

public class PlanAdapter extends ListAdapter<Plan, PlanAdapter.PlanViewHolder> {
    private final Consumer<Plan> onPlanClicked;
    private DateItemsViewModel dateItemViewModel;

    public PlanAdapter(PlanDiffCallback planDiffCallback, ViewModel dateItemViewModel, Consumer<Plan> onPlanClicked) {
        super(planDiffCallback);
        this.onPlanClicked = onPlanClicked;
        this.dateItemViewModel = (DateItemsViewModel) dateItemViewModel;
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
        private Consumer<Integer> onPlanClicked;
        private DateItemsViewModel dateItemViewModel;
        private Context context;
//        private Plan focusedPlan;
        public PlanViewHolder(@NonNull View itemView, Context context, ViewModel dateItemViewModel, Consumer<Integer> onPlanClicked) {
            super(itemView);
            this.context = context;
            this.dateItemViewModel = (DateItemsViewModel) dateItemViewModel;
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
                Plan planToDelete = dateItemViewModel.getPlansForTheDay().getValue().get(getBindingAdapterPosition());
                dateItemViewModel.removePlanForCurrDay(dateItemViewModel.getPlansForTheDay().getValue().get(getBindingAdapterPosition()));
                Toast.makeText(context, "DELETED: " + planToDelete, Toast.LENGTH_SHORT).show();
                dateItemViewModel.getDatabaseHelper().deletePlan(planToDelete);
            });

            /////////////////////////// CHANGE TO EDIT PLAN FRAGMENT //////////////////////
            itemView.findViewById(R.id.editIv).setOnClickListener(v -> {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_holder_fcv, new EditPlanFragment());
                ft.addToBackStack(null);
                ft.commit();

                dateItemViewModel.setFocusedPlan(dateItemViewModel.getPlansForTheDay().getValue().get(getBindingAdapterPosition()));
                //TODO disable menu OVO JEE FEATURE
                // ideja je mozda bila da ovaj tu kod dole prebacimo npr u mainActivity ili u mainPageAdapter,
                // a da u view modelu imamo boolean flag kojeg posmatra nas mainpageadapter
//                Menu menu =((BottomNavigationView)itemView.findViewById(R.id.bottomNavigation)).getMenu();
//                for(int i = 0; i < menu.size(); i++) {
//                    menu.getItem(i).setEnabled(false);
//                }
            });
            //////////////////////////////////////////////////////////////////////////////
        }
        @SuppressLint("SetTextI18n")
        public void bind(Plan plan) {
            String sb = plan.getPlanTimeFrom() + " - " + plan.getPlanTimeTo();
            ((TextView)itemView.findViewById(R.id.timeTv)).setText(sb);
            ((TextView)itemView.findViewById(R.id.nameTv)).setText(plan.getName());
            switch(plan.getPriority()) {
                case LOW_PRIORITY: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.GREEN); break;
                case MID_PRIORITY: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.YELLOW); break;
                case HIGH_PRIORITY: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.RED); break;
                default: itemView.findViewById(R.id.planVector).setBackgroundColor(Color.GREEN); break;
            }
            if(plan.getPlanDate().isBefore(LocalDate.now()) ||
                    (plan.getPlanDate().isEqual(LocalDate.now()) && plan.getPlanTimeTo().isBefore(LocalTime.now()))) {
                // oboji ga u sivo
                itemView.findViewById(R.id.planItemLL).setBackgroundColor(Color.LTGRAY);
            }
        }

    }
}
