package model;

public class Treasure extends Choice {

    private int prize;
//    private String optionMessage;

    public Treasure(int prize) {
//        this.optionMessage = "Claim your treasure!";
        super("Claim your treasure!");
        this.prize = prize;
    }

//    //EFFECTS: prints a message representing this possible next choice
//    public void printOptionMessage() {
//        System.out.println(optionMessage);
//    }

    protected String getOutcome() {
        return "Your prize is " + prize + " spendibees.";
    }

    //EFFECTS: prints the result of choosing this choice
//    public void printOutcome() {
//        System.out.println();
//    }

}
