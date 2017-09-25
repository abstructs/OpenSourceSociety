package model;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class Date {
    private LocalDate date;

    public Date() {
        this.date = LocalDate.now();
    }

    public Date(String year, String month, String day) {
        this.date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
    }

    public Date(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // EFFECTS: returns whether the date is greater than
    public Boolean gr(Date other) {
        return this.date.compareTo(other.getDate()) > 0;
    }

    // EFFECTS: returns whether the date is less than
    public Boolean lt(Date other) {
        return this.date.compareTo(other.getDate()) < 0;
    }

    // EFFECTS: returns whether the date is equal
    public Boolean eq(Date other) {
        return this.date.compareTo(other.getDate()) == 0;
    }

    // EFFECTS: returns the date in a short format
    public String getShortFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-uu");
        return formatter.format(this.date);
    }

    // EFFECTS: returns the date in a long format
    public String getLongFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, uuuu");
        return formatter.format(this.date);
    }
}
