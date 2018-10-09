import functions.*;

public class Main{
    public static void main(String[] args) throws InappropriateFunctionPointException {
        double[] values = { -27, -8, -1, 0, 1, 8, 27 };
        ArrayTabulatedFunction test = new ArrayTabulatedFunction(-3, 3, values);
        // GETTERS
        // ArrayTabulatedFunction
        System.out.println("LDB: " + test.getLeftDomainBorder());
        System.out.println("RDB: " + test.getRightDomainBorder());
        System.out.println("PointsCount: " + test.getPointsCount());
        System.out.println("GetPointY(2): " + test.getPointY(2));
        System.out.println("Get funct value(1): " + test.getFunctionValue(1));
        System.out.println("Get funct value(-2): " + test.getFunctionValue(-2));
        System.out.println("Get funct value(1.5): " + test.getFunctionValue(1.5));
        // FunctionPoint
        FunctionPoint testPoint = new FunctionPoint(test.getPoint(2));
        System.out.println("X: " + testPoint.getX());
        System.out.println("Y: " + testPoint.getY());
        // SETTERS
        System.out.println(test);
        // Correct setting
        test.setPoint(3, new FunctionPoint(-0.5, 2));
        System.out.println("SetPoint[3](0.5,2): " + '\n' + test);
        // Incorrect setting
        test.setPoint(3, new FunctionPoint(6, 2));
        System.out.println("SetPoint[3](6,2): " + '\n' + test);
        // Change Yvalue at third FunctionPoint
        test.setPointY(3, 10);
        System.out.println("SetPointY(3,10): " + '\n' + test);
        // Test add and delete
        test.addPoint(new FunctionPoint(3,28));
        System.out.println("addPoint(3,28): " + '\n' + test);
        for(int i = 0; i < 8; i++){
            test.addPoint(new FunctionPoint(i+0.1,28));
        }
        System.out.println("addPoint(3,28): " + '\n' + test);
        test.deletePoint(test.getPointsCount() - 1);
        System.out.println("deletePoint(last): " + '\n' + test);
        test.deletePoint(2);
        System.out.println("deletePoint(2): " + '\n' + test);


    }
}