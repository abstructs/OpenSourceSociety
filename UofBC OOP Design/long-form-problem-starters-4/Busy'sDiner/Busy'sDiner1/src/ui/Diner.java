package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class Diner {

    public static void main(String[] args) {
        Server server = new Server(generateTurkeyClubSandwich());
        Chef chef = new Chef();
        Host host = new Host(generateTurkeyClubSandwich());
        for (int i=0; i < 2 ; i++) {
            System.out.println("Table " + (i + 1) + ":\n");

            host.greet();
            host.describeDish();
            Order o = server.takeOrder();

            chef.makeDish(o);

            System.out.println();

            doOrderRoutine(host, o, server);
            System.out.println();
        }

        System.out.println();
        chef.doDishes();
    }

    private static void doOrderRoutine(Host h, Order o, Server s) {
        h.deliverFood(o);
        if(o.isReadyToBePaid())
            s.takePayment(o);
    }


    private static Dish generateTurkeyClubSandwich() {
        List<String> ingredients = new ArrayList<>();
        ingredients.add("avocado");
        ingredients.add("sriracha");
        ingredients.add("cheddar cheese");
        ingredients.add("bread");
        ingredients.add("lettuce");
        ingredients.add("tomato");
        ingredients.add("turkey");
        ingredients.add("bacon");
        return new Dish("Turkey club sandwich",
                "\"Our trendy sandwich has avocado, sriracha sauce, cheese, veggies, turkey and bacon.\"",
                ingredients,
                "\t1. Pour sriracha\n\t2. Spread avocado\n\t3. Stack meat\n\t4. Place veggies");
    }

}
