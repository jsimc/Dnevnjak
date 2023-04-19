package com.example.dnevnjak20.listeners;

import android.text.Editable;
import android.text.TextWatcher;

import com.example.dnevnjak20.view_models.DateItemsViewModel;

public class SearchTextWatcher implements TextWatcher {
    DateItemsViewModel dateItemsViewModel;

    public SearchTextWatcher(DateItemsViewModel dateItemsViewModel) {
        this.dateItemsViewModel = dateItemsViewModel;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        dateItemsViewModel.filterPlans(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
