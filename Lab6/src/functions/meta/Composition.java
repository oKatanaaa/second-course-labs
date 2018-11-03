package functions.meta;

import functions.Function;

import java.awt.*;

public class Composition implements Function {
    private Function outer;
    private Function inner;

    public Composition(Function outer, Function inner){
        this.outer = outer;
        this.inner = inner;
    }

    @Override
    public double getLeftDomainBorder() {
        return this.outer.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return this.outer.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return this.outer.getFunctionValue(this.inner.getFunctionValue(x));
    }
}
