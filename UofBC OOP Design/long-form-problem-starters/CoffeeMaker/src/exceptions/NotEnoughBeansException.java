package exceptions;

import exceptions.BeansAmountException;

public class NotEnoughBeansException extends BeansAmountException {
    public NotEnoughBeansException(double beans) {
        super(beans, String.format("%f is not enough beans", beans));
    }
}
