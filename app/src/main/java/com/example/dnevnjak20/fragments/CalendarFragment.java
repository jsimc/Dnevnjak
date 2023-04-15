package com.example.dnevnjak20.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.adapter.DateAdapter;
import com.example.dnevnjak20.databinding.CalendarFragmentBinding;
import com.example.dnevnjak20.differ.DateItemDiffCallback;
import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

import java.time.LocalDate;
import java.time.Month;

public class CalendarFragment extends Fragment {

    private DateItemsViewModel dateItemsViewModel;
    private DateAdapter dateAdapter;

    private CalendarFragmentBinding calendarFragmentBinding;
    public CalendarFragment() {
        super(R.layout.calendar_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        calendarFragmentBinding = CalendarFragmentBinding.inflate(getLayoutInflater());
        return calendarFragmentBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        dateItemsViewModel = new ViewModelProvider(this)
                .get(DateItemsViewModel.class);
        initObservers();
        initRecycler();
        initListeners();
    }
    private void initListeners() {
        calendarFragmentBinding.listRv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //nalazimo sredinu kalendara
                GridLayoutManager gridLayoutManager = (GridLayoutManager) ((RecyclerView)v).getLayoutManager();
                int middlePosition = (gridLayoutManager.findFirstVisibleItemPosition() + gridLayoutManager.findLastVisibleItemPosition()) / 2;
                //ako je sredina kalendara u jednom mesecu tako ce i biti naziv gore + godina
                StringBuilder sb = new StringBuilder();
                DateItem dateItem = dateItemsViewModel.getDateItemAtPosition(middlePosition);
                sb.append(Month.of(dateItem.getMonth())).append(" ")
                    .append(dateItem.getYear()).append(".");
                calendarFragmentBinding.headerTv.setText(sb);
            }
        });
    }

    private void initObservers() {
        // javljamo adapteru da
        dateItemsViewModel.getDates().observe(getViewLifecycleOwner(), dates -> {
            dateAdapter.submitList(dates);
        });

    }

    private void initRecycler() {
        dateAdapter = new DateAdapter(new DateItemDiffCallback(), dateItem -> {
            //TODO ovde kad se klikne na dateItem ce da se prebaci na DailyPlan Fragment za taj dan
//            FragmentTransaction transaction =
//            Toast.makeText(getContext(), dateItem.getYear() + "", Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), dateItem+"", Toast.LENGTH_SHORT).show();
//            Toast.makeText(getContext(), Month.of(dateItem.getMonth())+"", Toast.LENGTH_SHORT).show();
        });
        calendarFragmentBinding.listRv.setLayoutManager(new GridLayoutManager(getContext(), 7));
        calendarFragmentBinding.listRv.setAdapter(dateAdapter);
        calendarFragmentBinding.listRv.scrollToPosition(dateItemsViewModel.getPositionForDate(
                LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
    }
}