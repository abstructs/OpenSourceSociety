package model;

import java.util.List;

public class TransactionSummary {

    private String name;
    private List<Transaction> transactions;

    public TransactionSummary(String name) {
        // TODO: complete the implementation of this method
    }

    // getters
    public String getName() {
        // TODO: complete the implementation of this method
        return null;
    }
    public List<Transaction> getTransactions() {
        // TODO: complete the implementation of this method
        return null;
    }
    public int getNumTransactions() {
        // TODO: complete the implementation of this method
        return 0;
    }

    // REQUIRES: t is not already within transactions
    // MODIFIES: this
    // EFFECTS: adds the given transaction t to the list of transactions
    public void addTransaction(Transaction t) {
        // TODO: complete the implementation of this method
    }

    // REQUIRES: transactions is non-empty
    // EFFECTS: computes the average amount of money spent on a transaction
    public double averageTransactionCost() {
        // TODO: complete the implementation of this method
        return 0.0;
    }

    // REQUIRES: transactions is non-empty
    // EFFECTS:  returns the average cost of all transactions of type specificType in this
    //           TransactionSummary
    public double specificTypeAverage(TransactionType specificType) {
        // TODO: complete the implementation of this method
        return 0.0;
    }

    // REQUIRES: transactions is non-empty
    // EFFECTS: returns the largest transaction (in terms of cost) in this TransactionSummary
    public Transaction largestTransaction() {
        // TODO: complete the implementation of this method
        return null;
    }

    // EFFECTS: returns true if the given transaction is contained within the list of transactions
    public boolean contains(Transaction t) {
        // TODO: complete the implementation of this method
        return false;
    }


}
