package exceptions;

import exceptions.BeansAmountException;

public class TooManyBeansException extends BeansAmountException {
    public TooManyBeansException(double beans) {
        super(beans, String.format("%f is too many beans", beans));
    }
}
