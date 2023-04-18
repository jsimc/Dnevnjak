package com.example.dnevnjak20.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dnevnjak20.model.DateItem;
import com.example.dnevnjak20.model.Plan;
import com.example.dnevnjak20.model.enums.ObligationPriority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateItemsViewModel extends ViewModel {

    public static int counter = 10000;

    private final MutableLiveData<List<DateItem>> dates = new MutableLiveData<>();
    private final MutableLiveData<List<Plan>> plansForTheDay = new MutableLiveData<>();
    private final MutableLiveData<Integer> focusedPosition = new MutableLiveData<>();
    private List<DateItem> dateItemList;

//    static {
//        new DateItemsViewModel();
//    }

    public DateItemsViewModel() {
        initDateItems();
        // We are doing this because cars.setValue in the background is first checking if the reference on the object is same
        // and if it is it will not do notifyAll. By creating a new list, we get the new reference everytime
        List<DateItem> listToSubmit = new ArrayList<>(dateItemList);
        dates.setValue(listToSubmit);
        focusedPosition.setValue(getPositionForDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear()));
        plansForTheDay.setValue(new ArrayList<>(getDateItemAtPosition(focusedPosition.getValue()).getDailyPlans()));
    }

    private void initDateItems() {
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
            DateItem dateItemToAdd = new DateItem(day, month, year, i);
            //TODO kako da ovo ubacis u bazu da se sacuvaju vrednosti koje si vec ubacivala. Moraces da prolazis kroz bazu i da stavljas iteme iz baze
//            System.out.println("ADDED PLAN : " + dateItemToAdd.addPlan(new Plan()));
            dateItemList.add(dateItemToAdd);
            day = day%danPoModulu + 1;
            month = day == 1 ? month%12 + 1 : month;
            year = day == 1 ? (month == 1 ? year+1: year) : year;
        }
    }

    public MutableLiveData<List<DateItem>> getDates() {
        return dates;
    }

    public MutableLiveData<List<Plan>> getPlansForTheDay() {
        return plansForTheDay;
    }

    public void setPlansForTheDay(List<Plan> plansToSubmit) {
        this.plansForTheDay.setValue(new ArrayList<>(plansToSubmit));
    }

    public LiveData<Integer> getFocusedPosition() {
        return focusedPosition;
    }

    public void setFocusedPosition(int newPosition) {
        focusedPosition.setValue(newPosition);
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
    public DateItem getCurrentDateItem() {
        return getDateItemAtPosition(getFocusedPosition().getValue());
    }

    // Filter plans for the day, day is obtained using focusedPosition
    public void filterPlans(ObligationPriority obligationPriority) {
        DateItem dateItem = getDateItemAtPosition(focusedPosition.getValue());
        // ako je kliknuto dugme ALL vraca samo sve dane
        if(obligationPriority==ObligationPriority.ALL) {
            plansForTheDay.setValue(new ArrayList<>(dateItem.getDailyPlans()));
            return;
        }
        // filtrira planove za taj dan na osnovu stepena obaveze
        List<Plan> listToSubmit = new ArrayList<>(
                dateItem.getDailyPlans().stream()
                .filter(plan -> plan.getPriority().equals(obligationPriority))
                .collect(Collectors.toList()));
        plansForTheDay.setValue(listToSubmit);
    }
    public void filterPlans(LocalDateTime now) {
        DateItem dateItem = getDateItemAtPosition(focusedPosition.getValue());
        List<Plan> listToSubmit = new ArrayList<>(
                dateItem.getDailyPlans().stream()
                        .filter(plan -> plan.getPlanDate().isAfter(now.toLocalDate())
                                    || (plan.getPlanDate().equals(now.toLocalDate()) &&
                                        plan.getPlanTimeTo().isAfter(now.toLocalTime())))
                        .collect(Collectors.toList()));
        plansForTheDay.setValue(listToSubmit);
    }

    public boolean addPlanForCurrDay(Plan plan) {
        if (!getCurrentDateItem().addPlan(plan)) return false;
        this.plansForTheDay.setValue(new ArrayList<>(getCurrentDateItem().getDailyPlans()));
        return true;
    }

    public boolean removePlanForCurrDay(Plan plan) {
        boolean isDeleted = getCurrentDateItem().getDailyPlans().remove(plan);
        this.plansForTheDay.setValue(new ArrayList<>(getCurrentDateItem().getDailyPlans()));
        return isDeleted;
    }

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
