package functions;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class TabulatedFunctions {
    private static TabulatedFunctionFactory functionFactory =
            new ArrayTabulatedFunction.ArrayTabulatedFunctionFactory();

    private TabulatedFunctions() {

    }

    public static TabulatedFunction tabulate(Function fun, double leftX, double rightX, int pointsCount){
        if(leftX < fun.getLeftDomainBorder())
            throw new IllegalArgumentException("Out of domain boundaries!");
        if(rightX > fun.getRightDomainBorder())
            throw new IllegalArgumentException("Out of domain boundaries!");

        double[] values = getFunctionValues(fun, leftX, rightX, pointsCount);

        return createTabulatedFunction(leftX, rightX, values);
    }
    public static TabulatedFunction tabulate(Function fun, double leftX, double rightX, int pointsCount, Class<? extends TabulatedFunction> aClass){
        if(leftX < fun.getLeftDomainBorder())
            throw new IllegalArgumentException("Out of domain boundaries!");
        if(rightX > fun.getRightDomainBorder())
            throw new IllegalArgumentException("Out of domain boundaries!");

        double[] values = getFunctionValues(fun, leftX, rightX, pointsCount);

        return createTabulatedFunction(leftX, rightX, values, aClass);
    }

    private static double[] getFunctionValues(Function fun, double leftX, double rightX, int pointsCount) {
        double values[] = new double[pointsCount];
        double deltaX = (rightX - leftX)/ (pointsCount - 1);

        for(int i = 0; i < pointsCount - 1; i++)
            values[i] = fun.getFunctionValue(leftX + i*deltaX);

        values[pointsCount - 1] = fun.getFunctionValue(rightX);
        return values;
    }


    public static void outputTabulatedFunction(TabulatedFunction fun, OutputStream out){
        try{
            DataOutputStream stream = new DataOutputStream(out);

            int pointsCount = fun.getPointsCount();
            stream.writeInt(pointsCount);

            for(int i = 0; i < pointsCount; i++){
                stream.writeDouble(fun.getPointX(i));
                stream.writeDouble(fun.getPointY(i));
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in){
        try{
            DataInputStream stream = new DataInputStream(in);

            int pointsCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount];

            for(int i = 0; i < pointsCount; i++){
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }

            return createTabulatedFunction(points);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in, Class<? extends TabulatedFunction> aClass){
        try{
            DataInputStream stream = new DataInputStream(in);

            int pointsCount = stream.readInt();
            FunctionPoint[] points = new FunctionPoint[pointsCount];

            for(int i = 0; i < pointsCount; i++){
                points[i] = new FunctionPoint(stream.readDouble(), stream.readDouble());
            }

            return createTabulatedFunction(points, aClass);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction fun, Writer out){
        try{
            BufferedWriter writer = new BufferedWriter(out);

            int pointsCount = fun.getPointsCount();
            String str = String.valueOf(pointsCount);
            writer.write(str + " ");

            for(int i = 0; i < pointsCount; i++){
                writer.write(String.valueOf(fun.getPointX(i)) + " ");
                writer.write(String.valueOf(fun.getPointY(i)) + " ");
            }
            writer.flush();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static TabulatedFunction readTabulatedFunction(Reader in){
        try{
            StreamTokenizer streamTokenizer = new StreamTokenizer(in);

            streamTokenizer.nextToken();
            int pointsCount = (int)streamTokenizer.nval;

            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for(int i = 0; i < pointsCount; i++) {
                streamTokenizer.nextToken();
                double x = streamTokenizer.nval;
                streamTokenizer.nextToken();
                double y = streamTokenizer.nval;
                points[i] = new FunctionPoint(x, y);
            }

            return createTabulatedFunction(points);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static TabulatedFunction readTabulatedFunction(Reader in, Class<? extends TabulatedFunction> aClass){
        try{
            StreamTokenizer streamTokenizer = new StreamTokenizer(in);

            streamTokenizer.nextToken();
            int pointsCount = (int)streamTokenizer.nval;

            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for(int i = 0; i < pointsCount; i++) {
                streamTokenizer.nextToken();
                double x = streamTokenizer.nval;
                streamTokenizer.nextToken();
                double y = streamTokenizer.nval;
                points[i] = new FunctionPoint(x, y);
            }

            return createTabulatedFunction(points, aClass);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void setTabulatedFunctionFactory(TabulatedFunctionFactory aFunctionFactory) {
        functionFactory = aFunctionFactory;
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values) {
        return functionFactory.createTabulatedFunction(leftX, rightX, values);
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points) {
        return functionFactory.createTabulatedFunction(points);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount) {
        return functionFactory.createTabulatedFunction(leftX, rightX, pointsCount);
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, double[] values, Class<? extends TabulatedFunction> aClass) {
        try {
            Constructor<? extends TabulatedFunction> constructor = aClass.getConstructor(double.class, double.class, double[].class);
            return constructor.newInstance(leftX, rightX, values);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(FunctionPoint[] points, Class<? extends TabulatedFunction> aClass) {
        try {
            Constructor<? extends TabulatedFunction> constructor = aClass.getConstructor(FunctionPoint[].class);
            return constructor.newInstance((Object) points);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static TabulatedFunction createTabulatedFunction(double leftX, double rightX, int pointsCount, Class<? extends TabulatedFunction> aClass){
        try {
            Constructor<? extends TabulatedFunction> constructor = aClass.getConstructor(double.class, double.class, int.class);
            return constructor.newInstance(leftX, rightX, pointsCount);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
