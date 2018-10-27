import functions.*;
import functions.basic.*;


import java.io.*;

public class Main{
    public static void main(String[] args) throws InappropriateFunctionPointException {
        double[] values = new double[] {1, 2, 3, 4, 5, 6};
        ArrayTabulatedFunction tempArr1 = new ArrayTabulatedFunction(0, 10, values);
        LinkedListTabulatedFunction tempLink2 = new LinkedListTabulatedFunction(0, 10, values);
        System.out.println(tempArr1);
        System.out.println(tempLink2);
        System.out.println(tempArr1.equals(tempLink2));
        tempLink2.deletePoint(0);
        System.out.println(tempArr1.equals(tempLink2));
    }
}