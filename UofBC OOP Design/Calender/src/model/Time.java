package model;

public class Time {
    private int hour;
    private int minutes;
    private String meridiem;

    public Time(int hour, int minutes, String meridiem) {
        this.hour = hour;
        this.minutes = minutes;
        this.meridiem = meridiem;
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

    public String timeIn12() {
        return Integer.toString(hour + 12 % 24) + meridiem;
    }

    public String timeIn24() {
        nextMeridiem();
        return Integer.toString(hour) + meridiem;
    }
}
