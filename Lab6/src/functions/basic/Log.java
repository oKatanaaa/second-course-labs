package functions.basic;

import functions.Function;

public class Log implements Function {
    private double base;

    public Log(double base){
        if(base < 0 || Double.compare(base, 1.0) == 0 || Double.compare(base, 0.0) == 0)
            throw new IllegalArgumentException();

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
        if(x < 0 || Double.compare(x, 0.0) == 0)
            throw new IllegalArgumentException();

        return Math.log(x)/Math.log(base);
    }
}
