package com.example.dnevnjak20.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnevnjak20.model.DateItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateItemsViewModel extends ViewModel {

    public static int counter = 10000;

    private final MutableLiveData<List<DateItem>> dates = new MutableLiveData<>();
    private List<DateItem> dateItemList;

    public DateItemsViewModel() {
        dateItemList = new ArrayList<>();
        int day = 1;
        int month = 8;
        int year = 2022;
        int danPoModulu;
        for(int i = 0; i < 1000; i++) {
            switch (month) {
                case 2: danPoModulu = 28; break;
                case 1: case 3: case 5: case 7: case 8: case 10: case 12: danPoModulu = 31; break;
                default: danPoModulu = 30;
            }
            dateItemList.add(new DateItem(day, month, year));
            day = day%danPoModulu + 1;
            month = day == 1 ? month%12 + 1 : month;
            year = day == 1 ? (month == 1 ? year+1: year) : year;
        }
        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
        List<DateItem> listToSubmit = new ArrayList<>(dateItemList);
        dates.setValue(listToSubmit);
    }

    public MutableLiveData<List<DateItem>> getDates() {
        return dates;
    }

    public DateItem getDateItemAtPosition(int position) {
        return dates.getValue().get(position);
    }

    public DateItem getDateItem(int day, int month, int year) {
        return dates.getValue().stream().filter(dateItem -> dateItem.equals(new DateItem(day, month, year)))
                .findFirst().orElse(null);
    }

    public int getPositionForDate(int day, int month, int year) {
        DateItem dateItem = getDateItem(day, month, year);
        return dateItem == null ? -1 : dates.getValue().indexOf(dateItem);
    }
//    public void filterDates(String filter) {
//    }


    //TODO ovde ces da ubacujes planove
//    public int addDate(String pictureUrl, String manufacturer, String model) {
//        int id = counter++;
//        Car car = new Car(id, pictureUrl, manufacturer, model);
//        carList.add(car);
//        ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//        cars.setValue(listToSubmit);
//        return id;
//    }
//
//    public void removeCar(int id) {
//        Optional<Car> carObject = carList.stream().filter(car -> car.getId() == id).findFirst();
//        if (carObject.isPresent()) {
//            carList.remove(carObject.get());
//            ArrayList<Car> listToSubmit = new ArrayList<>(carList);
//            cars.setValue(listToSubmit);
//        }
//    }

}
