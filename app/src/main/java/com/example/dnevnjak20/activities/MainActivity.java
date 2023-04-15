package com.example.dnevnjak20.activities;

import static com.example.dnevnjak20.activities.LoginActivity.USER_EMAIL;
import static com.example.dnevnjak20.activities.LoginActivity.USER_LOGGED_IN;
import static com.example.dnevnjak20.activities.LoginActivity.USER_NAME;
import static com.example.dnevnjak20.activities.LoginActivity.USER_PASS;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.adapter.MainPagerAdapter;
import com.example.dnevnjak20.databinding.ActivityMainBinding;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private ViewPager viewPager;

    private DateItemsViewModel dateItemsViewModel;

    private boolean keep = true;
    private final int DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this).setKeepOnScreenCondition(()->keep);
        new Handler().postDelayed(()->keep = false, DELAY);


        sharedPreferences = getSharedPreferences(getString(R.string.preference_logged_in), Context.MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean(USER_LOGGED_IN, false);
        if(!loggedIn) {
            //ako nije bio ulogovan...
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            //ako je vec bio ulogovan
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            init();
            initListeners();
            String email = sharedPreferences.getString(USER_EMAIL, null);
            String username = sharedPreferences.getString(USER_NAME, null);
            String password = sharedPreferences.getString(USER_PASS, null);
        }

    }
    private void init() {
        viewPager = binding.viewPager;
        dateItemsViewModel = new ViewModelProvider(this)
                .get(DateItemsViewModel.class);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
    }

//    private void initObservers() {
//        // javljamo adapteru da
//        dateItemsViewModel.getDates().observe(this, dates -> {
//            dateAdapter.submitList(dates);
//        });
//
//    }

    private void initListeners() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                binding.bottomNavigation.getMenu().getItem(position).setChecked(true);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        initNavigation();
    }
    @SuppressLint("NonConstantResourceId")
    private void initNavigation() {
        (binding.bottomNavigation).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.navigation_1: viewPager.setCurrentItem(MainPagerAdapter.CALENDAR_FRAGMENT, false); break;
                case R.id.navigation_2: viewPager.setCurrentItem(MainPagerAdapter.DAILY_PLAN_FRAGMENT, false); break;
                case R.id.navigation_3: viewPager.setCurrentItem(MainPagerAdapter.PROFILE_FRAGMENT, false); break;
            }
            return true;
        });
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}