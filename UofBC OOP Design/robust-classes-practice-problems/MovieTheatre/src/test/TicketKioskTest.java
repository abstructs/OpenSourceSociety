package test;

import model.Movie;
import model.MovieGoer;
import model.Ticket;
import model.TicketKiosk;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicketKioskTest {

    private Movie m1, m2;
    private MovieGoer mg1, mg2;
    private Ticket t1, t2;
    private TicketKiosk tk;

    @Before
    public void setUp() {
        tk = new TicketKiosk("Scotiabank Theatre");
        mg1 = new MovieGoer("Tom Hanks", 28, tk);
        mg2 = new MovieGoer("Sarah Johnson", 17, tk);
        m1 = new Movie("Arrival", 19, 50);
        m2 = new Movie("Ratatouille", 0, 100);
        t1 = new Ticket(m1);
        t2 = new Ticket(m2);
    }

    @Test
    public void testGetters() {
        assertEquals(tk.getName(),"Scotiabank Theatre");
        assertTrue(tk.addMovie(m1));
        assertTrue(tk.getMovies().contains(m1));
        assertFalse(tk.addMovie(m1));
        assertEquals(tk.getMovies().size(), 1);
    }

    @Test
    public void testaddMovie() {
        assertTrue(tk.addMovie(m2));
        assertTrue(tk.addMovie(m1));
        assertEquals(tk.getMovies().size(),2);
        assertTrue(tk.getMovies().contains(m1));
        assertTrue(tk.getMovies().contains(m2));
    }

    @Test
    public void testsellTicket() {
        assertTrue(tk.sellTicket(mg1, m1));
        assertEquals(mg1.getTicket(), t1);
    }


}