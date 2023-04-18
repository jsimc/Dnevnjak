package com.example.dnevnjak20.model;

import com.example.dnevnjak20.model.enums.ObligationPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class Plan {
    private int id;
    // green, yellow or red
    private ObligationPriority priority;
    // more info about the plan
    private String longInfo;
    // name of the plan
    private String name;

    // is this good way of storing dates? should I use string or Instant?
    private LocalDate planDate; //date of the plan
    private LocalTime planTimeFrom; //start of the plan in hours and minutes
    private LocalTime planTimeTo; //end of the plan in hours and minutes, optional???

    public Plan() {
        name = "Pablo";
        planTimeFrom = LocalTime.of(19, 15);
        planTimeTo = LocalTime.of(20, 15);
        planDate = LocalDate.of(2023, 4, 20);
        priority = ObligationPriority.LOW_PRIORITY;
        longInfo = "long info";
    }

    public Plan(ObligationPriority priority) {
        this.priority = priority;
    }

    public ObligationPriority getPriority() {
        return priority;
    }

    public String getLongInfo() {
        return longInfo;
    }

    public String getName() {
        return name;
    }

    public LocalDate getPlanDate() {
        return planDate;
    }

    public LocalTime getPlanTimeFrom() {
        return planTimeFrom;
    }

    public LocalTime getPlanTimeTo() {
        return planTimeTo;
    }

    public int getId() {
        return id;
    }

    public void setPriority(ObligationPriority priority) {
        this.priority = priority;
    }

    public void setLongInfo(String longInfo) {
        this.longInfo = longInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate = planDate;
    }

    public void setPlanTimeFrom(LocalTime planTimeFrom) {
        this.planTimeFrom = planTimeFrom;
    }

    public void setPlanTimeTo(LocalTime planTimeTo) {
        this.planTimeTo = planTimeTo;
    }

    // da li se vremena podudaraju, ne vraca bas DA LI JE EQUALS, nego da li su u istom vremenskom periodu
    public boolean sameTime(Plan plan1) {
        if(!planDate.equals(plan1.getPlanDate())) return false;
        return !planTimeFrom.isAfter(plan1.getPlanTimeTo())
                && !planTimeTo.isBefore(plan1.getPlanTimeFrom());
    }

    @Override
    public String toString() {
        return "Plan{" +
                "name='" + name + '\'' +
                ", planDate=" + planDate +
                ", planTimeFrom=" + planTimeFrom +
                ", planTimeTo=" + planTimeTo +
                '}';
    }
}
