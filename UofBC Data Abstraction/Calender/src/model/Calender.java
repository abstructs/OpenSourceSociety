package model;

import java.util.ArrayList;

public class Calender {
    private String email;
    private Date currentDate;
    private ArrayList<Entry> entries;
    public Calender(Date date) {
        currentDate = date;
        entries = new ArrayList<Entry>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    // REQUIRES: new entry should not have been added before
    public void addEntry(Entry entry) {
        for(Entry e : this.entries) {
            if (e.equals(entry)) {
                return;
            }
        }

        entries.add(entry);
    }

    // REQUIRES: at least one entry should be added
    public void removeEntry(Entry entry) {
        entries.remove(entry);
    }

    // REQUIRES: at least one entry should be added
    public Entry getNextEntry() {
        return entries.get(0);
    }
}
