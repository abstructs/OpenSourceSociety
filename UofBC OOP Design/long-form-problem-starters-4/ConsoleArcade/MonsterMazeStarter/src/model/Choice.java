package model;

public abstract class Choice {

    private String optionMessage;
    private String outcome;
    public Choice(String optionMessage) {
        this.optionMessage = optionMessage;
        this.outcome = "";
    }

    protected String getOutcome() {
        return this.outcome;
    }

    //EFFECTS: prints a message representing this possible next choice
    public void printOptionMessage() {
        System.out.println(optionMessage);
    }

    public void printOutcome() {
        System.out.println(this.getOutcome());
    }
}
