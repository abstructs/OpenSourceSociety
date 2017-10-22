package exceptions;

public class StaleCoffeeException extends Exception {
    private int timeSinceLastBrew;

    public StaleCoffeeException(int timeSinceLastBrew) {
        super("Coffee is stale.");
        this.timeSinceLastBrew = timeSinceLastBrew;
    }
}
