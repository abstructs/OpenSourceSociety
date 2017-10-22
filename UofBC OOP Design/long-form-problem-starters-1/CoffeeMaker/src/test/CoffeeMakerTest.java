package test;

import exceptions.*;
import model.CoffeeMaker;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoffeeMakerTest {

    private CoffeeMaker cm;
    @Before
    public void setUp() {
        cm = new CoffeeMaker();
    }

    @Test
    public void testBrewCoffeeWithProperAmounts() {
        try {
            cm.brew(2.6, 14.75);
            cm.brew(2.4, 0);
        } catch(BeansAmountException e) {
            fail("Bad beans amount.");
        } catch(WaterException e) {
            fail("bad water amount.");
        }
    }

    @Test
    public void testBrewCoffeeWithTooMuchWater() {
        try {
            cm.brew(2.6, 14.76);
            fail("Didn't throw expected exception.");
        } catch(BeansAmountException e) {
            fail("Bad beans amount.");
        } catch(WaterException e) {

        }
    }

    @Test
    public void testBrewCoffeeWithBadBeanAmount() {
        try {
            cm.brew(2.61, 14.75);
            fail("Didn't throw expected exception.");
        } catch(BeansAmountException e) {
            System.out.println(e);
        } catch(WaterException e) {
            fail("Threw wrong exception.");
        }

        try {
            cm.brew(2.39, 14.75);
            fail("Didn't throw expected exception.");
        } catch(BeansAmountException e) {
            System.out.println(e);
        } catch(WaterException e) {
            fail("Threw wrong exception.");
        }
    }

    @Test
    public void testBrewCoffeeWithBadAmounts() {
        try {
            cm.brew(2.61, 14.76);
            fail("Didn't throw expected exception.");
        } catch(BeansAmountException e) {
            System.out.println(e);
        } catch(WaterException e) {
            System.out.println(e);
        }
    }
}
