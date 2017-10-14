package main;

import model.BusFareCard;
import java.util.Scanner;

public class Main {

    private static Scanner sc;
    private static BusFareCard card;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        System.out.print("Welcome to the bus fare ticketing kiosk. Would you like to buy a fare card? ");
        String userInput = sc.nextLine();

        if (userInput.equalsIgnoreCase("Yes")) {
            createTicket();
        } else {
            System.out.print("Thank you, see you next time.");
        }
    }


    private static void createTicket() {
        System.out.print("Please enter your name: ");
        String userName = sc.nextLine();
        System.out.print("Please enter your age: ");
        int userAge = Integer.parseInt(sc.nextLine());
        System.out.print("Please enter the amount you want to load on your card: ");
        Double userBalance = Double.parseDouble(sc.nextLine());

        try {
            card = new BusFareCard(userName, userAge, userBalance);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }


        System.out.println("A card with balance: " + userBalance + " has been created in the name: " + userName + "\n");

        System.out.print("Would you like to load a fare onto your card? ");
        String userResponse = sc.nextLine();
        if (userResponse.equalsIgnoreCase("Yes")) {
            purchaseFare();
        } else {
            System.out.println("Have a good day.");
        }
    }

    private static void purchaseFare() {
        System.out.print("Please specify the type of fare you wish to purchase (Adult/Concession): ");
        String userFareChoice = sc.nextLine();
        if (userFareChoice.equalsIgnoreCase("Adult")) {
            purchaseAdultFare(card);
        } else if (userFareChoice.equalsIgnoreCase("Concession")) {
            purcahseConcessionFare(card);
        } else {
            System.out.println("That is not a valid fare type.");
        }
    }

    private static void purchaseAdultFare(BusFareCard c) {
        try {
            //TODO: what kind of exception are we expecting to catch here? Replace this todo with the method call
            gotoinfoPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            gotoinfoPage();
        }
    }

    private static void purcahseConcessionFare(BusFareCard c) {
        try {
            //TODO: what kind of exception are we expecting to catch here? Replace this todo with the method call
            gotoinfoPage();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            gotoinfoPage();

        }
    }

    private static void gotoinfoPage() {
        System.out.print("The balance remaining on your bus fare card is now: " + card.getBalance());
    }

}
