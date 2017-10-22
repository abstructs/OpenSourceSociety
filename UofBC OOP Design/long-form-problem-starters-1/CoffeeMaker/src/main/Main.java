package main;
import exceptions.*;
import model.*;
public class Main {

    public static void main(String[] args){
        CoffeeMaker goodPath = new CoffeeMaker();
        CoffeeMaker badPath = new CoffeeMaker();

        try {
            goodPath.brew(5, 5);
            goodPath.pourCoffee();
            goodPath.pourCoffee();
            goodPath.pourCoffee();

            badPath.brew(2.61, 14.76);
            badPath.brew(2.60, 14.75);
            badPath.pourCoffee();
            badPath.pourCoffee();
            badPath.pourCoffee();
            badPath.setTimeSinceLastBrew(61);
            badPath.pourCoffee();
        } catch(Exception e) {
            System.out.println(e);
        }




    }


}