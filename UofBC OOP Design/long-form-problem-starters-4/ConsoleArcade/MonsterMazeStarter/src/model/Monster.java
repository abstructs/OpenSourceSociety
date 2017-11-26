package model;

public class Monster extends Choice {

    private Treasure treasure;
//    private String optionMessage;

    public Monster() {
//        this.optionMessage = "Fight a monster.";
        super("Fight a monster.");
        treasure = null;
    }

//    //EFFECTS: prints a message representing this possible next choice
//    public void printOptionMessage() {
//        System.out.println(optionMessage);
//    }

    //MODIFIES: this
    //EFFECTS: sets the treasure to t
    public void setTreasure(Treasure t) {
        this.treasure = t;
    }

    //EFFECTS: prints the result of choosing this choice
//    public void printOutcome() {
//        if (treasure == null) {
//            System.out.println("Ha! I killed you!");
//        } else {
//            System.out.println("Ahh! You killed me!");
//            treasure.printOutcome();
//        }
//    }
    // TODO: THIS SHIT
    protected String getOutcome() {
        if (treasure == null) {
            return "Ha! I killed you!";
        } else {
            return "Ahh! You killed me!";
            treasure.printOutcome();
        }
    }

}
