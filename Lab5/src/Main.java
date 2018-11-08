import functions.*;
import functions.basic.*;


import java.io.*;

public class Main{
    public static void main(String[] args) throws InappropriateFunctionPointException {
        double[] values = new double[] {1, 2, 3, 4, 5, 6};
        ArrayTabulatedFunction tempArr1 = new ArrayTabulatedFunction(0, 10, values);
        ArrayTabulatedFunction tempArr2 = new ArrayTabulatedFunction(0, 10, values);

        LinkedListTabulatedFunction tempLink1 = new LinkedListTabulatedFunction(0, 10, values);
        LinkedListTabulatedFunction tempLink2 = new LinkedListTabulatedFunction(0, 10, values);

        System.out.println("Same type(array): ");
        System.out.println(tempArr1);
        System.out.println(tempArr2);
        System.out.println(tempArr1.equals(tempArr2));
        tempArr1.deletePoint(0);
        System.out.println(tempArr1.equals(tempArr2));

        System.out.println("Same type(linked list): ");
        System.out.println(tempLink1);
        System.out.println(tempLink2);
        System.out.println(tempLink1.equals(tempLink2));
        tempLink1.deletePoint(0);
        System.out.println(tempLink1.equals(tempLink2));

        System.out.println("Different types: ");
        System.out.println(tempArr1);
        System.out.println(tempLink1);
        System.out.println(tempArr1.equals(tempLink1));
        tempLink1.deletePoint(0);
        System.out.println(tempArr1.equals(tempLink1));

        System.out.println("Hashcode: ");
        System.out.println(tempArr1.hashCode());
        System.out.println(tempArr2.hashCode());
        System.out.println(tempLink1.hashCode());
        System.out.println(tempLink2.hashCode());

        System.out.println("Hashcode change: ");
        System.out.println(tempArr1.hashCode());
        tempArr1.deletePoint(0);
        System.out.println(tempArr1.hashCode());

        System.out.println("Cloning(array): ");
        tempArr2 = (ArrayTabulatedFunction)tempArr1.clone();
        System.out.println(tempArr1);
        System.out.println(tempArr2);

        System.out.println("Cloning(linked list): ");
        tempLink2 = (LinkedListTabulatedFunction) tempLink1.clone();
        System.out.println(tempLink1);
        System.out.println(tempLink2);
        tempLink1.deletePoint(0);
        System.out.println(tempLink1);
        System.out.println(tempLink2);
    }
}