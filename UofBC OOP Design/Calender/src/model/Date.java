package model;

import java.util.ArrayList;
import java.util.Arrays;
public class Date {
    private int day;
    private int month;
    private int year;

    private ArrayList<String> months = new ArrayList<>(Arrays.asList(
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    ));

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // EFFECTS: prints the date in a short format
    public String getShortFormat() {
        return day + "-" + month + "-" + Integer.toString(year).substring(2,4);
    }

    // EFFECTS: prints the date in a long format
    public String getLongFormat() {
        return months.get(month - 1) + ", " + day + " " + year;
    }
}
