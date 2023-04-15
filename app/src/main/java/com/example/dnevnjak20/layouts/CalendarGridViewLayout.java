package com.example.dnevnjak20.layouts;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.dnevnjak20.model.DateItem;

import java.util.ArrayList;
import java.util.List;

public class CalendarGridViewLayout extends GridView {

    private List<DateItem> dateItemList;

    public CalendarGridViewLayout(Context context) {
        super(context);
        dateItemList = new ArrayList<>();
        int day = 1;
        int month = 1;
        int year = 2001;
        int danPoModulu;
        for(int i = 0; i < 1000; i++) {
            switch (month) {
                case 2: danPoModulu = 28; break;
                case 1: case 3: case 5: case 7: case 8: case 10: case 12: danPoModulu = 31; break;
                default: danPoModulu = 30;
            }
            dateItemList.add(new DateItem(day, month, year));
            day = (day+1)%danPoModulu; month = (month+1)%12; year+=1;
        }

    }


}
