package com.example.dnevnjak20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.databinding.CalendarFragmentBinding;
import com.example.dnevnjak20.databinding.FragmentDailyPlanBinding;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

public class DailyPlanFragment extends Fragment {
    public static final String DAILY_PLAN_FRAGMENT_TAG = "daily_plan_fragment";
    private DateItemsViewModel dateItemsViewModel;

    private FragmentDailyPlanBinding binding;
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
        dateItemsViewModel = new ViewModelProvider(requireActivity()).get(DateItemsViewModel.class);
        System.out.println("DATEITEMSVIEWMODEL - DAILYPLAN: " + dateItemsViewModel);
        dateItemsViewModel.getFocusedPosition().observe(getViewLifecycleOwner(),
                (i) -> binding.dailyPlanTv.setText(i+""));
    }
}