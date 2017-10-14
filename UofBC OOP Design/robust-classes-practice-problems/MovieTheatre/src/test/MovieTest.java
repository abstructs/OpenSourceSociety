package test;

import model.Movie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    private Movie m1, m2;

    @Before
    public void setUp() {
        m1 = new Movie("Alien: Covenant", 18, 100);
        m2 = new Movie("Ratatouille", 0, 120);
    }

    @Test
    public void testGetters() {
        assertEquals(m1.getTitle(),"Alien: Covenant");
        assertEquals(m2.getTitle(),"Ratatouille");
        assertEquals(m1.getAgeRestriction(),18);
        assertEquals(m2.getAgeRestriction(),0);
        assertEquals(m1.getMaxSeating(),100);
        assertEquals(m2.getMaxSeating(),120);
    }

    @Test
    public void testisFull() {
        assertFalse(m1.isFull());

        while (!m1.isFull()) {
            m1.addViewer();
        }

        assertTrue(m1.isFull());
        assertEquals(m1.getCurrentSeating(),100);
    }


}