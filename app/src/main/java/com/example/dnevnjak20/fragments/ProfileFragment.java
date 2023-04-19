package com.example.dnevnjak20.fragments;

import static android.app.Activity.RESULT_OK;
import static com.example.dnevnjak20.activities.LoginActivity.USER_EMAIL;
import static com.example.dnevnjak20.activities.LoginActivity.USER_NAME;
import static com.example.dnevnjak20.activities.LoginActivity.USER_PASS;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dnevnjak20.R;
import com.example.dnevnjak20.activities.LoginActivity;
import com.example.dnevnjak20.activities.MainActivity;
import com.example.dnevnjak20.databinding.FragmentDailyPlanBinding;
import com.example.dnevnjak20.databinding.FragmentProfileBinding;
import com.example.dnevnjak20.view_models.DateItemsViewModel;

import java.io.File;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }
    private void init() {
        this.sharedPreferences = this.getActivity().getSharedPreferences(getString(R.string.preference_logged_in), Context.MODE_PRIVATE);
        binding.userNameTv.setText(sharedPreferences.getString(USER_NAME, "Username1"));
        binding.userEmailTv.setText(sharedPreferences.getString(USER_EMAIL, "Email1"));
        initListeners();
    }

    private void initListeners() {
        ///// change password //////
        binding.changePasswordBtn.setOnClickListener(v -> {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
            alertDialog.setTitle("Values");
            EditText oldPass = new EditText(requireContext());
            EditText newPass = new EditText(requireContext());
            EditText confirmPass = new EditText(requireContext());


            oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

            oldPass.setHint("Old Password");
            newPass.setHint("New Password");
            confirmPass.setHint("Confirm Password");
            LinearLayout ll=new LinearLayout(requireContext());
            ll.setOrientation(LinearLayout.VERTICAL);

            ll.addView(oldPass);

            ll.addView(newPass);
            ll.addView(confirmPass);
            alertDialog.setView(ll);
            alertDialog.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // TODO change password for user, find by username
                            String email = sharedPreferences.getString(USER_EMAIL, "");
                            String username = sharedPreferences.getString(USER_NAME, "");
                            String password = sharedPreferences.getString(USER_PASS, "");
                            if(!password.equals(oldPass.getText().toString())) {
                                Toast.makeText(requireContext(), "Wrong old password!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(requireContext(), ""+((MainActivity)getActivity()).getDbHelper().setNewPassword(
                                    username, email, password, newPass.getText().toString()
                            ), Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
            alertDialog.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = alertDialog.create();
            alert11.show();
        });
        ////// log out ///////////
        binding.logOutBtn.setOnClickListener(v -> {
            // brisemo info u shared preferences
            sharedPreferences.edit().clear().apply();
            // vracamo na login activity
            this.getActivity().finish();
            startActivity(new Intent(this.getActivity(), LoginActivity.class));
        });
    }
}