package model;

public class Host {

    private Dish dish;

    private static final String PREFIX = "HOST - ";
    public Host(Dish d) {
        this.dish = d;
    }

    //EFFECTS: prints out a description of the dish on the menu
    public void describeDish() {
        System.out.println(dish.getDescription());
    }

    //EFFECTS: prints out a greeting
    public void greet() {
        System.out.println("\"Hello and welcome to Busy's, the home of the trendy turkey club sandwich.\"");
    }

    //MODIFIES: this, order
    //EFFECTS: logs order as served and brings to table
    public void deliverFood(Order order) {
        order.setIsServed();
        System.out.print(PREFIX + "Delivered food: ");
        order.print();
    }
}
