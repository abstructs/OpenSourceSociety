package model;
import java.util.Random;
public class Coin {

    private int coin_state = 0;

    // MODIFIES: coin_state
    // EFFECTS: randomly chooses between heads and tails
    public void flip() {
        Random ran = new Random();
        boolean b = ran.nextBoolean();
        if (b) { coin_state = 1; }
        else { coin_state = 0; }
    }

    // EFFECTS: returns the status of the coin
    public String coin_status() { if (coin_state == 1) { return "heads"; } return "tails"; }
}
