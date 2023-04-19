package com.example.dnevnjak20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dnevnjak20.adapter.WholePlanPagerAdapter;
import com.example.dnevnjak20.databinding.PlanPagerFragmentBinding;
import com.example.dnevnjak20.differ.PlanDiffCallback;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlanPagerFragment extends Fragment {
    private PlanPagerFragmentBinding binding;
    private DateItemsViewModel dateItemsViewModel;
    private WholePlanPagerAdapter planPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PlanPagerFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateItemsViewModel = new ViewModelProvider(requireActivity()).get(DateItemsViewModel.class);
        init();
    }

    private void init() {
        initObservers();
        initRecycler();
    }

    private void initRecycler() {
        planPagerAdapter = new WholePlanPagerAdapter(new PlanDiffCallback(), dateItemsViewModel);
        binding.wholePlanRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.wholePlanRv.setAdapter(planPagerAdapter);
        binding.wholePlanRv.scrollToPosition(dateItemsViewModel.getCurrentDateItem().getDailyPlans().indexOf(dateItemsViewModel.getFocusedPlan().getValue()));
    }
    private void initObservers() {
        //TODO mozda konflikt? sa DailyPlanFragment line:100
        dateItemsViewModel.getPlansForTheDay().observe(getViewLifecycleOwner(), plans ->{
            planPagerAdapter.submitList(plans);
        });
    }
}
