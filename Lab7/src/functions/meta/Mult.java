package functions.meta;

import functions.Function;

public class Mult implements Function {
    private Function fun1;
    private Function fun2;

    public Mult(Function fun1, Function fun2){
        this.fun1 = fun1;
        this.fun2 = fun2;
    }

    @Override
    public double getLeftDomainBorder() {
        return Math.max(fun1.getLeftDomainBorder(), fun2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return Math.min(this.fun1.getRightDomainBorder(),this.fun2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return this.fun1.getFunctionValue(x) * this.fun2.getFunctionValue(x);
    }
}
