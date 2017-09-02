package model;

import java.util.List;

public class Band {

    private String name;
    private double totalMoney;
    private double currentMoney;
    private List<String> members;
    private int numShowsPlayed;

    public Band(String name) {
        // TODO: complete the implementation of this method
    }

    // getters
    public String getName() {
        // TODO: complete the implementation of this method
        return null;
    }
    public double getTotalMoney() {
        // TODO: complete the implementation of this method
        return 0.0;
    }
    public double getCurrentMoney() {
        // TODO: complete the implementation of this method
        return 0.0;
    }
    public List<String> getMembers() {
        // TODO: complete the implementation of this method
        return null;
    }
    public int getNumShowsPlayed() {
        // TODO: complete the implementation of this method
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: adds a member with the given name to the band
    public void addMember(String name) {
        // TODO: complete the implementation of this method
    }

    // MODIFIES: this
    // EFFECTS: given an amount the band will be played for the show, update
    //          the current money and total money by the amount, and add one to
    //          the number of shows played.
    public void playGig(double amt) {
        // TODO: complete the implementation of this method

    }

    // MODIFIES: this
    // EFFECTS: pays the given amount to each band member, the total paid
    //          is subtracted from the current money. If there is not enough
    //          money, nothing happens
    public void payMembers(double amt) {
        // TODO: complete the implementation of this method
    }

    // REQUIRES: numShowsPlayed > 0
    // MODIFIES: nothing
    // EFFECTS: computes the average amount the band is payed per show
    public double averagePerShow() {
        // TODO: complete the implementation of this method
        return 0.0;
    }


}
