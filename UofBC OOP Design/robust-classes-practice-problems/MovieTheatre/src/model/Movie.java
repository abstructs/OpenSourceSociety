package model;

public class Movie {

    private String title;
    private int ageRestriction;
    private int currentSeating;
    private int maxSeating;

    public Movie(String title, int ageRestriction, int seating) {
        this.title = title;
        this.ageRestriction = ageRestriction;
        this.currentSeating = 0;
        this.maxSeating = seating;
    }

    //getters
    public String getTitle() {
        // TODO: complete the implementation of this method
        return null;
    }
    public int getAgeRestriction() {
        // TODO: complete the implementation of this method
        return 0;
    }
    public int getCurrentSeating() {
        // TODO: complete the implementation of this method
        return 0;
    }
    public int getMaxSeating() {
        // TODO: complete the implementation of this method
        return 0;
    }

    //EFFECTS: returns true if the movie is at full capacity, else return false
    public boolean isFull() {
        // TODO: complete the implementation of this method
        return false;
    }

    //EFFECTS: increment the currentSeating field by 1
    public void addViewer() {
        // TODO: complete the implementation of this method
    }


}
