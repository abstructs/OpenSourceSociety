package model;

import java.util.List;

public class Host extends FOHEmployee {

    private static final String ERROR = "ERROR!! ";
    private static final String PREFIX = "HOST - ";

    public Host(Dish dish) {
        super(dish);
    }

    //getters
    public String getPrefix() { return PREFIX; }

}
