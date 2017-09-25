package ui;
import model.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Date testDate = new Date();
        testDate.setDate(LocalDate.of(testDate.getDate().getYear() + 1, 1, 1));
        Calender calender = new Calender(new Date());
        calender.setEmail("andrew@gmail.com");
        calender.addEntry(new Meeting(testDate, new Time(), "UX Meeting"));
        calender.addEntry(new Meeting(testDate, new Time(), "Software Design Meeting"));
        calender.addEntry(new Reminder(testDate, new Time(), "Gym", "Workout"));
        calender.addEntry(new Reminder(testDate, new Time(), "Gym", "Go for a run"));
        calender.addEntry(new Event(testDate, new Time(), "Party", new Reminder(new Date(), new Time(), "Groceries", "Buy cake")));
        calender.addEntry(new Event(testDate, new Time(), "Cleanup", new Reminder(new Date(), new Time(), "Get supplies", "Remember cat litter")));
        calender.addEntry(new Event(testDate, new Time(), "Finish course", new Reminder(new Date(), new Time(), "Buy cheese", "It's important!")));

        for (Entry e : calender.getEntries()) {
            System.out.println("Entry: " + e.getLabel());
        }

        Meeting testMeeting = new Meeting(testDate, new Time(), "UX Meeting");
        testMeeting.addAttendee("Andrew");
        testMeeting.addAttendee("Dan");
        testMeeting.sendInvites();

        testMeeting.removeAttendee("Maz");
    }
}
