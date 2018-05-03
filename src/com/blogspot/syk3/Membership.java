package com.blogspot.syk3;

import java.util.Calendar;
import java.util.List;

public abstract class Membership {
    // private Calendar startDate = Calendar.getInstance();
    private Calendar startDate;
    private GymType gymType;

    // Constructor
    public Membership(Calendar startDate, GymType gymType) {
        this.startDate = startDate;
        this.gymType = gymType;
    }

    public abstract List<Member> getMembers();

    // startDate getter
    public Calendar getStartDate() {
        return startDate;
    }

    // startDate setter
    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    // gymType getter
    public GymType getGymType() {
        return gymType;
    }

    // gymType setter
    public void setGymType(GymType gymType) {
        this.gymType = gymType;
    }
}
