package functions;

import java.io.*;

public class TabulatedFunctions {
    private TabulatedFunctions() {

    }

    public static TabulatedFunction tabulate(Function fun, double leftX, double rightX, int pointsCount){
        if(leftX < fun.getLeftDomainBorder())
            throw new IllegalArgumentException("Out of domain boundaries!");
        if(rightX > fun.getRightDomainBorder())
            throw new IllegalArgumentException("Out of domain boundaries!");

        double[] values = getFunctionValues(fun, leftX, rightX, pointsCount);

        return new ArrayTabulatedFunction(leftX, rightX, values);
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

            return new ArrayTabulatedFunction(points);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void writeTabulatedFunction(TabulatedFunction fun, Writer out){
        try{
            BufferedWriter writer = new BufferedWriter(out);

            int pointsCount = fun.getPointsCount();
            writer.write(String.valueOf(pointsCount) + " ");

            for(int i = 0; i < pointsCount; i++){
                writer.write(String.valueOf(fun.getPointX(i)) + " ");
                writer.write(String.valueOf(fun.getPointY(i)) + " ");
            }
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

            return new ArrayTabulatedFunction(points);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


}
