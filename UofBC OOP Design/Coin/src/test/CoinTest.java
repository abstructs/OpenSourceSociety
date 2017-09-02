package test;

import model.Coin;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

class CoinTest {
    private Coin coin;

    @Before
    public void setup() {
        coin = new Coin();
    }

    @Test
    public void flipTest() {
        Integer tailsFlipped = 0;
        Integer headsFlipped = 0;
        for(int i = 0; i < 100; i++) {
            coin.flip();
            String face = coin.coin_status();

            if (face.equals("tails")) { tailsFlipped++; }
            else if (face.equals("heads")) { headsFlipped++; }
        }
        assertTrue(tailsFlipped > 0 && headsFlipped > 0);
    }
}
