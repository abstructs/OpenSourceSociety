package model;

public abstract class Vegetable implements Growable {

    private String name;
    private String instructions;
    private VegType type;
    public Vegetable(String name, VegType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public VegType getType() {
        return type;
    }

    // EFFECTS: prints "Feeding (VegName)"
    @Override
    public void feed() {
        System.out.println("Feeding " + this.getName());
    }

    // EFFECTS: prints "Watering (VegName)"
    @Override
    public void water() {
        System.out.println("Watering " + this.getName());
    }

    // EFFECTS: prints "Harvesting (VegName)"
    @Override
    public void harvest() {
        System.out.println("Harvesting " + this.getName());
    }
}
