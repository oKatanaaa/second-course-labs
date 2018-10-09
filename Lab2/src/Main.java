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
        try {
            test.setPoint(3, new FunctionPoint(6, 2));
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("SetPoint[3](6,2): " + '\n' + test);
        }
        // Change Yvalue at third FunctionPoint
        test.setPointY(3, 10);
        System.out.println("SetPointY(3,10): " + '\n' + test);
        // Test add and delete
        try {
            test.addPoint(new FunctionPoint(3, 28));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("addPoint(3,28): " + '\n' + test);
        }
        try {
            for (int i = 0; i < 8; i++) {
                test.addPoint(new FunctionPoint(i + 0.1, 28));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("addPoint(3,28): " + '\n' + test);
        }
        test.deletePoint(test.getPointsCount() - 1);
        System.out.println("deletePoint(last): " + '\n' + test);
        test.deletePoint(2);
        System.out.println("deletePoint(2): " + '\n' + test);

        // Testing new list
        System.out.println("\nTEST LIST\n");
        TabulatedFunction newTest = new ArrayTabulatedFunction(0, 10, new double[]{10.0, 15.0, 20.0, 30.0, 50.0, 100.0});
        try {
            newTest.addPoint(new FunctionPoint(-1, 0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest);
        }


        try {
            newTest.setPoint(0, new FunctionPoint(99, 0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest);
        }

        try {
            for (int i = 6; i > 0; --i) {
                newTest.deletePoint(i);
                System.out.println(newTest);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest);
        }

        try {
            newTest = new ArrayTabulatedFunction(0, 0, null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest);
        }

        try {
            newTest = new ArrayTabulatedFunction(0, 0, 0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest);
        }

        System.out.println("\nTEST LIST\n");
        TabulatedFunction newTest2 = new LinkedListTabulatedFunction(0, 10, new double[]{10.0, 15.0, 20.0, 30.0, 50.0, 100.0});
        System.out.println(newTest2);
        try {
            newTest2.addPoint(new FunctionPoint(-1, 0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest2);
        }


        try {
            newTest2.setPoint(0, new FunctionPoint(99, 0));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest2);
        }

        try {
            for (int i = 6; i > 0; --i) {
                newTest2.deletePoint(i);
                System.out.println(newTest2);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest2);
        }

        try {
            newTest2 = new LinkedListTabulatedFunction(0, 0, null);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest2);
        }

        try {
            newTest2 = new LinkedListTabulatedFunction(0, 1, 0);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println(newTest2);
        }

    }
}