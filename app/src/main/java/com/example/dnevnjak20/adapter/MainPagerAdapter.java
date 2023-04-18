package com.example.dnevnjak20.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dnevnjak20.fragments.CalendarFragment;
import com.example.dnevnjak20.fragments.DailyPlanFragment;
import com.example.dnevnjak20.fragments.FragmentHolder;
import com.example.dnevnjak20.fragments.ProfileFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"Calendar", "Daily Plan", "Profile"};
    private final int ITEM_COUNT = 3;
    public static final int CALENDAR_FRAGMENT = 0;
    public static final int DAILY_PLAN_FRAGMENT = 1;
    public static final int PROFILE_FRAGMENT = 2;

    private Fragment calendarFragment;
    private Fragment dailyPlanFragmet;
    private Fragment profileFragment;


    public MainPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        calendarFragment = new CalendarFragment();
        dailyPlanFragmet = new FragmentHolder();
        profileFragment = new ProfileFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CALENDAR_FRAGMENT: return calendarFragment;
            case DAILY_PLAN_FRAGMENT: return dailyPlanFragmet;
            case PROFILE_FRAGMENT: return profileFragment;
            default: return calendarFragment;
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
