package com.blogspot.syk3;

import java.util.Calendar;

public class Member {
    // attributes of Member object
    private String name;
    private Calendar birthdate;
    private Gender gender;
    private Stats stats;
    private double weight;

    // Constructor
    public Member(String name, Calendar member1Birthdate, Gender gender, Stats stats, double weight) {
        this.name = name;
        this.birthdate = member1Birthdate;
        this.gender = gender;
        this.stats = stats;
        this.weight = weight;
    }

    // name getter
    public String getName() {
        return name;
    }

    // name setter
    public void setName(String name) {
        this.name = name;
    }

    // birthdate getter
    public Calendar getBirthdate() {
        return birthdate;
    }

    // birthdate setter
    public void setBirthdate(Calendar birthdate) {
        this.birthdate = birthdate;
    }

    // gender getter
    public Gender getGender() {
        return gender;
    }

    // gender setter
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    // stats getter
    public Stats getStats() {
        return stats;
    }

    // stats setter
    // TODO: Needed?
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    // weight getter
    public double getWeight() {
        return weight;
    }

    // weight setter
    public void setWeight(double weight) {
        this.weight = weight;
    }

    // when printing Member as String
    public String toString() {
        Calendar today = Calendar.getInstance();
        // calculate age based on year
        int age = today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);
        // check if birth month is before current month or if birth day is today or before today
        boolean isBeforeTodayOrToday = today.get(Calendar.MONTH) > birthdate.get(Calendar.MONTH) ||
                (today.get(Calendar.MONTH) == birthdate.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) >=
                        birthdate.get(Calendar.DAY_OF_MONTH));
        if (!isBeforeTodayOrToday) {
            age--;
        }

        return this.name + ", " + this.gender + ", " + this.weight + ", " + age + ", " + this.stats;
    }
}
