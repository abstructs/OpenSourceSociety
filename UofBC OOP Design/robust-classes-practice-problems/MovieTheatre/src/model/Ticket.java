package model;

public class Ticket {

    private Movie movie;

    public Ticket(Movie m) {
        movie = m;
    }

    // getters
    public Movie getMovie() {
        // TODO: complete the implementation of this method
        return null;
    }

    // Do not touch the implementation of this method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Ticket)) return false;

        Ticket compareTo = (Ticket) o;

       return (this.getMovie().getTitle().equals(compareTo.getMovie().getTitle()));
    }


}
