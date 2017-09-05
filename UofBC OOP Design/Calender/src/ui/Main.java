package ui;
import model.*;
public class Main {
    public static void main(String[] args) {
        Date date = new Date(12, 2, 1997);
        System.out.println(date.getLongFormat());
        System.out.println(date.getShortFormat());
    }
}
