package com.example.dnevnjak20.fragments;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.databinding.EditPlanFragmentBinding;
import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.model.enums.ObligationPriority;
import com.example.dnevnjak20.view_models.DateItemsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Locale;

/**
 * EditPlanFragment je detaljan prikaz obaveze
 */
public class EditPlanFragment extends Fragment {
    private EditPlanFragmentBinding binding;
    private DateItemsViewModel dateItemsViewModel;
    private LiveData<Plan> plan;
    ///////////// Needed for editing a plan /////////////
    private String planName;
    private LocalDate planDate;
    private LocalTime planTimeFrom;
    private LocalTime planTimeTo;
    private ObligationPriority priority;
    private String longInfo;
    /////////////////////////////////////////////////////
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = EditPlanFragmentBinding.inflate(inflater, container, false);
        //TODO disable menu
//                Menu menu =((BottomNavigationView)itemView.findViewById(R.id.bottomNavigation)).getMenu();
//                for(int i = 0; i < menu.size(); i++) {
//                    menu.getItem(i).setEnabled(false);
//                }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        dateItemsViewModel = new ViewModelProvider(requireActivity()).get(DateItemsViewModel.class);
        System.out.println("DATEITEMSVIEWMODEL: " + dateItemsViewModel);
        plan = dateItemsViewModel.getFocusedPlan();
        // Header
        binding.editPlanHeaderTv.setText(getCurrDateString());
        // Priorities
        setPriorityBtnChecked();
        // Title
        binding.editTitleEt.setText(plan.getValue().getName());
        // From
        binding.editFromBtn.setText(String.format(Locale.getDefault(),"From: %02d:%02d", plan.getValue().getPlanTimeFrom().getHour(), plan.getValue().getPlanTimeFrom().getMinute()));
        // To
        binding.editToBtn.setText(String.format(Locale.getDefault(),"From: %02d:%02d", plan.getValue().getPlanTimeTo().getHour(), plan.getValue().getPlanTimeTo().getMinute()));
        // Long info
        binding.editInfoEt.setText(plan.getValue().getLongInfo());
        initListeners();
    }

    private void setPriorityBtnChecked() {
        switch(plan.getValue().getPriority()) {
            case LOW_PRIORITY:
                binding.editLowBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));break;
            case MID_PRIORITY:
                binding.editMidBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null))); break;
            case HIGH_PRIORITY: default:
                binding.editHighBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));
        }
    }

    /**
     * Napomena: titleEt i longInfoEt nemaju listenere nego njihove vrednosti izvlacim na kraju, kad se klikne Save
     */
    private void initListeners() {
        //////////// PRIORITY BUTTONS ////////////////////////
        binding.editLowBtn.setOnClickListener(v -> {
            plan.getValue().setPriority(ObligationPriority.LOW_PRIORITY);
            binding.editLowBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));
            binding.editMidBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.midButton, null)));
            binding.editHighBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.highButton, null)));
        });
        binding.editMidBtn.setOnClickListener(v -> {
            plan.getValue().setPriority(ObligationPriority.MID_PRIORITY);
            binding.editLowBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lowButton, null)));
            binding.editMidBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));
            binding.editHighBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.highButton, null)));
        });
        binding.editHighBtn.setOnClickListener(v -> {
            plan.getValue().setPriority(ObligationPriority.HIGH_PRIORITY);
            binding.editLowBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.lowButton, null)));
            binding.editMidBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.midButton, null)));
            binding.editHighBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500, null)));
        });
        //////////////////////////////////////////////////////////
        ////////// SAVE BUTTON ////////////////////////////////////
        binding.saveBtn.setOnClickListener(v -> {
            //TODO sacuvaj u bazu LMAO ???
            plan.getValue().setLongInfo(binding.editInfoEt.getText().toString());
            plan.getValue().setName(binding.editTitleEt.getText().toString());
            Toast.makeText(requireContext(), "Plan changed!", Toast.LENGTH_SHORT).show();
            toDailyPlanView();
        });
        //////////////////////////////////////////////////////////

        //////////////////// CANCEL BUTTON ///////////////////////
        binding.editCancelBtn.setOnClickListener(v -> {
            toDailyPlanView();
        });
        //////////////////////////////////////////////////////////

        /////////////// DELETE BUTTON ////////////////////////////
        binding.deleteBtn.setOnClickListener(v -> {
            dateItemsViewModel.removePlanForCurrDay(plan.getValue());
            // TODO oce li ovo da radi?
            showRemoveSnackBar(dateItemsViewModel.getFocusedPlan().getValue());
            dateItemsViewModel.setFocusedPlan(
                    dateItemsViewModel.getPlansForTheDay().getValue().isEmpty() ? null : dateItemsViewModel.getPlansForTheDay().getValue().get(0));
            toDailyPlanView();
        });
        //////////////////////////////////////////////////////////

        //////////////////// FROM ////////////////////////////////
        binding.editFromBtn.setOnClickListener(v -> {
            new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                binding.editFromBtn.setText(String.format(Locale.getDefault(),"From: %02d:%02d", hourOfDay, minute));
                plan.getValue().setPlanTimeFrom(LocalTime.of(hourOfDay, minute));
            }, 12, 0, false).show();
        });
        //////////////////////////////////////////////////////////

        ////////////////////// TO ////////////////////////////////
        binding.editToBtn.setOnClickListener(v -> {
            new TimePickerDialog(getContext(), (view1, hourOfDay, minute) -> {
                plan.getValue().setPlanTimeTo(LocalTime.of(hourOfDay, minute));
                binding.editToBtn.setText(String.format(Locale.getDefault(),"To: %02d:%02d", hourOfDay, minute));
            }, 12, 0, false).show();
        });
        //////////////////////////////////////////////////////////
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
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_holder_fcv, new DailyPlanFragment());
        ft.commit();
        Menu menu =((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigation)).getMenu();
        for(int i = 0; i < menu.size(); i++) {
            if(i==1) continue;
            menu.getItem(i).setEnabled(true);
        }
    }
    private void showRemoveSnackBar(Plan plan) {
        Snackbar.make(binding.getRoot(), "Plan removed", Snackbar.LENGTH_SHORT)
                .setAction("Undo", view -> dateItemsViewModel.removePlanForCurrDay(plan))
                .show();
    }
}
