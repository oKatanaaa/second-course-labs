package functions.meta;

import functions.Function;

public class Shift implements Function {
    private Function fun;
    private double shiftX;
    private double shiftY;

    public Shift(Function fun, double shiftX, double shiftY){
        this.fun = fun;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.fun.getLeftDomainBorder() + this.shiftX;
    }

    @Override
    public double getRightDomainBorder() {
        return this.fun.getRightDomainBorder() + this.shiftX;
    }

    @Override
    public double getFunctionValue(double x) {
        return this.fun.getFunctionValue(x - this.shiftX) + shiftY;
    }
}
