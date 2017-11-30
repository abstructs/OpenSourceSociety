package model;

import model.observer_pattern.Observer;
import model.observer_pattern.Subject;
import model.random.BingoCall;

import java.util.ArrayList;
import java.util.List;

public class Game implements Subject {

    public static int CARD_SIZE = 25;
    public static int SIDE_LENGTH = (int) Math.sqrt(CARD_SIZE);

    private BingoCall currentCall;
    private List<Observer> observers;
    private boolean gameOver;

    public Game() {
        observers = new ArrayList<>();
        callNext();
    }

    //getters
    public BingoCall getCurrentCall(){
        return currentCall;
    }

    public boolean isGameOver(){
        checkGameOver();
        return gameOver;
    }

    public List<PlayerCard> getCards() {
        List<PlayerCard> playerCards = new ArrayList<>();
        for (Observer o : observers) { //NOTE: refactor this line ONLY.
            if (o.getClass().getSimpleName().equals("PlayerCard"))
                playerCards.add((PlayerCard) o);
        }
        return playerCards;
    }

    //EFFECTS: generates the next bingo call and notifies observers
    public void callNext() {
        currentCall = new BingoCall();
        for(Observer o : observers) {
            o.update(currentCall);
        }
    }

    //MODIFIES: this
    //EFFECTS: adds observer to list of observers
    public void addCard(PlayerCard pc) {
        observers.add(pc);
    }

    //MODIFIES: this
    //EFFECTS: adds observer to list of observers
    public void addObserver(Observer o) {

    }

    //EFFECTS: notifies observers of state change
    public void notifyObservers() {

    }

    //EFFECTS: sets game over to true if one of the players has bingo
    private void checkGameOver(){
        for (Observer pc : observers) {
            PlayerCard p = (PlayerCard) pc;
            if (p.hasBingo()) {
                gameOver = true;
                break;
            }
        }
    }
}
