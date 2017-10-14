package test;

import model.Kitchen;
import model.Owner;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OwnerTest {

    private Kitchen testKitchen;
    private Owner testOwner;

    @Before
    public void setUp() {
        testKitchen = new Kitchen(30, 4, 100, true);
        testOwner = new Owner("Paul Lockwood", testKitchen);
    }

    @Test
    public void testGetters() {
        assertEquals(testOwner.getName(), "Paul Lockwood");
        assertEquals(testOwner.getKitchen(), testKitchen);
    }

    @Test
    public void testOrderMoreTacos() {
        assertEquals(testOwner.getKitchen().getIngredientCount(),30);
        assertEquals(testOwner.getKitchen().getTacoCount(),4);

        assertTrue(testOwner.orderMoreTacos(10));

        assertEquals(testOwner.getKitchen().getTacoCount(),14);
        assertEquals(testOwner.getKitchen().getIngredientCount(),0);

        assertFalse(testOwner.orderMoreTacos(1));
    }

    @Test
    public void testaskForMoreIngredients() {
        assertEquals(testOwner.getKitchen().getIngredientCount(), 30);
        assertEquals(testOwner.getKitchen().getBalance(),100);

        assertTrue(testOwner.askForMoreIngredients(30));

        assertEquals(testOwner.getKitchen().getBalance(),40);
        assertEquals(testOwner.getKitchen().getIngredientCount(),60);

        assertFalse(testOwner.askForMoreIngredients(100000));
    }


}