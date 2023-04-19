package com.example.dnevnjak20.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.adapter.PlanAdapter;
import com.example.dnevnjak20.adapter.WholePlanPagerAdapter;
import com.example.dnevnjak20.databinding.CalendarFragmentBinding;
import com.example.dnevnjak20.databinding.FragmentDailyPlanBinding;
import com.example.dnevnjak20.differ.PlanDiffCallback;
import com.example.dnevnjak20.listeners.SearchTextWatcher;
import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.model.enums.ObligationPriority;
import com.example.dnevnjak20.view_models.DateItemsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class DailyPlanFragment extends Fragment {
    public static final String DAILY_PLAN_FRAGMENT_TAG = "daily_plan_fragment";
    private DateItemsViewModel dateItemsViewModel;
    private FragmentDailyPlanBinding binding;
    private PlanAdapter planAdapter;
    public DailyPlanFragment() {
        super(R.layout.fragment_daily_plan);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDailyPlanBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();

    }

    private void init() {
        dateItemsViewModel = new ViewModelProvider(requireActivity()).get(DateItemsViewModel.class);
        initObservers();
        initRecycler();
        initListeners();
    }

    private void initRecycler() {
        planAdapter = new PlanAdapter(new PlanDiffCallback(), dateItemsViewModel, plan -> {
            //TODO kad se klikne na plan da se otvori detaljan prikaz tog plana
            dateItemsViewModel.setFocusedPlan(plan);

            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_holder_fcv, new PlanPagerFragment());
            ft.addToBackStack(null);
            ft.commit();
//            Menu menu =((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigation)).getMenu();
//            for(int i = 0; i < menu.size(); i++) {
//                menu.getItem(i).setEnabled(false);
//            }
        });
        binding.planRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.planRv.setAdapter(planAdapter);
    }

    private void initObservers() {
        dateItemsViewModel.getFocusedPosition().observe(getViewLifecycleOwner(), pos -> {
            DateItem currDateItem = dateItemsViewModel.getDateItemAtPosition(dateItemsViewModel.getFocusedPosition().getValue());
            StringBuilder sb = new StringBuilder();
            sb.append(Month.of(currDateItem.getMonth()))
                    .append(" ").append(currDateItem.getDay()).append(". ")
                    .append(currDateItem.getYear()).append(". ");
            binding.dailyPlanHeaderTv.setText(sb);
        });
        dateItemsViewModel.getPlansForTheDay().observe(getViewLifecycleOwner(), plans -> {
            planAdapter.submitList(plans);
        });
        //TODO
//        dateItemsViewModel.getFocusedPlan().observe(getViewLifecycleOwner(), plan -> {
//            planAdapter
//        });
    }

    private void initListeners() {
        binding.allTv.setOnClickListener(v -> {
            //treba da obavesti adapter da vrati recyclerview-u celu listu
            dateItemsViewModel.filterPlans(ObligationPriority.ALL);
        });
        binding.lowTv.setOnClickListener(v -> {
            //treba da obavesti adapter da vrati recyclerview-u listu zelenih planova
            dateItemsViewModel.filterPlans(ObligationPriority.LOW_PRIORITY);
        });
        binding.midTv.setOnClickListener(v -> {
            //treba da obavesti adapter da vrati recyclerview-u listu zutih planova
            dateItemsViewModel.filterPlans(ObligationPriority.MID_PRIORITY);
        });
        binding.highTv.setOnClickListener(v -> {
            //treba da obavesti adapter da vrati recyclerview-u listu crvenih planova
            dateItemsViewModel.filterPlans(ObligationPriority.HIGH_PRIORITY);
        });
        binding.pastObligationsCheckBox.setOnClickListener(v -> {
            if(binding.pastObligationsCheckBox.isChecked()) {
                dateItemsViewModel.filterPlans(ObligationPriority.ALL);
            } else {
                dateItemsViewModel.filterPlans(LocalDateTime.now());
            }
        });
        binding.searchEt.addTextChangedListener(new SearchTextWatcher(dateItemsViewModel));
/////////////////////////////CHANGING TO CREATE PLAN FRAGMENT//////////////////////////////
        binding.floatingButton.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_holder_fcv, new CreatePlanFragment());
            ft.addToBackStack(null);
            ft.commit();
//            Menu menu =((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigation)).getMenu();
//            for(int i = 0; i < menu.size(); i++) {
//                menu.getItem(i).setEnabled(false);
//            }
        });
///////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("RESUME");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("PAUSE");
    }
}