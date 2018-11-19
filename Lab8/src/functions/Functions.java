package functions;

import functions.meta.*;

public class Functions {
    private Functions(){

    }
    public static Function shift(Function fun, double shiftX, double shiftY){
        return new Shift(fun, shiftX, shiftY);
    }

    public static Function scale(Function fun, double scaleX, double scaleY){
        return new Scale(fun, scaleX, scaleY);
    }

    public static Function power(Function fun, double power){
        return new Power(fun, power);
    }

    public static Function sum(Function fun1, Function fun2){
        return new Sum(fun1, fun2);
    }

    public static Function mult(Function fun1, Function fun2){
        return new Mult(fun1, fun2);
    }

    public static Function composition(Function outer, Function inner){
        return new Composition(outer, inner);
    }

    public static double integrate(Function fun, double leftX, double rightX, double discreteStep) {
        if(leftX < fun.getLeftDomainBorder() || fun.getRightDomainBorder() < rightX)
            throw new IllegalArgumentException("Boundaries of the integration area are out of function domain boundaries.");

        double integralSum = 0;

        while(leftX + discreteStep < rightX) {
            integralSum += fun.getFunctionValue(leftX) * discreteStep +
                    (fun.getFunctionValue(leftX + discreteStep) -  fun.getFunctionValue(leftX))* discreteStep / 2;
            leftX += discreteStep;
        }

        return  integralSum;
    }
}
