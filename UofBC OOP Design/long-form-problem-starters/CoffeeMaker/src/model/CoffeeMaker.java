package model;
import exceptions.*;

import java.beans.Beans;

/**
 * A coffee maker used to train baristas.
 *
 * Class invariant: cups remaining >= 0, time since last brew >= 0
 */

public class CoffeeMaker {

    private int timeSinceLastBrew;
    private int cupsRemaining;
    public CoffeeMaker(){
        cupsRemaining = 0;
    }

    // getters
    public int getTimeSinceLastBrew() {
        return timeSinceLastBrew;
    }

    // REQUIRES: Cups remaining greater than 0
    public int getCupsRemaining() throws NoCupsRemainingException {
        return this.cupsRemaining;
    }

    // EFFECTS: return true if there are coffee cups remaining
    public boolean areCupsRemaining() {
        return this.cupsRemaining != 0;
    }

    //REQUIRES: a non-negative integer, time must be less than 1 day
    //EFFECTS: sets time since last brew
    public void setTimeSinceLastBrew(int time) throws StaleCoffeeException {
        this.timeSinceLastBrew = time;
    }

    //REQUIRES: beans between 2.40 and 2.60 cups, water > 14.75 cups
    //EFFECTS: sets cups remaining to full (20 cups) and time since last brew to 0
    public void brew(double beans, double water) throws WaterException, BeansAmountException {
        if(beans < 2.4 || beans > 2.6) {
            throw new BeansAmountException(beans);
        }
        if(water > 14.75) {
            throw new WaterException(water);
        }
        this.cupsRemaining = 20;
        this.timeSinceLastBrew = 0;
    }

    ///REQUIRES: cups remaining > 0, time since last brew < 60
    //MODIFIES: this
    //EFFECTS: subtracts one cup from cups remaining
    public void pourCoffee() throws NoCupsRemainingException, StaleCoffeeException {
        if(this.cupsRemaining == 0) {
            throw new NoCupsRemainingException();
        }
        if(this.timeSinceLastBrew > 60) {
            throw new StaleCoffeeException(this.timeSinceLastBrew);
        }

        this.cupsRemaining--;
    }

}
