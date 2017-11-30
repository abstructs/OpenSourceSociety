package model;

public class Treasure extends Choice {

    private int prize;

    public Treasure(int prize) {
        super("Claim your treasure!");
        this.prize = prize;
    }

    protected String getOutcome() {
        return "Your prize is " + prize + " spendibees.";
    }
}
