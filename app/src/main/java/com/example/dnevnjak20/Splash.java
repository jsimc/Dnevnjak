package com.example.dnevnjak20;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

public class Splash extends ViewModel {

        private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(true);

        public Splash() {
            try {
                Thread.sleep(1000);
                isLoading.setValue(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @NotNull
        public LiveData<Boolean> isLoading() {
            return isLoading;
        }

}
