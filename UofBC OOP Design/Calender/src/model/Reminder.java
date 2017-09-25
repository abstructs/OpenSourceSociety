package model;

public class Reminder extends Entry {
    private String note;
    private String additionalNote;

    public Reminder(Date date, Time time, String label, String note) {
        super(date,time,label);
        this.note = note;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }
}
