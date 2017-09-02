package ui;

import model.Coin;
public class Main {
    public static void main(String[] args) {
        Coin coin = new Coin();

        coin.flip();
        System.out.println(coin.coin_status());

        coin.flip();
        System.out.println(coin.coin_status());

        coin.flip();
        System.out.println(coin.coin_status());

        System.out.println("Lol it works");
        try {
            System.in.read();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
