package com.example.manipulator_control;

public class Values {
    public String speed;
    public String ascending;
    public String Aascending;

    public Values(String speed, String ascending, String Aascending, String Bascending, String crossbar) {
        this.speed = speed;
        this.ascending = ascending;
        this.Aascending = Aascending;
        this.Bascending = Bascending;
        this.crossbar = crossbar;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getAscending() {
        return ascending;
    }

    public void setAscending(String ascending) {
        this.ascending = ascending;
    }

    public String getAascending() {
        return Aascending;
    }

    public void setAascending(String aascending) {
        Aascending = aascending;
    }

    public String getBascending() {
        return Bascending;
    }

    public void setBascending(String bascending) {
        Bascending = bascending;
    }

    public String getCrossbar() {
        return crossbar;
    }

    public void setCrossbar(String crossbar) {
        this.crossbar = crossbar;
    }

    public String Bascending;
    public String crossbar;
}
