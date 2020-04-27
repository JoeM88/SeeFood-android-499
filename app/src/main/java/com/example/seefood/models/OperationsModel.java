package com.example.seefood.models;

public class OperationsModel {
    private int openHour;
    private int openMins;
    private int durHours;
    private int durMins;

    public OperationsModel(){/*Empty Constructor*/}

    public OperationsModel(int openHour, int openMins, int durHours, int durMins){
        this.openHour = openHour;
        this.openMins = openMins;
        this.durHours = durHours;
        this.durMins = durMins;
    }

    public int getOpenHour() {
        return openHour;
    }

    public void setOpenHour(int openHour) {
        this.openHour = openHour;
    }

    public int getOpenMins() {
        return openMins;
    }

    public void setOpenMins(int openMins) {
        this.openMins = openMins;
    }

    public int getDurHours() {
        return durHours;
    }

    public void setDurHours(int durHours) {
        this.durHours = durHours;
    }

    public int getDurMins() {
        return durMins;
    }

    public void setDurMins(int durMins) {
        this.durMins = durMins;
    }
}
