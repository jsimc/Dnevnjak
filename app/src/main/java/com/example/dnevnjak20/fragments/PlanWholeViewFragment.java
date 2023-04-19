package com.example.dnevnjak20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.databinding.PlanWholeViewFragmentBinding;
import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.view_models.DateItemsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.Month;

public class PlanWholeViewFragment extends Fragment {
    private PlanWholeViewFragmentBinding binding;
    private DateItemsViewModel dateItemsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PlanWholeViewFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dateItemsViewModel = new ViewModelProvider(requireActivity()).get(DateItemsViewModel.class);
        init();
    }

    private void init() {
        //////////// Header /////////////
        binding.seeHeaderTv.setText(getCurrDateString());
        ////////////  Time  /////////////
        String sb = dateItemsViewModel.getFocusedPlan().getValue().getPlanTimeFrom() + " - " + dateItemsViewModel.getFocusedPlan().getValue().getPlanTimeTo();
        binding.seeTimeTv.setText(sb);
        //////////// Title //////////////
        binding.seeTitleTv.setText(dateItemsViewModel.getFocusedPlan().getValue().getName());
        //////////// Info ///////////////
        binding.seeLongInfoTv.setText(dateItemsViewModel.getFocusedPlan().getValue().getLongInfo());
        initListeners();
    }

    private void initListeners() {
        ///////// Edit Button  ////////
        binding.editBtn.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_holder_fcv, new EditPlanFragment());
            ft.addToBackStack(null);
            ft.commit();
        });
        ///////// Back Button ///////
        binding.backBtn.setOnClickListener(v -> {
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
        FragmentManager fm = getParentFragmentManager();
        fm.popBackStack();
        Menu menu =((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigation)).getMenu();
        for(int i = 0; i < menu.size(); i++) {
            if(i==1) continue;
            menu.getItem(i).setEnabled(true);
        }
    }
}
