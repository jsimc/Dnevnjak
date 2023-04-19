package com.example.dnevnjak20.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.fragments.EditPlanFragment;
import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.view_models.DateItemsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.Month;


public class WholePlanPagerAdapter extends ListAdapter<Plan, WholePlanPagerAdapter.WholePlanViewHolder> {

    private DateItemsViewModel dateItemViewModel;

    public WholePlanPagerAdapter(@NonNull DiffUtil.ItemCallback<Plan> diffCallback, ViewModel dateItemsViewModel) {
        super(diffCallback);
        this.dateItemViewModel = (DateItemsViewModel) dateItemsViewModel;
    }

    @NonNull
    @Override
    public WholePlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_whole_view_fragment, parent, false);
        return new WholePlanViewHolder(view, parent.getContext(), dateItemViewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull WholePlanViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static class WholePlanViewHolder extends RecyclerView.ViewHolder {

        private DateItemsViewModel dateItemsViewModel;
        private Context context;
        public WholePlanViewHolder(@NonNull View itemView, Context context, ViewModel dateItemsViewModel) {
            super(itemView);
            this.context = context;
            this.dateItemsViewModel = (DateItemsViewModel) dateItemsViewModel;
        }

        public void bind(Plan plan) {
            init(plan);
        }
        private void init(Plan plan) {
            //////////// Header /////////////
            ((TextView)itemView.findViewById(R.id.seeHeaderTv)).setText(getCurrDateString());
//            binding.seeHeaderTv.setText(getCurrDateString());
            ////////////  Time  /////////////
            String sb = plan.getPlanTimeFrom() + " - " + plan.getPlanTimeTo();
            ((TextView)itemView.findViewById(R.id.seeTimeTv)).setText(sb);
//            binding.seeTimeTv.setText(sb);
            //////////// Title //////////////
            ((TextView)itemView.findViewById(R.id.seeTitleTv)).setText(plan.getName());
//            binding.seeTitleTv.setText(dateItemsViewModel.getFocusedPlan().getValue().getName());
            //////////// Info ///////////////
            ((TextView)itemView.findViewById(R.id.seeLongInfoTv)).setText(plan.getLongInfo());
//            binding.seeLongInfoTv.setText(dateItemsViewModel.getFocusedPlan().getValue().getLongInfo());
            //////////// Priority ///////////
            setPriority(plan, ((TextView)itemView.findViewById(R.id.seePriorityTv)));
            initListeners();
        }

        private void setPriority(Plan plan, TextView viewById) {
            switch(plan.getPriority()){
                case LOW_PRIORITY:
                    viewById.setText(context.getResources().getText(R.string.low));
                    viewById.setBackgroundColor(context.getResources().getColor(R.color.lowButton, null)); break;
                case MID_PRIORITY:
                    viewById.setText(context.getResources().getText(R.string.mid));
                    viewById.setBackgroundColor(context.getResources().getColor(R.color.midButton, null)); break;
                case HIGH_PRIORITY: default:
                    viewById.setText(context.getResources().getText(R.string.high));
                    viewById.setBackgroundColor(context.getResources().getColor(R.color.highButton, null)); break;
            }
        }

        private void initListeners() {
            ///////// Edit Button  ////////
            itemView.findViewById(R.id.editBtn).setOnClickListener(v -> {
                FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_holder_fcv, new EditPlanFragment());
                ft.addToBackStack(null);
                ft.commit();
            });
            ///////// Back Button ///////
            itemView.findViewById(R.id.backBtn).setOnClickListener(v -> {
                toDailyPlanView();
            });
        }
        private String getCurrDateString() {
            DateItem currDateItem = dateItemsViewModel.getDateItemAtPosition(dateItemsViewModel.getFocusedPosition().getValue());
            String sb = Month.of(currDateItem.getMonth()) +
                    " " + currDateItem.getDay() + ". " +
                    currDateItem.getYear() + ". ";
            return sb;
        }
        private void toDailyPlanView() {
            FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
            fm.popBackStack();
//            Menu menu =((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigation)).getMenu();
//            for(int i = 0; i < menu.size(); i++) {
//                if(i==1) continue;
//                menu.getItem(i).setEnabled(true);
//            }
        }
    }
}
