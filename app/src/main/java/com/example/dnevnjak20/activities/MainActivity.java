package com.example.dnevnjak20.activities;

import static com.example.dnevnjak20.activities.LoginActivity.USER_EMAIL;
import static com.example.dnevnjak20.activities.LoginActivity.USER_LOGGED_IN;
import static com.example.dnevnjak20.activities.LoginActivity.USER_NAME;
import static com.example.dnevnjak20.activities.LoginActivity.USER_PASS;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.splashscreen.SplashScreen;
import androidx.viewpager.widget.ViewPager;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.adapter.MainPagerAdapter;
import com.example.dnevnjak20.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;

    private ViewPager viewPager;

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
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
    }

    private void initListeners() {
        initNavigation();
    }
    @SuppressLint("NonConstantResourceId")
    private void initNavigation() {
        (binding.bottomNavigation).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                // setCurrentItem metoda viewPager samo obavesti koji je Item trenutno aktivan i onda metoda getItem u adapteru setuje odredjeni fragment za tu poziciju
                case R.id.navigation_1: viewPager.setCurrentItem(MainPagerAdapter.FRAGMENT_1, false); break;
                case R.id.navigation_2: viewPager.setCurrentItem(MainPagerAdapter.FRAGMENT_2, false); break;
                case R.id.navigation_3: viewPager.setCurrentItem(MainPagerAdapter.FRAGMENT_3, false); break;
            }
            return true;
        });
    }
}