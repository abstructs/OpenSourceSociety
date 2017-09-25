package test;
import model.Date;
import model.Event;
import model.Reminder;
import model.Time;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import static org.junit.Assert.*;

public class EventTest {
    private Event testEvent;
    private Reminder testReminder1;
    private Reminder testReminder2;

    @Before
    public void setStuff() {
        Date testDate = new Date();
        testDate.setDate(LocalDate.of(testDate.getDate().getYear() + 1, 1, 1));
        testReminder1 = new Reminder(testDate, new Time(), "Chores", "Wash dishes");
        testReminder2 = new Reminder(testDate, new Time(), "Party", "Yeah");
        testEvent = new Event(testDate, new Time(), "Testing!", testReminder1);
    }
    @Test
    public void setsReminder() {
        testEvent.setReminder(testReminder2);
        assertEquals(testEvent.getReminder(), testReminder2);
    }

    @Test
    public void getsReminder() {
        assertEquals(testEvent.getReminder(), testReminder1);
    }
}
