package functions.meta;

import functions.Function;

public class Power implements Function {
    private Function fun;
    private double power;

    public Power(Function fun, double power) {
        this.fun = fun;
        this.power = power;
    }

    @Override
    public double getLeftDomainBorder() {
        return fun.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return fun.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(fun.getFunctionValue(x), power);
    }
}
