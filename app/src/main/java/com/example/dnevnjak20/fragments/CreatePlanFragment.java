package com.example.dnevnjak20.fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.databinding.CreatePlanFragmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CreatePlanFragment extends Fragment {
    private CreatePlanFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CreatePlanFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.createPlanHeaderTv.setText("MRNJAU");
        binding.cancelBtn.setOnClickListener(v -> {
            FragmentManager fm = getParentFragmentManager();
            fm.popBackStack();
            Menu menu =((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigation)).getMenu();
            for(int i = 0; i < menu.size(); i++) {
                if(i==1) continue;
                menu.getItem(i).setEnabled(true);
            }
        });
    }

    private void initListeners() {
//        TimePickerDialog.
    }
}
