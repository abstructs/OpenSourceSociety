package test;

import model.Kitchen;
import model.Owner;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KitchenTest {

    private Kitchen testKitchen;
    private Owner testOwner;

    @Before
    public void setUp() {
        testKitchen = new Kitchen(30, 4, 1000, false);
        testOwner = new Owner("Alan Turing", testKitchen);
    }

    @Test
    public void testGetters() {
        assertEquals(testKitchen.getIngredientCount(),30);
        assertEquals(testKitchen.getTacoCount(),4);
        assertEquals(testKitchen.getBalance(),1000);
        assertFalse(testKitchen.getCookState());

        testKitchen.setCookStatus(true);

        assertTrue(testKitchen.getCookState());
    }

    @Test
    public void testmakeTacoNoException() {
        testKitchen.setCookStatus(true);
        assertTrue(testKitchen.getCookState());

        try {
            testKitchen.makeTaco(9);
        } catch (Exception e) {
            fail("This exception should not have been thrown.");
        }

        assertEquals(testKitchen.getIngredientCount(), 3);
        assertEquals(testKitchen.getTacoCount(),13);
    }

    @Test
    public void testmakeTacoWithException() {
        testKitchen.setCookStatus(true);
        assertTrue(testKitchen.getCookState());

        try {
            testKitchen.makeTaco(11);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(testKitchen.getIngredientCount(),30);
        assertEquals(testKitchen.getTacoCount(),4);
    }

    @Test
    public void testmakeTacoNoCookException() {
        try {
            testKitchen.makeTaco(1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void testbuyIngredientsNoException() {
        try {
            testKitchen.buyIngredients(100);
        } catch (Exception e) {
            fail("This exception should not have been thrown.");
        }

        assertEquals(testKitchen.getBalance(),800);
        assertEquals(testKitchen.getIngredientCount(),130);
    }

    @Test
    public void testbuyIngredientsWithException() {
        try {
            testKitchen.buyIngredients(1000);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        assertEquals(testKitchen.getBalance(),1000);
        assertEquals(testKitchen.getIngredientCount(),30);
    }


}