package com.example.dnevnjak20;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.dnevnjak20.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    public static final String USER_LOGGED_IN = "userLoggedIn";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_NAME = "userName";
    public static final String USER_PASS = "userPassword";
    private SharedPreferences sharedPreferences;
    private LoginValidator loginValidator;
    private SharedPreferences.Editor edit;
    private ActivityLoginBinding binding;
    private EditText emailEt;
    private EditText usernameEt;
    private EditText passwordEt;
    private Button loginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences(getString(R.string.preference_logged_in), Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
        loginValidator = LoginValidator.getInstance();
        emailEt = binding.email;
        usernameEt = binding.username;
        passwordEt = binding.password;
        loginBtn = binding.login;
        initListeners();
    }

    private void initListeners() {
        loginBtn.setOnClickListener(view -> {
            String username = usernameEt.getText().toString();
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();

            if(!loginValidator.validateEmail(email, this)) {
                emailEt.setError(getString(R.string.invalid_email));
            }
            if(!loginValidator.validateUsername(username, this)) {
                usernameEt.setError(getString(R.string.invalid_username));
            }
            if(!loginValidator.validatePassword(password, this)) {
                passwordEt.setError(getString(R.string.invalid_password));
            }
            if(loginValidator.validate(username, email, password, this)) {
                edit.putBoolean(USER_LOGGED_IN, true);
                edit.putString(USER_EMAIL, email);
                edit.putString(USER_NAME, username);
                edit.putString(USER_PASS, password);
                edit.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        });
    }
}
