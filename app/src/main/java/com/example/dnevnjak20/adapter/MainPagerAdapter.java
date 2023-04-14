package com.example.dnevnjak20.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dnevnjak20.fragments.CalendarFragment;
import com.example.dnevnjak20.fragments.DailyPlanFragment;
import com.example.dnevnjak20.fragments.ProfileFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"Calendar", "Daily Plan", "Profile"};
    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;

    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case FRAGMENT_1: return new CalendarFragment();
            case FRAGMENT_2: return new DailyPlanFragment();
            case FRAGMENT_3: return new ProfileFragment();
            default: return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
