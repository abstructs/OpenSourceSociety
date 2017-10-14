package test;
import model.Date;
import model.Reminder;
import model.Time;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ReminderTest {
    private Reminder reminder;
    private Date testDate;
    private String testNote;
    private String additionalNote;

    @Before
    public void setStuff() {
        testDate = new Date();
        testDate.setDate(LocalDate.of(testDate.getDate().getYear() + 1, 1, 1));
        testNote = "Remember to do stuff";
        additionalNote = "Remember to more do stuff";
        reminder = new Reminder(testDate, new Time(), "Test Reminder", testNote);
    }

    @Test
    public void setsAndGetsAdditionalNote() {
        reminder.setAdditionalNote(additionalNote);
        assertEquals(reminder.getAdditionalNote(), additionalNote);
    }
}
