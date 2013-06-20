package com.tuntunkun.chaos.weather;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: naoya sawada
 * Date: 13/06/09
 * Time: 22:13
 * To change this template use File | Settings | File Templates.
 */
public class Weather {
    /*
     * Weather class
     * type     : observation or forecast
     * date     : date of data
     * rainfall : precipitation
     */
    public static enum TYPE {OBSERVATION, FORECAST};
    public TYPE type;
    public Date date;
    public double rainfall;

    /*
     * constructor
     */
    public Weather(TYPE type, Date date, double rainfall) {
        this.type       = type;
        this.date       = date;
        this.rainfall   = rainfall;
    }

    /*
     * Sensor data -> String
     */
    public String toString() {
        return String.format("<weather type:%s, date:%s, rainfall:%f>", type.toString(), date.toString(), rainfall);
    }
}
