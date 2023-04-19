package com.example.dnevnjak20.fragments;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.databinding.CreatePlanFragmentBinding;
import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.model.enums.ObligationPriority;
import com.example.dnevnjak20.view_models.DateItemsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Locale;

public class CreatePlanFragment extends Fragment {
    private CreatePlanFragmentBinding binding;
    private DateItemsViewModel dateItemsViewModel;

    /////////// Needed for new Plan ////////////
    private String planName;
    private LocalDate planDate;
    private LocalTime planTimeFrom;
    private LocalTime planTimeTo;
    private ObligationPriority priority;
    private String longInfo;
    ////////////////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreatePlanFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        dateItemsViewModel = new ViewModelProvider(requireActivity()).get(DateItemsViewModel.class);
        ///////// header ////////////////////////////////
        binding.createPlanHeaderTv.setText(getCurrDateString());
        /////////////////////////////////////////////////
        initListeners();
    }

    private void initListeners() {
        binding.setLowBtn.setOnClickListener(v -> {
            priority = ObligationPriority.LOW_PRIORITY;
            binding.setLowBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));
            binding.setMidBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.midButton, null)));
            binding.setHighBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.highButton, null)));
        });
        binding.setMidBtn.setOnClickListener(v -> {
            priority = ObligationPriority.MID_PRIORITY;
            binding.setLowBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lowButton, null)));
            binding.setMidBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));
            binding.setHighBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.highButton, null)));
        });
        binding.setHighBtn.setOnClickListener(v -> {
            priority = ObligationPriority.HIGH_PRIORITY;
            binding.setLowBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lowButton, null)));
            binding.setMidBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.midButton, null)));
            binding.setHighBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));
        });
        binding.cancelBtn.setOnClickListener(v -> {
            toDailyPlanView();
        });
        //////////////////// CREATE BTN //////////////////////////////
        binding.createBtn.setOnClickListener(v -> {
            checkAttributes();
            planName = binding.titleEt.getText().toString();
            planDate = getCurrDate();
            longInfo = binding.infoEt.getText().toString();
//            System.out.println("PRIORITY = " + priority.toString());
            Plan plan = new Plan(planName, priority, planDate, planTimeFrom, planTimeTo, longInfo);
            if(!dateItemsViewModel.addPlanForCurrDay(plan)) {
                Toast.makeText(requireContext(), "Already have a plan at that time", Toast.LENGTH_SHORT).show();
            } else {
                showAddSnackBar(plan);
                toDailyPlanView();
            }
            // TODO da se prebaci na prethodni prozor ALI da se sacuva u BAZU PLAN.
        });
        ///////////////////////////////////////////////////////////////
        binding.button3.setOnClickListener(v -> {
            new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                binding.button3.setText(String.format(Locale.getDefault(),"From: %02d:%02d", hourOfDay, minute));
                planTimeFrom = LocalTime.of(hourOfDay, minute);
            }, 12, 0, false).show();
        });
        binding.button4.setOnClickListener(v -> {
            new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                planTimeTo = LocalTime.of(hourOfDay, minute);
                binding.button4.setText(String.format(Locale.getDefault(),"To: %02d:%02d", hourOfDay, minute));
            }, 12, 0, false).show();
        });
    }
    private String getCurrDateString() {
        DateItem currDateItem = dateItemsViewModel.getDateItemAtPosition(dateItemsViewModel.getFocusedPosition().getValue());
        String sb = Month.of(currDateItem.getMonth()) +
                " " + currDateItem.getDay() + ". " +
                currDateItem.getYear() + ". ";
        return sb;
    }

    private LocalDate getCurrDate() {
        DateItem currDateItem = dateItemsViewModel.getDateItemAtPosition(dateItemsViewModel.getFocusedPosition().getValue());
        return LocalDate.of(currDateItem.getYear(), currDateItem.getMonth(), currDateItem.getDay());
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
    private void checkAttributes() {
        if(priority == null) {
            Toast.makeText(requireContext(), "Choose a priority.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(planTimeFrom == null) {
            Toast.makeText(requireContext(), "Select starting time.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(planTimeTo == null) {
            Toast.makeText(requireContext(), "Select ending time.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(planTimeTo.isBefore(planTimeFrom)) {
            Toast.makeText(requireContext(), "Ending time has to be after starting time.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void showAddSnackBar(Plan plan) {
        Snackbar.make(binding.getRoot(), "Plan added", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view -> dateItemsViewModel.removePlanForCurrDay(plan))
                .show();
    }
}
