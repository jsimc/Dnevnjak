package com.example.dnevnjak20.model;

import com.example.dnevnjak20.model.enums.ObligationPriority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Random;

public class Plan implements Comparable<Plan>{
    private int id;
    private transient int dateItemId;
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

    public Plan(String name, ObligationPriority priority, LocalDate planDate, LocalTime planTimeFrom, LocalTime planTimeTo, String longInfo) {
        this.priority = priority;
        this.longInfo = longInfo;
        this.name = name;
        this.planDate = planDate;
        this.planTimeFrom = planTimeFrom;
        this.planTimeTo = planTimeTo;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plan plan = (Plan) o;
        return /*id == plan.id && */
                priority == plan.priority && Objects.equals(name, plan.name) && Objects.equals(planDate, plan.planDate) && Objects.equals(planTimeFrom, plan.planTimeFrom) && Objects.equals(planTimeTo, plan.planTimeTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, priority, name, planDate, planTimeFrom, planTimeTo);
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

    @Override
    public int compareTo(Plan p) {
        if(this.getPlanDate().isEqual(p.planDate)) {
            return this.getPlanTimeFrom().compareTo(p.getPlanTimeFrom());
        }
        return this.getPlanDate().compareTo(p.planDate);
    }
}
