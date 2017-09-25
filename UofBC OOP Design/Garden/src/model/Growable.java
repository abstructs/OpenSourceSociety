package model;

public interface Growable {
    // EFFECTS: feeds the growable
    void feed();
    // EFFECTS: waters the growable
    void water();
    // EFFECTS: gets the instructions for the growable
    String getInstructions();

    void harvest();
}
