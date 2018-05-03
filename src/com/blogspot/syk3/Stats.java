package com.blogspot.syk3;

public class Stats {
    private int backSquat;
    private int deadlift;
    private int benchPress;
    private int dip;
    private int overheadPress;
    private int pullUp;

    // backSquat getter
    public int getBackSquat() {
        return backSquat;
    }

    // backSquat setter
    public Stats setBackSquat(int backSquat) {
        this.backSquat = backSquat;
        return this;
    }

    // deadlift getter
    public int getDeadlift() {
        return deadlift;
    }

    // deadlift setter
    public Stats setDeadlift(int deadlift) {
        this.deadlift = deadlift;
        return this;
    }

    // benchPress getter
    public int getBenchPress() {
        return benchPress;
    }

    // benchPress setter
    public Stats setBenchPress(int benchPress) {
        this.benchPress = benchPress;
        return this;
    }

    // dip getter
    public int getDip() {
        return dip;
    }

    // dip setter
    public Stats setDip(int dip) {
        this.dip = dip;
        return this;
    }

    // overheadPress getter
    public int getOverheadPress() {
        return overheadPress;
    }

    // overheadPress setter
    public Stats setOverheadPress(int overheadPress) {
        this.overheadPress = overheadPress;
        return this;
    }

    // pullUp getter
    public int getPullUp() {
        return pullUp;
    }

    // pullUp setter
    public Stats setPullUp(int pullUp) {
        this.pullUp = pullUp;
        return this;
    }

    // when printing stats as String
    public String toString() {
        return "Squat: " + this.backSquat + ", Deadlift: " + this.deadlift + ", Bench Press: " + this.benchPress +
                ", Dip: " + this.dip + ", Overhead Press: " + this.overheadPress + ", Pull-Up: " + this.pullUp;
    }
}
