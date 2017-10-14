package model;

public class MovieGoer {

    private String name;
    private int age;
    private Ticket ticket;
    private TicketKiosk tk;

    public MovieGoer(String name, int age,  TicketKiosk tk) {
        this.name = name;
        this.age = age;
        this.tk = tk;
        ticket = null;
    }

    // getters
    public String getName() {
        // TODO: complete the implementation of this method
        return null;
    }
    public int getAge() {
        // TODO: complete the implementation of this method
        return 0;
    }
    public TicketKiosk getTicketKiosk() {
        // TODO: complete the implementation of this method
        return null;
    }
    public Ticket getTicket() {
        // TODO: complete the implementation of this method
        return null;
    }

    // REQUIRES: the movie (m) must not be at full capacity, i.e. more people can watch the movie
    //           this moviegoer must be of appropriate age to watch the movie (age > m.ageRestriction)
    // MODIFIES: this
    // EFFECTS: a new ticket associated with the given movie is created, and
    //           becomes this MovieGoer's ticket
    public void buyTicket(Movie m) {
        // TODO: complete the implementation of this method
    }


}
