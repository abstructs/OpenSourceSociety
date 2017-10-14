package model;

public abstract class Entry {

    private Time time;
    private Date date;
    private String label;
    private int interval;
    private Boolean isRepeating;

    // REQUIRES: date and time must be set in the future
    public Entry(Date date, Time time, String label) {
        Date todaysDate = new Date();
        Time todaysTime = new Time();
        assert(date.gr(todaysDate));
        if (date.eq(todaysDate)) { assert(!time.gr(todaysTime)); }
        this.date = date;
        this.time = time;
        this.label = label;
    }

    public Date getDate() {
        return date;
    }

    public String getLabel() {
        return label;
    }

    public Time getTime() {
        return time;
    }

    public Boolean isRepeating() {
        return isRepeating;
    }

    public void setRepeating(Boolean repeating) {
        isRepeating = repeating;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
