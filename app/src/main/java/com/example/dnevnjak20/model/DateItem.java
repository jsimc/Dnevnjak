package com.example.dnevnjak20.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DateItem {
    private int id;
    private int day;
    private int month;
    private int year;

    private int position;

    private List<Plan> dailyPlans;

    public DateItem(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
        dailyPlans = new ArrayList<>();
    }
    public DateItem(int day, int month, int year, int position) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.position = position;
        dailyPlans = new ArrayList<>();
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public List<Plan> getDailyPlans() {
        return dailyPlans;
    }

    public boolean addPlan(Plan plan) {
        //TODO add plan ukoliko termin nije zauzet???
        //proveri jel radi
        if(dailyPlans.stream().noneMatch(plan1 -> plan1.getPlanTimeFrom().isAfter(plan.getPlanTimeTo()) ||
                plan1.getPlanTimeTo().isBefore(plan.getPlanTimeFrom())))
            return false;
        dailyPlans.add(plan);
        return true;
    }

    @Override
    public String toString() {
        return "DateItem{" +
                "id=" + id +
                ", day=" + day +
                ", month=" + month +
                ", year=" + year +
                ", dailyPlans=" + dailyPlans +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateItem dateItem = (DateItem) o;
        //TODO kad ubacis SQLite odkomentarisi ovo id == dateItem.id
        return /*id == dateItem.id && */
                day == dateItem.day && month == dateItem.month && year == dateItem.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, month, year);
    }
}
