package functions.meta;

import functions.Function;

public class Scale implements Function {
    private Function fun;
    private double scaleX;
    private double scaleY;

    public Scale(Function fun, double scaleX, double scaleY) {
        this.fun = fun;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.fun.getLeftDomainBorder() * this.scaleX;
    }

    @Override
    public double getRightDomainBorder() {
        return this.fun.getRightDomainBorder() * this.scaleX;
    }

    @Override
    public double getFunctionValue(double x) {
        return this.fun.getFunctionValue(x / this.scaleX) * this.scaleY;
    }
}
