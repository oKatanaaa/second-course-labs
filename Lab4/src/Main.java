import functions.*;
import functions.basic.*;


import java.io.*;

public class Main{
    public static void main(String[] args) throws InappropriateFunctionPointException {
        System.out.println("Original");
        Function[] funs = new Function[]{new Sin(), new Cos()};
        for (double x = 0; x <= 2 * Math.PI; x+= 1) {
            for (Function fun : funs)
                System.out.print(String.valueOf(fun.getFunctionValue(x)) + " ");
        }
        System.out.println("\nTabulated analog");


        TabulatedFunction[] tabulatedFuns = new TabulatedFunction[] {
                TabulatedFunctions.tabulate(funs[0], 0, 2 * Math.PI, 10),
                TabulatedFunctions.tabulate(funs[1], 0, 2 * Math.PI, 10)
        };
        for (double x = 0; x <= 2 * Math.PI; x += 1) {
            for (Function fun : tabulatedFuns) {
                System.out.print(String.valueOf(fun.getFunctionValue(x)) + " ");
            }
        }
        System.out.println("\nSquares");


        Function squareTabulatedSin = Functions.power(tabulatedFuns[0], 2);
        Function squareTabulatedCos = Functions.power(tabulatedFuns[1], 2);
        Function squareSumSinCos = Functions.sum(squareTabulatedSin,squareTabulatedCos);
        for(double x = 0; x <= 2 * Math.PI; x += 1) {
            System.out.print(String.valueOf(squareSumSinCos.getFunctionValue(x)) + " ");
        }
        System.out.println("\nTabulated Exp");

        // WRITING TABULATED FUNCTION
        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(
                new Exp(), 0, 10, 11
        );
        try {
            FileWriter fileToWrite = new FileWriter("writeFile.txt");
            TabulatedFunctions.writeTabulatedFunction(tabulatedExp, fileToWrite);
            fileToWrite.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        // READING TABULATED FUNCTION

        try {
            FileReader fileToRead = new FileReader("writeFile.txt");
            TabulatedFunction readedFun = TabulatedFunctions.readTabulatedFunction(fileToRead);
            fileToRead.close();

            for(double x = 1; x <= 10; x += 1){
                System.out.print(tabulatedExp.getFunctionValue(x) + " ");
                System.out.print(readedFun.getFunctionValue(x) + " ");
                x+= 1;
            }
        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage() + "4");
        } catch (Exception e){
            System.out.println(e.getMessage() + "4");
        }

        System.out.println("\nTabulated Ln");
        // OUTPUT TABULATED FUNCTION
        TabulatedFunction tabulatedLn = TabulatedFunctions.tabulate(
                new Log(Math.E), 1, 10, 11
        );
        try {
            FileOutputStream fileToWrite = new FileOutputStream("outFile");
            TabulatedFunctions.outputTabulatedFunction(tabulatedLn, fileToWrite);
            fileToWrite.close();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        // INPUT TABULATED FUNCTION

        try {
            FileInputStream fileToRead = new FileInputStream("outFile");
            TabulatedFunction readedFun = TabulatedFunctions.inputTabulatedFunction(fileToRead);
            fileToRead.close();

            for(double x = 1; x <= 10; x += 1){
                System.out.print(tabulatedLn.getFunctionValue(x) + " ");
                System.out.print(readedFun.getFunctionValue(x) + " ");
            }
            System.out.println();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage() + "4");
        } catch (Exception e){
            System.out.println(e.getMessage() + "4");
        }

        // SERIALIZE FUNCTION COMPOSITION
        Function outer = new Log(Math.E);
        Function inner = new Exp();
        Function compose = Functions.composition(outer, inner);
        TabulatedFunction tabulatedCompose = TabulatedFunctions.tabulate(
                compose, 1, 10, 11
        );

        try{
            FileOutputStream fileOut = new FileOutputStream("outFile.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tabulatedCompose);
            out.close();
            fileOut.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        TabulatedFunction deserializedFun;
        try{
            FileInputStream fileOut = new FileInputStream("outFile.ser");
            ObjectInputStream out = new ObjectInputStream(fileOut);
            deserializedFun = (TabulatedFunction) out.readObject();
            out.close();
            fileOut.close();
            System.out.println("Deserialized and original");
            for(int x = 1; x <= 10; x += 1){
                System.out.print(tabulatedCompose.getFunctionValue(x) + " ");
                System.out.print(deserializedFun.getFunctionValue(x) + " ");
            }
        } catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }
}