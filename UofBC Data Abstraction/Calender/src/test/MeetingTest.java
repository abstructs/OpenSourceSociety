package test;

import model.Meeting;
import org.junit.Before;
import model.Date;
import model.Time;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.*;

public class MeetingTest {
    private Meeting meeting;
    private String testAttendee;
    private Date testDate;

    @Before
    public void setStuff() {
        testDate = new Date();
        testDate.setDate(LocalDate.of(testDate.getDate().getYear() + 1, 1, 1));
        meeting = new Meeting(testDate, new Time(), "Test Meeting");
        testAttendee = "Andrew Wilson";
    }

    @Test
    public void addsAttendee() {
        meeting.addAttendee(testAttendee);
        assertTrue(meeting.getAttendees().contains(testAttendee));
    }

    @Test
    public void removesAttendee() {
        meeting.addAttendee(testAttendee);
        meeting.removeAttendee(testAttendee);
        assertTrue(!meeting.getAttendees().contains(testAttendee));
    }
    @Test
    public void doesntRemoveWhenAttendeeDoesntExist() {
        meeting.removeAttendee(testAttendee);
        assertFalse(meeting.getAttendees().contains(testAttendee));
        assertFalse(meeting.getAttendees().remove(testAttendee));
    }
}
