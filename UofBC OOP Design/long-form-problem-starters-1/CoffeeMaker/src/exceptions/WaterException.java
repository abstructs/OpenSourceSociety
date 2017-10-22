package exceptions;

public class WaterException extends Exception {
    private double waterAmount;

    public WaterException(double waterAmount) {
        super("Water is overflowing.");
        this.waterAmount = waterAmount;
    }
}
