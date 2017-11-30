package model;

public class Monster extends Choice {

    private Treasure treasure;

    public Monster() {
        super("Fight a monster.");
        treasure = null;
    }

    //MODIFIES: this
    //EFFECTS: sets the treasure to t
    public void setTreasure(Treasure t) {
        this.treasure = t;
    }

    //EFFECTS: returns the outcome
    protected String getOutcome() {
        if (treasure == null) {
            return "Ha! I killed you!";
        } else {
            return "Ahh! You killed me!";
        }
    }

}
