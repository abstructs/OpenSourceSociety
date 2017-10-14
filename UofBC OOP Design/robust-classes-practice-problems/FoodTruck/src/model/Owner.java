package model;

public class Owner {

    private String name;
    private Kitchen kitchen;

    public Owner(String name, Kitchen kit) {
        this.name = name;
        kitchen = kit;
    }

    //getters
    public String getName() { return name; }
    public Kitchen getKitchen() { return kitchen; }

    // REQUIRES: we have enough ingredients to make the specified amount of tacos
    // MODIFIES: this object's kitchen field
    // EFFECTS: invokes the makeTaco() method on this object's kitchen, returns true
    public boolean orderMoreTacos(int amount) {
        kitchen.makeTaco(amount);
        return true;
    }

    //MODIFIES: this object's kitchen field
    //EFFECTS: invokes the buyIngredients() method on this object's kitchen, returns true.
    public boolean askForMoreIngredients(int amount) {
        this.kitchen.buyIngredients(amount);
        return true;
    }


}
