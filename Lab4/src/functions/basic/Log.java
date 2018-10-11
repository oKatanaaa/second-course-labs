package functions.basic;

import functions.Function;

public class Log implements Function {
    private double base;

    public Log(double base){
        this.base = base;
    }
    @Override
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    /**
     * This function uses logarithm property:
     * logc(b) / logc(a) = loga(b)
     * In this case c equals e(Euler's constant)
     * @param x
     * @return
     */
    @Override
    public double getFunctionValue(double x) {
        return Math.log(x)/Math.log(base);
    }
}
