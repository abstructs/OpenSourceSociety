package ui;
import model.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Vegetable> garden = new ArrayList();
        garden.add(new Onion());
        garden.add(new Kale());
        garden.add(new Beet());

        for(Vegetable veg : garden) {
            System.out.println(veg.getName());
            System.out.println(veg.getInstructions());
            veg.feed();
            veg.water();
            veg.harvest();
        }
    }
}
