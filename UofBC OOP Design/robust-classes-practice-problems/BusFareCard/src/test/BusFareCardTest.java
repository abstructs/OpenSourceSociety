package test;

import exceptions.IllegalAgeException;
import exceptions.NoBalanceException;

import model.BusFareCard;
import org.junit.Before;
import org.junit.Test;

import static model.BusFareCard.ADULT_FARE;
import static model.BusFareCard.CONCESSION_FARE;
import static org.junit.Assert.*;

public class BusFareCardTest {

    private BusFareCard adultCard, concCard, zeroBalanceCard;

    @Before
    public void setUp() {
        try {
            adultCard = new BusFareCard("Anthony Estey", 30, 100.00);
            concCard = new BusFareCard("Amy Zhu", 18, 56.00);
            zeroBalanceCard = new BusFareCard("Test", 19, 0);
        } catch (Exception e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    public void testGetters() {
        assertEquals(adultCard.getBalance(),100.00, 0.05);
        assertEquals(concCard.getBalance(),56.00, 0.05);
    }

    @Test
    public void testpurchaseAdultFareNoException() {
        assertEquals(adultCard.getBalance(),100.00, 0.05);
        assertFalse(adultCard.isFareLoaded());
        try {
            adultCard.purchaseAdultFare();
        } catch (IllegalAgeException | NoBalanceException e) {
            fail("Exception should not have been thrown.");
        }
        assertEquals(adultCard.getBalance(),100.00 - ADULT_FARE, 0.05);
        assertTrue(adultCard.isFareLoaded());
    }

    @Test
    public void testpurchaseAdultFareNoBalanceException() {
        try {
            zeroBalanceCard.purchaseAdultFare();
        } catch (NoBalanceException e1) {
            System.out.println(e1.getMessage());
        } catch (IllegalAgeException e2) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    public void testpurchaseConcessionFareIllegalAge() {
        assertEquals(adultCard.getBalance(),100.00, 0.05);
        try {
            adultCard.purchaseConcessionTicket();
        } catch (IllegalAgeException e1) {
            System.out.println(e1.getMessage());
        } catch (NoBalanceException e2) {
            System.out.println("Exception should not have been thrown.");
        }
        assertEquals(adultCard.getBalance(),100.00,0.05);
    }

    @Test
    public void testpurchaseConcessionFareNoException() {
        assertEquals(concCard.getBalance(),56.00,0.05);
        assertFalse(concCard.isFareLoaded());
        try {
            concCard.purchaseConcessionTicket();
        } catch (IllegalAgeException | NoBalanceException e) {
            fail("Exception should not have been thrown.");
        }
        assertEquals(concCard.getBalance(),56.00 - CONCESSION_FARE,0.05);
        assertTrue(concCard.isFareLoaded());
    }


}