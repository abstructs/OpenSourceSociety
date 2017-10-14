package model;
import java.util.ArrayList;
public class Meeting extends Entry {

    private ArrayList<String> attendees;

    public Meeting(Date date, Time time, String label) {
        super(date,time,label);
        attendees = new ArrayList<>();
    }

    public ArrayList<String> getAttendees() {
        return attendees;
    }

    // REQUIRES: attendee must not have been added before
    // MODIFIES: this
    // EFFECTS: adds an attendee to the list
    public void addAttendee(String attendee) {
        for(String a : this.attendees) {
            if (a.equals(attendee)) {
                return;
            }
        }

        attendees.add(attendee);
    }

    // REQUIRES: attendee must have been added before
    // MODIFIES: this
    // EFFECTS: removes an attendee to the list
    public void removeAttendee(String attendee) {
        attendees.remove(attendee);
    }

    // EFFECTS: "sends invites"
    public void sendInvites() {
        for(String attendee : this.attendees) {
            System.out.println("Inviting: " + attendee);
        }
    }
}
