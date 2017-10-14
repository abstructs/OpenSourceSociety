package test;
import model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class EntryTest {
    private Reminder testReminder;
    private Entry testEvent;
    private Entry testMeeting;

    @Before
    public void setStuff() {
        Date testDate = new Date();
        testDate.setDate(LocalDate.of(testDate.getDate().getYear() + 1, 1, 1));
        testReminder = new Reminder(testDate, new Time(), "Test Reminder", "Wash dishes");
        testEvent = new Event(testDate, new Time(), "Test Event", testReminder);
        testMeeting = new Meeting(testDate, new Time(), "Testing!");
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(testReminder.getLabel(), "Test Reminder");
        assertEquals(testMeeting.getLabel(), "Test Meeting");
        assertEquals(testEvent.getLabel(), "Test Event");

        testReminder.setRepeating(true);
        testMeeting.setRepeating(true);
        testEvent.setRepeating(false);

        assertEquals(testReminder.isRepeating(), true);
        assertEquals(testMeeting.isRepeating(), true);
        assertEquals(testEvent.isRepeating(), false);

        testReminder.setInterval(5);
        testMeeting.setInterval(2);
        testEvent.setInterval(3);

        assertEquals(testReminder.getInterval(), 5);
        assertEquals(testMeeting.getInterval(), 2);
        assertEquals(testEvent.getInterval(), 3);
    }
}
