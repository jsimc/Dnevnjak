package com.example.dnevnjak20;

import static com.example.dnevnjak20.LoginActivity.USER_EMAIL;
import static com.example.dnevnjak20.LoginActivity.USER_LOGGED_IN;
import static com.example.dnevnjak20.LoginActivity.USER_NAME;
import static com.example.dnevnjak20.LoginActivity.USER_PASS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.core.splashscreen.SplashScreen;

import com.example.dnevnjak20.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;

    private TextView textView;
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
            String email = sharedPreferences.getString(USER_EMAIL, null);
            String username = sharedPreferences.getString(USER_NAME, null);
            String password = sharedPreferences.getString(USER_PASS, null);
            textView.setText(username);
        }

    }
    private void init() {
        textView = binding.textView2;
    }
}