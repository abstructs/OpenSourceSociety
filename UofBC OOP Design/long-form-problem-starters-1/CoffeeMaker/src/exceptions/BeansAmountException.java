package exceptions;

public class BeansAmountException extends Exception {
    private double beans;

    public BeansAmountException(double beans){ this.beans = beans; }

    protected BeansAmountException(double beans, String message){
        super(message);
        this.beans = beans;
    }

    public double getBeans(){ return beans; }
}
