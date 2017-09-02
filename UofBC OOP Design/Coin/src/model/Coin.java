package model;

public class Coin {

    public int coin_state = 0;

    // MODIFIES: coin_state
    // EFFECTS: randomly chooses between heads and tails
    public void flip() { }

    // EFFECTS: returns the status of the coin
    public String coin_status() { return ""; }
}
