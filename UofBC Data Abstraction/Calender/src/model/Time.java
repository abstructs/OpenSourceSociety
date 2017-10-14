package model;
import java.time.LocalTime;
public class Time {
    private int hour;
    private int minutes;
    private String meridiem;

    public Time(int hour, int minutes, String meridiem) {
        this.hour = hour;
        this.minutes = minutes;
        this.meridiem = meridiem;
    }

    public Time() {
        LocalTime localTime = LocalTime.now();
        this.hour = localTime.getHour();
        this.minutes = localTime.getMinute();
    }

    // MODIFIES: this
    // EFFECTS: if the meridiem is AM changes it to PM and vice versa
    private void nextMeridiem() {
        if (meridiem.equals("AM")) {
            this.meridiem = "PM";
        }
        else {
            this.meridiem = "AM";
        }
    }
    // EFFECTS: returns the time in 12 hours
    public String timeIn12() {
        return Integer.toString(hour + 12 % 24) + meridiem;
    }

    // EFFECTS: returns the time in 24 hours
    public String timeIn24() {
        nextMeridiem();
        return Integer.toString(hour) + meridiem;
    }

    // EFFECTS: returns the hour
    public int getHour() {
        return hour;
    }

    // EFFECTS: returns the minutes
    public int getMinutes() {
        return minutes;
    }

    // EFFECTS: returns whether the time is greater than the time given
    public Boolean gr(Time other) {
        if (this.getHour() == other.getHour()) {
            return this.getMinutes() > other.getMinutes();
        }
        else {
            return this.getHour() > other.getHour();
        }
    }

    // EFFECTS: returns whether the time is less than than the time given
    public Boolean lt(Time other) {
        if (this.getHour() == other.getHour()) {
            return this.getMinutes() < other.getMinutes();
        }
        else {
            return this.getHour() < other.getHour();
        }
    }

    // EFFECTS: returns whether the time is equal than the time given
    public Boolean eq(Time other) {
        return this.getHour() == other.getHour() && this.getMinutes() == other.getMinutes();
    }
}