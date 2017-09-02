package ui;

import model.Contact;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        System.out.println("To add a new contact, type in their name, then press 'enter':");
        // TODO: Create a Contact object "c' here with the name read from the scanner, you can read input from the scanner
        // TODO: by calling next() on the scanner
        System.out.println("Add a phone number for " + //TODO: get the name field of the "c" object here + ":");
                // TODO: set the number of the Contact object "c" to the user input read from the scanner
                System.out.println("Would you like to call " + c.getName() + " now? Type 'y' for yes, 'n' for no.");
        options(c, s);
    }

    private static void options(Contact c, Scanner s){
        String option = s.next();
        if(option.equals("y")) {
            System.out.println("Enter a date for the call in the format MM/DD/YY:");
            c.call(s.next());
            System.out.println("Here is your updated call history:");
        } else if (option.equals("n")) {
            System.out.println("Call history: No Calls.");
        }
    }


}
