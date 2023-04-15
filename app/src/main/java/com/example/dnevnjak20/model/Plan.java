package com.example.dnevnjak20.model;

import com.example.dnevnjak20.model.enums.ObligationPriority;

import java.time.LocalDate;
import java.time.LocalTime;

public class Plan {
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
}
