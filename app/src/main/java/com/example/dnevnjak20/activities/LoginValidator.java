package com.example.dnevnjak20.activities;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.model.User;
import com.example.dnevnjak20.sqlite.DatabaseHelper;

public class LoginValidator {
    private static LoginValidator instance;
    private DatabaseHelper dbHelper;
    private User user;

    private LoginValidator() {}

    public static LoginValidator getInstance() {
        if (instance == null) {
            instance = new LoginValidator();
        }
        return instance;
    }

    public boolean validate(String username, String email, String password, LoginActivity loginActivity) {
        boolean flagIsValid = true;
        if (!validateEmail(email, loginActivity)) {
//            loginFormState.setValue(new LoginFormState(R.string.invalid_email, null,  null));
            flagIsValid = false;
        }
        if (!validateUsername(username, loginActivity)) {
//            loginFormState.setValue(new LoginFormState(null, R.string.invalid_username, null));
            flagIsValid = false;
        }
        if (!validatePassword(password, loginActivity)) {
//            loginFormState.setValue(new LoginFormState(null, null, R.string.invalid_password));
            flagIsValid = false;
        }
        return flagIsValid;
    }

    public boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return !username.trim().isEmpty();
    }

    public boolean isEmailValid(String email) {
        return email != null && email.matches("^[A-Za-z\\d]+@[A-Za-z]+\\.[A-Za-z]{2,}$");
    }

    // A placeholder password validation check
    public boolean isPasswordValid(String password) {
        if(password == null || password.trim().length() < 5) return false;
        return password.matches("^[^~#^|$%&*!]*$");
    }

    public boolean validateEmail(String email, LoginActivity loginActivity) {
        return isEmailValid(email);
    }

    public boolean validateUsername(String username, LoginActivity loginActivity) {
        return isUserNameValid(username);
    }

    public boolean validatePassword(String password, LoginActivity loginActivity) {
        return isPasswordValid(password);
    }

    public void setDbHelper(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
}
