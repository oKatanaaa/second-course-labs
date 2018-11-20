package functions;

public interface TabulatedFunctionFactory {
    TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values);
    TabulatedFunction createTabulatedFunction(FunctionPoint[] points);
    TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount);

}
